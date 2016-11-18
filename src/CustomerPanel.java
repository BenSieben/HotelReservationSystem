import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
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

    // One last label for drawing messages directed to user
    private JLabel messageLabel;

    // JPanel with card layout which holds all the different panels which can appear in the customer panel
    private JPanel customerCards;

    // All the customer reservation card panels that are in the customerCards panel
    private PickReservationDateCustomerCard reservationDatePanel;

    // Constants for identifying the different cards in customerCards
    public static final String PICK_DATE_PANEL = "0";

    private int cardIndex; // Keeps track of current card (for responding to directional button presses easily)
    private static final int NUM_CARDS = 1;  // Number of cards that exist (currently) in the card panel of the customer panel

    /**
     * Creates a new CustomerPanel with default name
     * for Customer
     */
    public CustomerPanel() {
        this(DEFAULT_CUSTOMER_NAME);  // Use default customer name
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
        this.welcomeLabel.setFont(new Font(null, Font.BOLD, 20));
        changeCustomerName(customerName);  // Use helper method to set welcomeLabel text
        this.logoutButton = new JButton("Logout");

        topPanel.add(this.logoutButton);
        topPanel.add(this.welcomeLabel);

        // Create center panel
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BorderLayout());

        // Create cards panel which lets customer perform actions in sequence
        this.customerCards = new JPanel();
        this.customerCards.setLayout(new CardLayout());

        this.reservationDatePanel = new PickReservationDateCustomerCard();
        this.customerCards.add(this.reservationDatePanel, CustomerPanel.PICK_DATE_PANEL);

        // Add relevant panels to middle panel
        middlePanel.add(customerCards, BorderLayout.CENTER);
        JPanel cancelButtonPanel = createCancelButtonPanel();
        middlePanel.add(cancelButtonPanel, BorderLayout.SOUTH);

        // Add all panels to the customer panel (and message label)
        add(topPanel, BorderLayout.NORTH);
        add(middlePanel, BorderLayout.CENTER);

        this.messageLabel = new JLabel("Any important error messages will appear here", SwingConstants.CENTER);
        this.messageLabel.setForeground(Color.RED);
        this.messageLabel.setBackground(Color.LIGHT_GRAY);
        this.messageLabel.setOpaque(true);
        add(messageLabel, BorderLayout.SOUTH);

        cardIndex = 0; // 0 is starting card index
    }

    /**
     * Sends the customerCards panel back by one card
     * (if already on the first panel, nothing happens)
     */
    public void goToPreviousCard() {
        System.err.println("Previous");
        // Go back to previous panel (but do not wrap from first panel to last panel)
        CardLayout layout = (CardLayout)customerCards.getLayout();
        // Decrement cardIndex, but do not go behind "0" card.
        // This depends on precondition that all cards are named in numerical sequence
        String nextCardID = Math.max(--cardIndex, 0) + "";
        layout.show(customerCards, nextCardID);
    }

    /**
     * Sends the customerCards panel forward by one
     * card (will wrap around to front card if going to "next"
     * card from the last card)
     */
    public void goToNextCard() {
        System.err.println("next");
        // On next button, go forward to next panel (but when going to next panel, if we are proceeding
        //   from the final card to first card, we must also submit reservation data to database
        CardLayout layout = (CardLayout)customerCards.getLayout();
        cardIndex = (++cardIndex) % CustomerPanel.NUM_CARDS;  // Increment card index, looping to 0 if necessary
        layout.next(customerCards);  // This moves to next card in sequence, wrapping to front if at last card
    }

    /**
     * Resets all components in the panel
     * to have default values
     */
    public void resetAllFields() {
        changeCustomerName(DEFAULT_CUSTOMER_NAME);
        this.messageLabel.setText("Any important error messages will appear here");
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
     * Creates a new JButton which has an action listener
     * to totally reset the customerCards and send
     * user back to first view and puts it in a
     * FlowLayout JPanel
     * @return JPanel containing a cancel button
     */
    private JPanel createCancelButtonPanel() {
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // On cancel button, jump back to first panel and reset all fields
                System.err.println("cancel");
                CardLayout layout = (CardLayout)customerCards.getLayout();
                layout.show(customerCards, PICK_DATE_PANEL);
                resetAllFields();
            }
        });
        JPanel cancelButtonPanel = new JPanel();
        cancelButtonPanel.add(cancelButton);
        cancelButtonPanel.setBackground(new Color(255, 187, 51));
        return cancelButtonPanel;
    }
}
