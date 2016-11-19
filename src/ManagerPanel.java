import javax.swing.*;
import javax.swing.border.TitledBorder;
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

    // Components of action selection for hotel manager
    private JButton viewCurrentReservationsPanelButton;
    private JButton viewArchivedReservationsPanelButton;
    private JButton viewRevenuePanelButton;

    // The middle panel itself
    private JPanel managerCards;

    // All the manager reservation card panels that are in the managerCards panel
    private ViewCurrentReservationsManagerCard viewCurrentReservationsManagerCard;
    private ViewArchivedReservationsManagerCard viewArchivedReservationsManagerCard;
    private ViewRevenueReservationsManagerCard viewRevenueReservationsManagerCard;

    // Constants for identifying the different cards in managerCards
    public static final String CURRENT_RESERVATIONS_PANEL = "Reservations";
    public static final String ARCHIVED_RESERVATIONS_PANEL = "Archives";
    public static final String REVENUE_PANEL = "Revenue";

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
        middlePanel.setLayout(new BorderLayout());
        middlePanel.setBackground(new Color(204, 153, 255));

        JPanel middleTopPanel = new JPanel();
        middleTopPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        middleTopPanel.setOpaque(false);
        this.viewCurrentReservationsPanelButton = new JButton("View Current Reservations");
        this.viewArchivedReservationsPanelButton = new JButton("View Archived Reservations");
        this.viewRevenuePanelButton = new JButton("View Revenue");
        middleTopPanel.add(this.viewCurrentReservationsPanelButton);
        middleTopPanel.add(this.viewArchivedReservationsPanelButton);
        middleTopPanel.add(this.viewRevenuePanelButton);

        this.managerCards = new JPanel();
        this.managerCards.setLayout(new CardLayout());  // Flip through various actions hotel manager can perform
        this.managerCards.setOpaque(false);

        this.viewCurrentReservationsManagerCard = new ViewCurrentReservationsManagerCard();
        this.viewArchivedReservationsManagerCard = new ViewArchivedReservationsManagerCard();
        this.viewRevenueReservationsManagerCard = new ViewRevenueReservationsManagerCard();

        this.managerCards.add(this.viewCurrentReservationsManagerCard, ManagerPanel.CURRENT_RESERVATIONS_PANEL);
        this.managerCards.add(this.viewArchivedReservationsManagerCard, ManagerPanel.ARCHIVED_RESERVATIONS_PANEL);
        this.managerCards.add(this.viewRevenueReservationsManagerCard, ManagerPanel.REVENUE_PANEL);

        middlePanel.add(middleTopPanel, BorderLayout.NORTH);
        middlePanel.add(this.managerCards, BorderLayout.CENTER);

        // Add all panels to the customer panel (and message label)
        add(topPanel, BorderLayout.NORTH);
        add(middlePanel, BorderLayout.CENTER);

        this.messageLabel = new JLabel("Any important messages will appear here", SwingConstants.CENTER);
        this.messageLabel.setForeground(Color.RED);
        this.messageLabel.setBackground(Color.LIGHT_GRAY);
        this.messageLabel.setOpaque(true);
        add(messageLabel, BorderLayout.SOUTH);
    }

    /**
     * Resets all components in the panel
     * to have default values
     */
    public void resetAllFields() {
        this.messageLabel.setText("Any important messages will appear here");
        // TODO also reset the cards
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

    /**
     * Adds a new action listener to listen for the view current reservations button
     * @param listener new action listener to add to view current reservations button
     */
    public void addViewCurrentReservationsPanelButtonListener(ActionListener listener) {
        this.viewCurrentReservationsPanelButton.addActionListener(listener);
    }

    /**
     * Adds a new action listener to listen for the view archived reservations button
     * @param listener new action listener to add to view archived reservations button
     */
    public void addViewArchivedReservationsPanelButtonListener(ActionListener listener) {
        this.viewArchivedReservationsPanelButton.addActionListener(listener);
    }

    /**
     * Adds a new action listener to listen for the view revenue button
     * @param listener new action listener to add to view revenue button
     */
    public void addViewRevenuePanelButtonListener(ActionListener listener) {
        this.viewRevenuePanelButton.addActionListener(listener);
    }

    /**
     * Changes the manager panel's currently shown JPanel to be
     * the JPanel associated with the argument string,
     * newCard (does nothing if newCard is invalid)
     * @param newCard the string associated with the JPanel
     *                to show (ex: ManagerPanel.CURRENT_RESERVATIONS_PANEL)
     */
    public void changeCard(String newCard) {
        CardLayout cl = (CardLayout)(this.managerCards.getLayout());
        if(CURRENT_RESERVATIONS_PANEL.equals(newCard)
                || ARCHIVED_RESERVATIONS_PANEL.equals(newCard)
                || REVENUE_PANEL.equals(newCard)) {
            cl.show(this.managerCards, newCard);
        }
        else {
            //  Tell user that their newCard is unrecognizable
            System.err.println("Error: newCard \"" + newCard + "\" is not recognized by ManagerPanel!");
        }
    }
}
