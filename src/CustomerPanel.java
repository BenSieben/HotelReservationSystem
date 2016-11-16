import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * JPanel that specifically draws the
 * customer panels for customers
 * to perform actions like making reservations
 */
public class CustomerPanel extends JPanel {

    private JButton logoutButton;

    /**
     * Creates a new CustomerPanel
     */
    public CustomerPanel() {
        super();

        // TODO implement real customer panel
        setLayout(new BorderLayout());

        // Create top panel which lets customer log out
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(255, 221, 153));
        JLabel welcomeLabel = new JLabel("Welcome, Customer!");
        this.logoutButton = new JButton("Logout");

        // Create middle panel which lets customer perform actions
        JPanel middlePanel = new JPanel();
        middlePanel.setBackground(new Color(255, 204, 102));

        // Add all panels to the customer panel
        topPanel.add(this.logoutButton);
        topPanel.add(welcomeLabel);
        add(topPanel, BorderLayout.NORTH);
        add(middlePanel, BorderLayout.CENTER);
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
