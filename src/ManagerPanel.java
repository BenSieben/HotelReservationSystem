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

    private JButton logoutButton;

    /**
     * Creates a new ManagerPanel
     */
    public ManagerPanel() {
        super();

        // TODO implement real customer panel
        setLayout(new BorderLayout());

        // Create top panel which lets manager log out
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(230, 204, 255));
        JLabel welcomeLabel = new JLabel("Welcome, Manager!");
        this.logoutButton = new JButton("Logout");

        // Create middle panel which lets manager perform actions
        JPanel middlePanel = new JPanel();
        middlePanel.setBackground(new Color(204, 153, 255));

        // Add all panels to the manager panel
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
