import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * JPanel that specifically draws the
 * guest panels for guests
 * to perform actions like making reservations
 */
public class GuestPanel extends JPanel {

    private JButton logoutButton;

    /**
     * Creates a new GuestPanel
     */
    public GuestPanel() {
        super();

        // TODO implement real guest panel

        JLabel header = new JLabel("Welcome, Guest!");

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
