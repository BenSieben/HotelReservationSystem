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

        // TODO implement real manager panel

        JLabel header = new JLabel("Welcome, Manager!");
        this.logoutButton = new JButton("Logout");

        add(header);
        add(this.logoutButton);
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
