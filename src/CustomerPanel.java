import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * JPanel that specifically draws the
 * customer panels for customers
 * to perform actions like making reservations
 */
public class CustomerPanel extends JPanel {

    private static final String DEFAULT_CUSTOMER_NAME = "Customer";

    // Customer name can be set to greet customers by name
    //   in this welcomeLabel
    private JLabel welcomeLabel;

    // Field for the top panel
    private JButton logoutButton;

    /**
     * Creates a new CustomerPanel with default name
     * for Customer
     */
    public CustomerPanel() {
        this(DEFAULT_CUSTOMER_NAME);  // Use default customer nae
    }

    /**
     * Creates new CustomerPanel with given customerName
     * for customer
     * @param customerName name to refer to the customer by
     * @pre customerName is not null; customerName is not empty string
     */
    public CustomerPanel(String customerName) {
        super();

        // TODO implement real customer panel
        setLayout(new BorderLayout());

        // Create top panel which lets customer log out
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(255, 221, 153));
        this.welcomeLabel = new JLabel();
        changeCustomerName(customerName);  // Use helper method to set welcomeLabel text
        this.logoutButton = new JButton("Logout");

        topPanel.add(this.logoutButton);
        topPanel.add(this.welcomeLabel);

        // Create middle panel which lets customer perform actions
        JPanel middlePanel = new JPanel();
        middlePanel.setBackground(new Color(255, 204, 102));

        // Add all panels to the customer panel
        add(topPanel, BorderLayout.NORTH);
        add(middlePanel, BorderLayout.CENTER);
    }

    /**
     * Changes welcome greeting in panel to match the given
     * customer name
     * @param newName the name of the customer
     * @pre newName is not null; newName is not empty string
     */
    public void changeCustomerName(String newName) {
        if(newName != null && newName.length() > 0) {
            this.welcomeLabel.setText("Welcome, " + newName + "!");
        }
        else {
            // Use default "Customer" to refer to customer when given bad newName
            this.welcomeLabel.setText("Welcome, " + DEFAULT_CUSTOMER_NAME + "!");
        }
    }

    /**
     * Adds a new ActionListener to listen for the logout button
     * being pressed
     * @param listener the new ActionListener to associate with the logout button
     */
    public void addLogoutButtonListener(ActionListener listener) {
        this.logoutButton.addActionListener(listener);
    }

}
