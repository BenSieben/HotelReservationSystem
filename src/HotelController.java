import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public class HotelController {

    private HotelModel model;
    private HotelView view;
    private HashMap<String, Object> reserveRoomData;  // Holds needed details to reserve a room (as a customer)

    public HotelController(HotelModel model, HotelView view) {
        this.model = model;
        this.view = view;
        this.reserveRoomData = new HashMap<String, Object>();
    }

    /**
     * Sets up all listeners to listen in on actions
     * performed in View to be able to react
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
                final ViewReservationsCustomerCard cpViewReservationsCard = cp.getViewReservationsCustomerCard();
                try {
                    // TODO load customer reservations into the customer reservation view and archive view
                    ResultSet customerReservations = model.viewReservation();
                    ArrayList<Object[]> reservationList = new ArrayList<Object[]>();
                    while(customerReservations.next()) {
                        String updatedAt = customerReservations.getString("updated_at");
                        String start_date = customerReservations.getString("start_date");
                        String end_date = customerReservations.getString("end_date");
                        int guests = customerReservations.getInt("guests");
                        int roomNumber = customerReservations.getInt("room_number");
                        int detailsId = customerReservations.getInt("details_id");
                        double price = customerReservations.getDouble("price");
                        String roomType = customerReservations.getString("room_type");
                        int floor = customerReservations.getInt("floor");
                        int capacity = customerReservations.getInt("capacity");
                        int beds = customerReservations.getInt("beds");
                        int bathrooms = customerReservations.getInt("bathrooms");
                        boolean hasWindows = customerReservations.getBoolean("has_windows");
                        boolean smokingAllowed = customerReservations.getBoolean("smoking_allowed");
                        Object[] reservationDetails = {"BookID", "FirstName", "LastName", start_date, end_date,
                            guests, "$" + price, roomType, floor, capacity, beds, bathrooms, hasWindows, smokingAllowed};
                        reservationList.add(reservationDetails);
                    }
                    Object[][] reservationArray = reservationList.toArray(new Object[reservationList.size()][ReservationListPanel.COLUMN_NAMES.length]);
                    cpViewReservationsCard.setCurrentReservationDetailsPane(reservationArray);

                    cp.goToViewReservationView();
                }
                catch(Exception ex) {
                    cp.setMessageLabel("Error: unable to load reservation data");
                }
            }
        });
    }

    /**
     * Adds listeners to the pick reservation date customer card in the customer panel
     */
    private void initializePickReservationDateCustomerCardListeners() {
        final CustomerPanel cp = this.view.getCustomerPanel();
        final PickReservationDateCustomerCard cpDateCard = cp.getPickReservationDateCustomerCard();
        final PickRoomCustomerCard cpRoomCard = cp.getPickRoomCustomerCard();
        // We do not do anything when previous button is pressed on date card (since it is the first card),
        //   which is why there is no action listener for it
        cpDateCard.addNextButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(pickReservationDateCustomerCardFieldsAreValid()) {
                    HashMap<String, Object> reservationDates = new HashMap<String, Object>();
                    reservationDates.put("start_date", cpDateCard.getStartDateText());
                    reservationDates.put("end_date", cpDateCard.getEndDateText());

                    // Add start / end days to the reserveRoomData
                    reserveRoomData.put("start_date", cpDateCard.getStartDateText());
                    reserveRoomData.put("end_date", cpDateCard.getEndDateText());

                    ResultSet availableRooms = model.getAvailableRooms(reservationDates);
                    try {
                        ArrayList<JButton> roomButtonsList = new ArrayList<JButton>();
                        while(availableRooms.next()) {
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
                            final Object[][] availableRoomDetails = {{"Detail", "Value"}, {"Room ID", roomId},
                                {"Room Number", roomNumber}, {"Price", "$" + price}, {"Room Type", roomType},
                                {"Floor", floor}, {"Capacity", capacity}, {"Beds", beds}, {"Bathrooms", bathrooms},
                                {"Has Windows", hasWindows}, {"Smoking Allowed", smokingAllowed}};
                            JButton currentRoomButton = new JButton("Room Number: " +
                                    roomNumber + " (" + roomType + " Room)");
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
                    }
                    catch(Exception ex) {
                        System.err.println(ex.toString());
                    }
                }
                else {
                    cp.setMessageLabel("Error: invalid date field(s) detected. " +
                            "Make sure your dates are filled correctly (and are realistic).");
                }
            }
        });
    }

    /**
     * Checks whether or not the fields in pickReservationDateCustomerCard are valid
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
        if(startDate.matches(dateRegex) && endDate.matches(dateRegex)) {
            // Check two: the given dates are realistic values
            if(dateIsRealistic(startDate) && dateIsRealistic(endDate)) {
                // Check three: start date is less than end date
                return (startDate).compareTo(endDate) < 0;
            }
        }
        return false;  // This return catches any case where some bad field is detected
    }

    /**
     * Determines if given date is realistic or not
     * @param date YYYY-MM-DD date string to check
     * @return true if date is realistic, and false otherwise
     */
    private boolean dateIsRealistic(String date) {
        int year = Integer.parseInt(date.substring(0, date.indexOf("-")));
        int month = Integer.parseInt(date.substring(date.indexOf("-") + 1, date.lastIndexOf("-")));
        int day = Integer.parseInt(date.substring(date.lastIndexOf("-") + 1));
        if(year >= 2016 && year <= 3000) {  // Acceptable year starts at 2016 and ends at 3000 for reservation
            if(month >= 1 && month <= 12) {
                // Check that for the given month, the day is valid
                switch(month) {
                    case 1: return (day >= 1 && day <= 31);
                    case 2: if(year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) { // Check for leap year
                                return (day >= 1 && day <= 29);  // Is leap year
                            }
                            else {
                                return (day >= 1 && day <= 28);  // Is not leap year
                            }
                    case 3: return (day >= 1 && day <= 31);
                    case 4: return (day >= 1 && day <= 30);
                    case 5: return (day >= 1 && day <= 31);
                    case 6: return (day >= 1 && day <= 30);
                    case 7: return (day >= 1 && day <= 31);
                    case 8: return (day >= 1 && day <= 31);
                    case 9: return (day >= 1 && day <= 30);
                    case 10: return (day >= 1 && day <= 31);
                    case 11: return (day >= 1 && day <= 30);
                    case 12: return (day >= 1 && day <= 31);
                }

            }
        }
        return false; // This return catches any case where some bad date is detected
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
                // TODO make sure customer actually selected a room before going to next card
                HashMap<String, Object> roomDetails = cpRoomCard.getLastSelectedRoomDetails();
                if(roomDetails.size() > 1) {  // Customer did pick a room to reserve
                    // Add room ID and room capacity to the reserveRoomData
                    reserveRoomData.put("room_id", roomDetails.get("Room ID"));
                    reserveRoomData.put("capacity", roomDetails.get("Capacity"));
                    cp.goToNextCard();
                }
                else {  // Customer did not pick a room to reserve
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
                    // Check that number of guests selected by user is within recommended range
                    int numGuests = Integer.parseInt(numGuestsText);
                    // + 1 to include person making reservation (it is assumed they will also be in the room besides the guests)
                    if(numGuests + 1 <= (Integer)reserveRoomData.get("capacity")) {
                        reserveRoomData.put("guests", numGuests);
                        String[] paymentTypes = model.getAllPaymentTypes();
                        cpPaymentCard.setPaymentTypes(paymentTypes);
                        // TODO prepare payment card with total cost
                        cp.goToNextCard();
                    }
                    else {
                        cp.setMessageLabel("Error: number of guests would exceed recommended " +
                                " number of guests for the selected room to reserve");
                    }
                }
                catch(Exception ex) {
                    cp.setMessageLabel("Error: please enter an actual number of guests");
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
                    // TODO prepare receipt card (if not already done in other listeners)
                    // From the payment card, once they hit next we can determine their chosen payment type
                    //   (make the 2D object array for ConfirmReservationCustomerCard's setReservationDetailsPane method)

                    String selectedPaymentType = cpPaymentCard.getCurrentlySelectedPaymentType();
                    reserveRoomData.put("payment_type", selectedPaymentType);
                    String totalCostText = cpPaymentCard.getTotalCostText();
                    double totalCost = Double.parseDouble(totalCostText.substring(1));  // Skip the $ sign at beginning of string
                    reserveRoomData.put("amount", totalCost);
                    cp.goToNextCard();
                }
                catch(Exception ex) {
                    cp.setMessageLabel("Error: total cost is in an unreadable format!");
                }
            }
        });
    }

    /**
     * Adds listeners to the confirm reservation customer card in the customer panel
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
                // TODO uncomment these lines when ready to test submitting a new reservation to database
                //model.reserveRoom(reserveRoomData);
                //cp.resetAllFields();
                //cp.goToMakeNewReservationView();
            }
        });
    }

    /**
     * Adds listeners to the view reservations customer card in the customer panel
     */
    private void initializeViewReservationsCustomerCardListeners() {
        final CustomerPanel cp = this.view.getCustomerPanel();
        final ViewReservationsCustomerCard cpViewReservationCard = cp.getViewReservationsCustomerCard();
        cpViewReservationCard.addCancelReservationButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO delete selected reservation, then reload new list of reservations back into the reservation list panel
                System.err.println("Deleting reservation " + cpViewReservationCard.getCurrentlySelectedReservationToCancel());
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
                // TODO set up and go to view current reservations view in manager panel
                mp.changeCard(ManagerPanel.CURRENT_RESERVATIONS_PANEL);
            }
        });
        mp.addViewArchivedReservationsPanelButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO set up and go to view archived reservations view in manager panel
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
        final ViewCurrentReservationsManagerCard mpViewCurrentReservationCard = mp.getViewCurrentReservationsManagerCard();
        mpViewCurrentReservationCard.addCancelReservationButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO delete selected reservation, then reload new list of reservations back into the current reservation list panel
                System.err.println("Deleting reservation " +
                        mpViewCurrentReservationCard.getCurrentlySelectedReservationToCancel());
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
                // TODO check that manager entered valid year / month to get revenue for, then show
                //   the revenues in the view if a valid value was entered
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
                if (result.next() == true) { //check if result has any data, false means no
                    // Compute user's name
                    String usersName = result.getString("first_name") + " " + result.getString("last_name");

                    // Compute whether we should show customer view or manager view based on
                    //   the result of sign in query
                    if(result.getInt("customer_id") == 1) {  // Manager has customer id 1, so greet the manager
                        this.view.changeManagerName(usersName);
                        this.view.changeCard(HotelView.MANAGER_PANEL);
                        // TODO also update the view current reservations / archived reservations in the manager panel
                    }
                    else { // Regular customers will have any other id
                        this.view.changeCustomerName(usersName);
                        this.view.changeCard(HotelView.CUSTOMER_PANEL);
                        // TODO also update the view reservations customer card based on the signed in customer's id
                    }
                } else {
                    this.view.displayLoginMessage("Error! Sign-in query unsuccessful. " +
                            "Most likely case is that username / password combination is invalid.");
                }
            }
        }
        catch(Exception ex) {
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
                if(userData.get("age").equals(-1)) {
                    this.view.displayLoginMessage("Sign-up complete. You can now sign in. " +
                            "However, your age was invalid, and thus was defaulted to 18.");
                }
                else {
                    this.view.displayLoginMessage("Success! Sign-up complete. " +
                            "You can now sign in with your credentials.");
                }
            } else {
                this.view.displayLoginMessage("Error! Sign-up query unsuccessful. " +
                        "Most likely case is that the username already exists.");
            }
        }
        catch(Exception ex) {
            this.view.displayLoginMessage("Error! Exception occurred (" + ex.toString() + ").");
        }
    }

    /**
     * Handles cases when user logs out of customer
     * or manager panels
     */
    private void handleLogout() {
        // Clear all panels of content, then go back to login panel
        this.view.resetAllPanels();
        this.view.changeCard(HotelView.LOGIN_PANEL);
        this.model.signOut();
    }
}
