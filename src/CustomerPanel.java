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

        // Create top panel which lets guest log out
        JPanel topPanel = new JPanel();
        JLabel welcomeLabel = new JLabel("Welcome, Customer!");
        this.logoutButton = new JButton("Logout");

        topPanel.add(this.logoutButton);
        topPanel.add(welcomeLabel);
        add(topPanel, BorderLayout.NORTH);
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
