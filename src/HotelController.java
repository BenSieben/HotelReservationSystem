import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public class HotelController {

	private HotelModel model;
	private HotelView view;
	private HashMap<String, Object> reserveRoomData; // Holds needed details to reserve a room (as a customer)

	public HotelController(HotelModel model, HotelView view) {
		this.model = model;
		this.view = view;
		this.reserveRoomData = new HashMap<String, Object>();
	}

	/**
	 * Sets up all listeners to listen in on actions performed in View to be
	 * able to react
	 */
	public void initializeViewListeners() {
		// Listeners on the login panel
		initializeLoginPanelListeners();

		// Listeners on the customer panel (and sub-panels (the customer cards))
		initializeCustomerPanelListeners();

		initializePickReservationDateCustomerCardListeners();
		initializePickRoomCustomerCardListeners();
		initializeSelectGuestsCustomerCardListeners();
		initializePaymentCustomerCardListeners();
		initializeConfirmReservationCustomerCardListeners();
		initializeViewReservationsCustomerCardListeners();

		// Listeners on manager panel (and sub-panels (the manager cards))
		initializeManagerPanelListeners();

		initializeViewCurrentReservationsManagerCardListeners();
		initializeViewRevenueManagerCardListeners();
	}

	/**
	 * Adds listeners to the login panel
	 */
	private void initializeLoginPanelListeners() {
		LoginPanel lp = this.view.getLoginPanel();
		lp.addSignInButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleLoginSignIn();
			}
		});
		lp.addSignUpButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleLoginSignUp();
			}
		});
	}

	/**
	 * Adds listeners to the customer panel
	 */
	private void initializeCustomerPanelListeners() {
		final CustomerPanel cp = this.view.getCustomerPanel();
		cp.addLogoutButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleLogout();
			}
		});
		cp.addMakeNewReservationButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cp.goToMakeNewReservationView();
			}
		});
		cp.addViewReservationsButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Upon pressing view reservations button, load up current and
				// archived reservations
				if (loadCustomerCurrentReservations() && loadCustomerArchivedReservations()) {
					cp.goToViewReservationView();
				} else {
					cp.setMessageLabel("There was an error in retrieving your reservation information!");
				}
			}
		});
	}

	/**
	 * Adds listeners to the pick reservation date customer card in the customer
	 * panel
	 */
	private void initializePickReservationDateCustomerCardListeners() {
		final CustomerPanel cp = this.view.getCustomerPanel();
		final PickReservationDateCustomerCard cpDateCard = cp.getPickReservationDateCustomerCard();
		final PickRoomCustomerCard cpRoomCard = cp.getPickRoomCustomerCard();
		// We do not do anything when previous button is pressed on date card
		// (since it is the first card),
		// which is why there is no action listener for it
		cpDateCard.addNextButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (pickReservationDateCustomerCardFieldsAreValid()) {
					HashMap<String, Object> reservationDates = new HashMap<String, Object>();
					reservationDates.put("start_date", cpDateCard.getStartDateText());
					reservationDates.put("end_date", cpDateCard.getEndDateText());

					// Add start / end days to the reserveRoomData
					reserveRoomData.put("start_date", cpDateCard.getStartDateText());
					reserveRoomData.put("end_date", cpDateCard.getEndDateText());

					ResultSet availableRooms = model.getAvailableRooms(reservationDates);
					try {
						ArrayList<JButton> roomButtonsList = new ArrayList<JButton>();
						while (availableRooms.next()) {
							int roomId = availableRooms.getInt("room_id");
							int roomNumber = availableRooms.getInt("room_number");
							double price = availableRooms.getDouble("price");
							String roomType = availableRooms.getString("room_type");
							int floor = availableRooms.getInt("floor");
							int capacity = availableRooms.getInt("capacity");
							int beds = availableRooms.getInt("beds");
							int bathrooms = availableRooms.getInt("bathrooms");
							boolean hasWindows = availableRooms.getBoolean("has_windows");
							boolean smokingAllowed = availableRooms.getBoolean("smoking_allowed");
							final Object[][] availableRoomDetails = { { "Room ID", roomId },
									{ "Room Number", roomNumber }, { "Daily Price ($)", price },
									{ "Room Type", roomType }, { "Floor", floor }, { "Capacity", capacity },
									{ "Beds", beds }, { "Bathrooms", bathrooms }, { "Has Windows", hasWindows },
									{ "Smoking Allowed", smokingAllowed } };
							JButton currentRoomButton = new JButton(
									"Room Number: " + roomNumber + " (" + roomType + " Room)");
							currentRoomButton.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									cpRoomCard.setRoomDetailsPane(availableRoomDetails);
								}
							});
							roomButtonsList.add(currentRoomButton);
						}
						cpRoomCard.setRoomListPane(roomButtonsList);
						cp.goToNextCard();
					} catch (Exception ex) {
						System.err.println(ex.toString());
					}
				} else {
					cp.setMessageLabel("Error: invalid date field(s) detected. "
							+ "Make sure your dates are filled correctly (and are realistic).");
				}
			}
		});
	}

	/**
	 * Checks whether or not the fields in pickReservationDateCustomerCard are
	 * valid
	 * 
	 * @return true if all fields are valid; false otherwise
	 */
	private boolean pickReservationDateCustomerCardFieldsAreValid() {
		// Pull all fields from the date card
		PickReservationDateCustomerCard cpDate = this.view.getCustomerPanel().getPickReservationDateCustomerCard();
		String startDate = cpDate.getStartDateText();
		String endDate = cpDate.getEndDateText();

		// Regular expression to test fields against
		String dateRegex = "^\\d{4}-\\d{2}-\\d{2}$";

		// Check one: the dates match their regex
		if (startDate.matches(dateRegex) && endDate.matches(dateRegex)) {
			// Check two: the given dates are realistic values
			if (dateIsRealistic(startDate) && dateIsRealistic(endDate)) {
				// Check three: start date is less than end date
				return (startDate).compareTo(endDate) < 0;
			}
		}
		return false; // This return catches any case where some bad field is
						// detected
	}

	/**
	 * Determines if given date is realistic or not
	 * 
	 * @param date
	 *            YYYY-MM-DD date string to check
	 * @return true if date is realistic, and false otherwise
	 */
	private boolean dateIsRealistic(String date) {
		int year = Integer.parseInt(date.substring(0, date.indexOf("-")));
		int month = Integer.parseInt(date.substring(date.indexOf("-") + 1, date.lastIndexOf("-")));
		int day = Integer.parseInt(date.substring(date.lastIndexOf("-") + 1));
		if (year >= 2016 && year <= 3000) { // Acceptable year starts at 2016
											// and ends at 3000 for reservation
			if (month >= 1 && month <= 12) {
				// Check that for the given month, the day is valid
				switch (month) {
				case 1:
					return (day >= 1 && day <= 31);
				case 2:
					if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) { // Check
																					// for
																					// leap
																					// year
						return (day >= 1 && day <= 29); // Is leap year
					} else {
						return (day >= 1 && day <= 28); // Is not leap year
					}
				case 3:
					return (day >= 1 && day <= 31);
				case 4:
					return (day >= 1 && day <= 30);
				case 5:
					return (day >= 1 && day <= 31);
				case 6:
					return (day >= 1 && day <= 30);
				case 7:
					return (day >= 1 && day <= 31);
				case 8:
					return (day >= 1 && day <= 31);
				case 9:
					return (day >= 1 && day <= 30);
				case 10:
					return (day >= 1 && day <= 31);
				case 11:
					return (day >= 1 && day <= 30);
				case 12:
					return (day >= 1 && day <= 31);
				}

			}
		}
		return false; // This return catches any case where some bad date is
						// detected
	}

	/**
	 * Adds listeners to the pick room customer card in the customer panel
	 */
	private void initializePickRoomCustomerCardListeners() {
		final CustomerPanel cp = this.view.getCustomerPanel();
		final PickRoomCustomerCard cpRoomCard = cp.getPickRoomCustomerCard();
		cpRoomCard.addPreviousButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cp.goToPreviousCard();
			}
		});
		cpRoomCard.addNextButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				HashMap<String, Object> roomDetails = cpRoomCard.getLastSelectedRoomDetails();
				if (roomDetails.size() > 1) { // Customer did pick a room to
												// reserve
					// Add room ID and room capacity to the reserveRoomData
					reserveRoomData.put("room_id", roomDetails.get("Room ID"));
					reserveRoomData.put("capacity", roomDetails.get("Capacity"));
					// Also add other details (for the receipt)
					reserveRoomData.put("room_number", roomDetails.get("Room Number"));
					reserveRoomData.put("daily_price", roomDetails.get("Daily Price ($)"));
					reserveRoomData.put("room_type", roomDetails.get("Room Type"));
					reserveRoomData.put("floor", roomDetails.get("Floor"));
					reserveRoomData.put("beds", roomDetails.get("Beds"));
					reserveRoomData.put("bathrooms", roomDetails.get("Bathrooms"));
					reserveRoomData.put("has_windows", roomDetails.get("Has Windows"));
					reserveRoomData.put("smoking_allowed", roomDetails.get("Smoking Allowed"));
					cp.goToNextCard();
				} else { // Customer did not pick a room to reserve
					cp.setMessageLabel("Error: you must pick a room to reserve to proceed!");
				}
			}
		});
	}

	/**
	 * Adds listeners to the select guests customer card in the customer panel
	 */
	private void initializeSelectGuestsCustomerCardListeners() {
		final CustomerPanel cp = this.view.getCustomerPanel();
		final SelectGuestsCustomerCard cpGuestCard = cp.getSelectGuestsCustomerCard();
		final PaymentCustomerCard cpPaymentCard = cp.getPaymentCustomerCard();
		cpGuestCard.addPreviousButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cp.goToPreviousCard();
			}
		});
		cpGuestCard.addNextButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String numGuestsText = cpGuestCard.getNumGuestsText();
					// Check that number of guests selected by user is within
					// recommended range
					int numGuests = Integer.parseInt(numGuestsText);
					// + 1 to include person making reservation (it is assumed
					// they will also be in the room besides the guests)
					if (numGuests + 1 <= (Integer) reserveRoomData.get("capacity") && numGuests >= 0) {
						reserveRoomData.put("guests", numGuests);
						String[] paymentTypes = model.getAllPaymentTypes();
						cpPaymentCard.setPaymentTypes(paymentTypes);
						// TODO prepare payment card with total cost
						String totalCost = model.calculateReservationCost((String) reserveRoomData.get("start_date"),
								(String) reserveRoomData.get("end_date"), (double) reserveRoomData.get("daily_price"));
						cpPaymentCard.setTotalCost(totalCost);
						cp.goToNextCard();
					} else {
						cp.setMessageLabel("Error: number of guests would exceed recommended "
								+ " number of guests for the selected room to reserve");
					}
				} catch (Exception ex) {
					cp.setMessageLabel("Error: please enter an actual number of guests");
					ex.printStackTrace();
				}
			}
		});
	}

	/**
	 * Adds listeners to the payment customer card in the customer panel
	 */
	private void initializePaymentCustomerCardListeners() {
		final CustomerPanel cp = this.view.getCustomerPanel();
		final PaymentCustomerCard cpPaymentCard = cp.getPaymentCustomerCard();
		final ConfirmReservationCustomerCard cpReceiptCard = cp.getConfirmReservationCustomerCard();
		cpPaymentCard.addPreviousButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cp.goToPreviousCard();
			}
		});
		cpPaymentCard.addNextButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					// TODO prepare receipt card (if not already done in other
					// listeners)
					// From the payment card, once they hit next we can
					// determine their chosen payment type
					// (make the 2D object array for
					// ConfirmReservationCustomerCard's
					// setReservationDetailsPane method)

					String selectedPaymentType = cpPaymentCard.getCurrentlySelectedPaymentType();
					reserveRoomData.put("payment_type", selectedPaymentType);
					String totalCostText = cpPaymentCard.getTotalCostText();
					double totalCost = Double.parseDouble(totalCostText);
					reserveRoomData.put("amount", totalCost);

					System.out.println(reserveRoomData);
					Object[][] receiptData = { { "Start Date", reserveRoomData.get("start_date") },
							{ "End Date", reserveRoomData.get("end_date") },
							{ "Room ID", reserveRoomData.get("room_id") },
							{ "Room Number", reserveRoomData.get("room_number") },
							{ "Daily Price ($)", reserveRoomData.get("daily_price") },
							{ "Total Price ($)", reserveRoomData.get("amount") },
							{ "Payment Type", reserveRoomData.get("payment_type") },
							{ "Room Type", reserveRoomData.get("room_type") },
							{ "Floor", reserveRoomData.get("floor") }, { "Capacity", reserveRoomData.get("capacity") },
							{ "Number of Guests", reserveRoomData.get("guests") },
							{ "Beds", reserveRoomData.get("beds") }, { "Bathrooms", reserveRoomData.get("bathrooms") },
							{ "Has Windows", reserveRoomData.get("has_windows") },
							{ "Smoking Allowed", reserveRoomData.get("smoking_allowed") } };
					cpReceiptCard.setReservationDetailsPane(receiptData);

					cp.goToNextCard();
				} catch (Exception ex) {
					cp.setMessageLabel("Error: total cost is in an unreadable format!");
				}
			}
		});
	}

	/**
	 * Adds listeners to the confirm reservation customer card in the customer
	 * panel
	 */
	private void initializeConfirmReservationCustomerCardListeners() {
		final CustomerPanel cp = this.view.getCustomerPanel();
		ConfirmReservationCustomerCard cpReceiptCard = cp.getConfirmReservationCustomerCard();
		cpReceiptCard.addPreviousButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cp.goToPreviousCard();
			}
		});
		cpReceiptCard.addNextButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO make sure reserving the room works (once it is ready for
				// testing)
				model.reserveRoom(reserveRoomData);
				cp.resetAllFields();
				cp.goToMakeNewReservationView();
			}
		});
	}

	/**
	 * Adds listeners to the view reservations customer card in the customer
	 * panel
	 */
	private void initializeViewReservationsCustomerCardListeners() {
		final CustomerPanel cp = this.view.getCustomerPanel();
		final ViewReservationsCustomerCard cpViewReservationCard = cp.getViewReservationsCustomerCard();
		cpViewReservationCard.addCancelReservationButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					// TODO delete selected reservation, then reload new list of
					// reservations back into the reservation list panel
					int bookingID = Integer.parseInt(cpViewReservationCard.getCurrentlySelectedReservationToCancel());
					HashMap<String, Object> data = new HashMap<String, Object>();
					data.put("booking_id", bookingID);
					if (model.deleteReservation(data)) {
						loadCustomerCurrentReservations(); // Reload customer
															// reservations on
															// successful delete
					} else {
						cp.setMessageLabel("Error: unable to delete selected booking!");
					}
				} catch (Exception ex) {
					cp.setMessageLabel("Error: booking ID is not correct format (integer)!");
					ex.printStackTrace();
				}
			}
		});
		cpViewReservationCard.addChangeNumGuestsButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO let user specify new number of guests to put in selected
				// room
				int bookingID = Integer.parseInt(cpViewReservationCard.getCurrentlySelectedReservationToCancel());
				HashMap<String, Object> data = new HashMap<String, Object>();
				data.put("booking_id", bookingID);
				String guestsString = (String) JOptionPane.showInputDialog(null,
						"How many guests will be in reservation with booking ID " + bookingID + "?",
						"Enter new number of guests", JOptionPane.QUESTION_MESSAGE);
				if (guestsString == null) {
					cp.setMessageLabel("Error: you need to specify the new number of guests if changing it!");
				} else {
					try {
						int numGuests = Integer.parseInt(guestsString);
						if (numGuests >= 0 && numGuests + 1 <= model.getCapacityForBooking(bookingID)) {
							data.put("guests", numGuests);
							if (model.updateReservation(data)) {
								loadCustomerCurrentReservations();
							} else {
								cp.setMessageLabel(
										"Error: unable to change number of guests for selected reservation!");
							}
						} else {
							cp.setMessageLabel("Error: number of guests exceeds room size!");
						}
					} catch (Exception ex) {
						cp.setMessageLabel("Error: number of guests must be a number!");
						ex.printStackTrace();
					}
				}
			}
		});
	}

	/**
	 * Adds listeners to the manager panel
	 */
	private void initializeManagerPanelListeners() {
		final ManagerPanel mp = this.view.getManagerPanel();
		mp.addLogoutButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleLogout();
			}
		});
		mp.addViewCurrentReservationsPanelButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO set up and go to view current reservations view in
				// manager panel
				loadAllCurrentReservations();
				mp.changeCard(ManagerPanel.CURRENT_RESERVATIONS_PANEL);
			}
		});
		mp.addViewArchivedReservationsPanelButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO set up and go to view archived reservations view in
				// manager panel
				loadAllArchivedReservations();
				mp.changeCard(ManagerPanel.ARCHIVED_RESERVATIONS_PANEL);
			}
		});
		mp.addViewRevenuePanelButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO set up and go to view revenue view in manager panel
				mp.changeCard(ManagerPanel.REVENUE_PANEL);
			}
		});
	}

	/**
	 * Adds listeners to the view current reservations manager card
	 */
	private void initializeViewCurrentReservationsManagerCardListeners() {
		final ManagerPanel mp = this.view.getManagerPanel();
		final ViewCurrentReservationsManagerCard mpViewCurrentReservationCard = mp
				.getViewCurrentReservationsManagerCard();
		mpViewCurrentReservationCard.addCancelReservationButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO delete selected reservation, then reload new list of
				// reservations back into the current reservation list panel
				System.err.println("Deleting reservation "
						+ mpViewCurrentReservationCard.getCurrentlySelectedReservationToCancel());
			}
		});
	}

	/**
	 * Adds listeners to the view revenue manager card
	 */
	private void initializeViewRevenueManagerCardListeners() {
		final ManagerPanel mp = this.view.getManagerPanel();
		final ViewRevenueManagerCard mpViewRevenueManagerCard = mp.getViewRevenueManagerCard();
		mpViewRevenueManagerCard.addYearMonthSubmitButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO check that manager entered valid year / month to get
				// revenue for, then show
				// the revenues in the view if a valid value was entered
				String yearMonth = mpViewRevenueManagerCard.getYearMonthText();
			}
		});
	}

	/**
	 * Handles case when user tries to sign in
	 */
	private void handleLoginSignIn() {
		try {
			HashMap<String, Object> userData = this.view.getSignInValues();

			if (userData == null) {
				this.view.displayLoginMessage("Error! Sign-in cannot get info.");
			} else {
				ResultSet result = this.model.signIn(userData);
				if (result.next() == true) { // check if result has any data,
												// false means no
					// Compute user's name
					String usersName = result.getString("first_name") + " " + result.getString("last_name");

					// Compute whether we should show customer view or manager
					// view based on
					// the result of sign in query
					if (result.getInt("customer_id") == 1) { // Manager has
																// customer id
																// 1, so greet
																// the manager
						this.view.changeManagerName(usersName);
						this.view.changeCard(HotelView.MANAGER_PANEL);
						// TODO also update the view current reservations /
						// archived reservations in the manager panel
						loadAllCurrentReservations();
						loadAllArchivedReservations();
					} else { // Regular customers will have any other id
						this.view.changeCustomerName(usersName);
						this.view.changeCard(HotelView.CUSTOMER_PANEL);
						// TODO also update the view reservations customer card
						// based on the signed in customer's id
					}
				} else {
					this.view.displayLoginMessage("Error! Sign-in query unsuccessful. "
							+ "Most likely case is that username / password combination is invalid.");
				}
			}
		} catch (Exception ex) {
			this.view.displayLoginMessage("Error! Exception occurred (" + ex.toString() + ").");
		}
	}

	/**
	 * Handles case when user tries to sign up
	 */
	private void handleLoginSignUp() {
		try {
			HashMap<String, Object> userData = this.view.getSignUpValues();
			boolean isSuccessful = this.model.signUp(userData);
			if (isSuccessful) {
				// Check if user gave a valid age or not
				if (userData.get("age").equals(-1)) {
					this.view.displayLoginMessage("Sign-up complete. You can now sign in. "
							+ "However, your age was invalid, and thus was defaulted to 18.");
				} else {
					this.view.displayLoginMessage(
							"Success! Sign-up complete. " + "You can now sign in with your credentials.");
				}
			} else {
				this.view.displayLoginMessage("Error! Sign-up query unsuccessful. "
						+ "Most likely case is that the username already exists.");
			}
		} catch (Exception ex) {
			this.view.displayLoginMessage("Error! Exception occurred (" + ex.toString() + ").");
		}
	}

	/**
	 * Handles cases when user logs out of customer or manager panels
	 */
	private void handleLogout() {
		// Clear all panels of content, then go back to login panel
		this.view.resetAllPanels();
		this.view.changeCard(HotelView.LOGIN_PANEL);
		this.model.signOut();
	}

	/**
	 * Loads current reservations for logged-in customer.
	 * 
	 * @return true if load worked, and false if not
	 */
	private boolean loadCustomerCurrentReservations() {
		CustomerPanel cp = this.view.getCustomerPanel();
		ViewReservationsCustomerCard cpViewReservationsCard = cp.getViewReservationsCustomerCard();
		try {
			ResultSet customerReservations = model.viewReservation();
			Object[][] reservationArray = getReservations(customerReservations);
			cpViewReservationsCard.setCurrentReservationDetailsPane(reservationArray);
			return true;
		} catch (Exception ex) {
			cp.setMessageLabel("Error: unable to load reservation data");
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * Loads archived reservations for logged-in customer
	 * 
	 * @return true if load worked, and false if not
	 */
	private boolean loadCustomerArchivedReservations() {
		CustomerPanel cp = this.view.getCustomerPanel();
		ViewReservationsCustomerCard cpViewReservationsCard = cp.getViewReservationsCustomerCard();
		try {
			ResultSet archivedReservations = model.viewArchivedReservation();
			Object[][] reservationArray = getReservations(archivedReservations);
			cpViewReservationsCard.setArchivedReservationsDetailsPane(reservationArray);
			return true;
		} catch (Exception ex) {
			cp.setMessageLabel("Error: unable to load reservation data");
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * Loads all current reservations of the hotel for manager
	 * 
	 * @return true if load worked, and false if not
	 */
	private boolean loadAllCurrentReservations() {
		ManagerPanel mp = view.getManagerPanel();
		ViewCurrentReservationsManagerCard currentReservationsPanel = mp.getViewCurrentReservationsManagerCard();
		try {
			ResultSet customerReservations = model.viewAllCurrentReservations();
			Object[][] reservationArray = getReservations(customerReservations);
			currentReservationsPanel.setReservationDetailsPane(reservationArray);
			return true;
		} catch (Exception ex) {
			mp.setMessageLabel("Error: unable to load reservation data");
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * Loads all archived reservations of the hotel for manager
	 * 
	 * @return true if load worked, and false if not
	 */
	private boolean loadAllArchivedReservations() {
		ManagerPanel mp = view.getManagerPanel();
		ViewArchivedReservationsManagerCard archivedReservationsPanel = mp.getViewArchivedReservationsManagerCard();
		try {
			ResultSet archivedReservations = model.viewAllArchivedReservations();
			Object[][] reservationArray = getReservations(archivedReservations);
			archivedReservationsPanel.setReservationDetailsPane(reservationArray);
			return true;
		} catch (Exception ex) {
			mp.setMessageLabel("Error: unable to load reservation data");
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * Gets a 2D Array of reservations using data from a given ResultSet
	 * @param rs the ResultSet of reservations
	 * @return a 2D Array of reservations
	 * @throws Exception a SQLException
	 */
	private static Object[][] getReservations(ResultSet rs) throws Exception {
		ArrayList<Object[]> reservationList = new ArrayList<Object[]>();
		while (rs.next()) {
			int bookingID = rs.getInt("booking_id");
			String updatedAt = rs.getString("updated_at");
			String start_date = rs.getString("start_date");
			String end_date = rs.getString("end_date");
			int guests = rs.getInt("guests");
			int roomNumber = rs.getInt("room_number");
			int detailsId = rs.getInt("details_id");
			double price = rs.getDouble("price");
			String roomType = rs.getString("room_type");
			int floor = rs.getInt("floor");
			int capacity = rs.getInt("capacity");
			int beds = rs.getInt("beds");
			int bathrooms = rs.getInt("bathrooms");
			boolean hasWindows = rs.getBoolean("has_windows");
			boolean smokingAllowed = rs.getBoolean("smoking_allowed");
			Object[] reservationDetails = { bookingID, roomNumber, start_date, end_date, guests, "$" + price, roomType,
					floor, capacity, beds, bathrooms, hasWindows, smokingAllowed };
			reservationList.add(reservationDetails);
		}
		Object[][] reservationArray = reservationList
				.toArray(new Object[reservationList.size()][ReservationListPanel.COLUMN_NAMES.length]);
		return reservationArray;
	}
	
}
