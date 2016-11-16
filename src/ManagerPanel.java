import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * JPanel that specifically draws the
 * manager panels for the hotel manager
 * to perform actions like viewing
 * all reservations
 */
public class ManagerPanel extends JPanel{

    private static final String DEFAULT_MANAGER_NAME = "Manager";

    // Manager name can be set to greet customers by name
    //   in this welcomeLabel
    private JLabel welcomeLabel;

    // Field for the top panel
    private JButton logoutButton;

    // One last label for drawing messages directed to user
    private JLabel messageLabel;

    /**
     * Creates a new ManagerPanel with default name
     * for Manager
     */
    public ManagerPanel() {
        this(DEFAULT_MANAGER_NAME);  // Use default manager name
    }

    /**
     * Creates new ManagerPanel with given managerName
     * for the manager
     * @param managerName name to refer to the customer by
     * @pre managerName is not null; managerName is not empty string
     */
    public ManagerPanel(String managerName) {
        super();

        // TODO implement real manager panel
        setLayout(new BorderLayout());

        // Create top panel which lets manager log out
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(230, 204, 255));
        this.welcomeLabel = new JLabel();
        changeManagerName(managerName);  // Use helper method to set welcomeLabel text
        this.logoutButton = new JButton("Logout");

        topPanel.add(this.logoutButton);
        topPanel.add(this.welcomeLabel);

        // Create middle panel which lets manager perform actions
        JPanel middlePanel = new JPanel();
        middlePanel.setBackground(new Color(204, 153, 255));



        // Add all panels to the customer panel (and message label)
        add(topPanel, BorderLayout.NORTH);
        add(middlePanel, BorderLayout.CENTER);

        this.messageLabel = new JLabel("Any important error messages will appear here", SwingConstants.CENTER);
        this.messageLabel.setForeground(Color.RED);
        this.messageLabel.setBackground(Color.LIGHT_GRAY);
        this.messageLabel.setOpaque(true);
        add(messageLabel, BorderLayout.SOUTH);
    }

    /**
     * Changes welcome greeting in panel to match the given
     * manager name
     * @param newName the name of the manager
     * @pre newName is not null; newName is not empty string
     */
    public void changeManagerName(String newName) {
        if(newName != null && newName.length() > 0) {
            this.welcomeLabel.setText("Welcome, " + newName + "!");
        }
        else {
            // Use default "Customer" to refer to customer when given bad newName
            this.welcomeLabel.setText("Welcome, " + DEFAULT_MANAGER_NAME + "!");
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

    /**
     * Sets the message on the login panel to be the newMessage
     * @param newMessage text to place in the message label
     */
    public void setMessageLabel(String newMessage) {
        if(newMessage != null) {
            this.messageLabel.setText(newMessage);
        }
    }
}
