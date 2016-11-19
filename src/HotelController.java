import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.HashMap;

public class HotelController {

    private HotelModel model;
    private HotelView view;

    public HotelController(HotelModel model, HotelView view) {
        this.model = model;
        this.view = view;
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

        // Listeners on manager panel
        initializeManagerPanelListeners();
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
                cp.goToViewReservationView();
            }
        });
    }

    /**
     * Adds listeners to the pick reservation date customer card in the customer panel
     */
    private void initializePickReservationDateCustomerCardListeners() {
        final CustomerPanel cp = this.view.getCustomerPanel();
        PickReservationDateCustomerCard cpDateCard = cp.getPickReservationDateCustomerCard();
        // We do not do anything when previous button is pressed on date card (since it is the first card),
        //   which is why there is no action listener for it
        cpDateCard.addNextButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(true) { // TODO use pickReservationDateCustomerCardFieldsAreValid() when testing full program
                    // TODO run query on database to find rooms that match the time frame specified by customer
                    //   and place such rooms in the pick room customer card
                    // (also remember to attach seconds / milliseconds to end of times before submitting to DB)
                    // After that, the room buttons must be added to the pick room customer card and all the
                    //   action listeners must be wired to these buttons to show the room details
                    cp.goToNextCard();
                }
                else {
                    cp.setMessageLabel("Error: invalid date and / or time fields detected. " +
                            "Make sure your dates and times are filled correctly (and are realistic times).");
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
        String startTime = cpDate.getStartTimeText();
        String endDate = cpDate.getEndDateText();
        String endTime = cpDate.getEndTimeText();

        // Regular expressions to test fields against
        String dateRegex = "\\d{4}-\\d{2}-\\d{2}";
        String timeRegex = "\\d{2}:\\d{2}";

        // Check one: all dates and times match their regex
        if(startDate.matches(dateRegex) && startTime.matches(timeRegex)
                && endDate.matches(dateRegex) && endTime.matches(timeRegex)) {
            // Check two: the given dates and times are realistic values
            if(dateIsRealistic(startDate) && timeIsRealistic(startTime)
                    && dateIsRealistic(endDate) && timeIsRealistic(endTime)) {
                // Check three: start date and time is less than end date and time
                return (startDate + startTime).compareTo((endDate + endTime)) < 0;
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
     * Determines if given time is realistic or not
     * @param time HH:MM time string to check
     * @return true if time is realistic, and false otherwise
     */
    private boolean timeIsRealistic(String time) {
        int hour = Integer.parseInt(time.substring(0, time.indexOf(":")));
        int minute = Integer.parseInt(time.substring(time.indexOf(":") + 1));
        return (hour >= 0 && hour <= 23 && minute >= 0 && minute <= 59);
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
                cp.goToNextCard();
            }
        });
    }

    /**
     * Adds listeners to the select guests customer card in the customer panel
     */
    private void initializeSelectGuestsCustomerCardListeners() {
        final CustomerPanel cp = this.view.getCustomerPanel();
        final SelectGuestsCustomerCard cpGuestCard = cp.getSelectGuestsCustomerCard();
        cpGuestCard.addPreviousButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cp.goToPreviousCard();
            }
        });
        cpGuestCard.addNextButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO prepare payment card with total cost (and payment options)
                // TODO check that the number of guests selected by the user is within recommended range
                String numGuestsText = cpGuestCard.getNumGuestsText();
                cp.goToNextCard();
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
                // TODO prepare receipt card (if not already done in other listeners)
                // From the payment card, once they hit next we can determine their chosen payment type
                //   (make the 2D object array for ConfirmReservationCustomerCard's setReservationDetailsPane method)
                String selectedPaymentType = cpPaymentCard.getCurrentlySelectedPaymentType();
                cp.goToNextCard();
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
                // TODO submit the customer's reservation to the database
                //   all entered reservation data to be reset to defaults

                // TODO uncomment these two lines when ready to use actual program behavior
                //   by clearing all make new reservation panels and going back to first make new reservation panel
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
                System.err.println("View current reservations");
            }
        });
        mp.addViewArchivedReservationsPanelButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO set up and go to view archived reservations view in manager panel
                mp.changeCard(ManagerPanel.ARCHIVED_RESERVATIONS_PANEL);
                System.err.println("View archived reservations");
            }
        });
        mp.addViewRevenuePanelButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO set up and go to view revenue view in manager panel
                mp.changeCard(ManagerPanel.REVENUE_PANEL);
                System.err.println("View revenue");
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
    }
}
