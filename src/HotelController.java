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

        initializePickReservationCustomerCardListeners();
        initializePickRoomCustomerCardListeners();
        initializeSelectGuestsCustomerCardListeners();
        initializePaymentCustomerCardListeners();
        initializeConfirmReservationCustomerCardListeners();

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
        CustomerPanel cp = this.view.getCustomerPanel();
        cp.addLogoutButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogout();
            }
        });
    }

    /**
     * Adds listeners to the pick reservation customer card in the customer panel
     */
    private void initializePickReservationCustomerCardListeners() {
        CustomerPanel cp = this.view.getCustomerPanel();
        PickReservationDateCustomerCard cpDateCard = cp.getPickReservationDateCustomerCard();
        cpDateCard.addPreviousButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cp.goToPreviousCard();
            }
        });
        cpDateCard.addNextButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cp.goToNextCard();
            }
        });
    }

    /**
     * Adds listeners to the pick room customer card in the customer panel
     */
    private void initializePickRoomCustomerCardListeners() {
        CustomerPanel cp = this.view.getCustomerPanel();
        PickRoomCustomerCard cpRoomCard = cp.getPickRoomCustomerCard();
        cpRoomCard.addPreviousButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cp.goToPreviousCard();
            }
        });
        cpRoomCard.addNextButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cp.goToNextCard();
            }
        });
    }

    /**
     * Adds listeners to the select guests customer card in the customer panel
     */
    private void initializeSelectGuestsCustomerCardListeners() {
        CustomerPanel cp = this.view.getCustomerPanel();
        SelectGuestsCustomerCard cpGuestCard = cp.getSelectGuestsCustomerCard();
        cpGuestCard.addPreviousButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cp.goToPreviousCard();
            }
        });
        cpGuestCard.addNextButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cp.goToNextCard();
            }
        });
    }

    /**
     * Adds listeners to the payment customer card in the customer panel
     */
    private void initializePaymentCustomerCardListeners() {
        CustomerPanel cp = this.view.getCustomerPanel();
        PaymentCustomerCard cpPaymentCard = cp.getPaymentCustomerCard();
        cpPaymentCard.addPreviousButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cp.goToPreviousCard();
            }
        });
        cpPaymentCard.addNextButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cp.goToNextCard();
            }
        });
    }

    /**
     * Adds listeners to the confirm reservation customer card in the customer panel
     */
    private void initializeConfirmReservationCustomerCardListeners() {
        CustomerPanel cp = this.view.getCustomerPanel();
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
                cp.goToNextCard();
            }
        });
    }

    /**
     * Adds listeners to the manager panel
     */
    private void initializeManagerPanelListeners() {
        ManagerPanel mp = this.view.getManagerPanel();
        mp.addLogoutButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogout();
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
