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
        LoginPanel lp = this.view.getLoginPanel();
        CustomerPanel gp = this.view.getCustomerPanel();
        ManagerPanel mp = this.view.getManagerPanel();

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
        gp.addLogoutButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogout();
            }
        });
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

    public void init() {
        signUp();
        signIn();
    }

    public void signUp() {
        try {
            HashMap<String, Object> userData = new HashMap<String, Object>();
            userData = this.view.signUpView();

            if (userData == null) {
                this.view.displayError("Sign-up cannot get info.");
            } else {
                boolean isSuccessful = this.model.signUp(userData);
                if (isSuccessful) {
                    this.view.displaySuccess("Sign-up complete.");
                } else {
                    this.view.displayError("Sign-up query unsuccessful.");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void signIn() {
        try {
            HashMap<String, Object> userData = new HashMap<String, Object>();
            userData = this.view.signInView();

            if (userData == null) {
                this.view.displayError("Sign-in cannot get info.");
            } else {
                ResultSet result = this.model.signIn(userData);
                if (result.next() == true) { //check if result has any data, false means no
                    this.view.displaySuccess("Welcome " + result.getString("first_name") + " " + result.getString("last_name") + ".");
                } else {
                    this.view.displayError("Sign-in query unsuccessful.");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
