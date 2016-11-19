import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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

    // Fields for the top panel
    private JButton logoutButton;
    private JButton makeNewReservationButton;
    private JButton viewReservationsButton;

    // One last label for drawing messages directed to user
    private JLabel messageLabel;

    // JPanel with card layout which holds all the different panels which can appear in the customer panel
    private JPanel customerCards;

    // All the customer reservation card panels that are in the customerCards panel
    private PickReservationDateCustomerCard pickReservationDateCustomerCard;
    private PickRoomCustomerCard pickRoomCustomerCard;
    private SelectGuestsCustomerCard selectGuestsCustomerCard;
    private PaymentCustomerCard paymentCustomerCard;
    private ConfirmReservationCustomerCard confirmReservationCustomerCard;
    private ViewReservationsCustomerCard viewReservationsCustomerCard;

    // Constants for identifying the different cards in customerCards
    public static final String PICK_DATE_PANEL = "0";
    public static final String PICK_ROOM_PANEL = "1";
    public static final String ADD_GUESTS_PANEL = "2";
    public static final String PAYMENT_PANEL = "3";
    public static final String RESERVATION_CONFIRM_PANEL = "4";
    public final static String VIEW_RESERVATIONS_PANEL = "5";

    private int cardIndex; // Keeps track of current card (for responding to directional button presses easily)
    private static final int NUM_MAKE_RESERVATION_CARDS = 5;  // Number of cards that exist (currently) in the card panel of the customer panel

    // JPanel which holds cancel button
    private JPanel cancelButtonPanel;

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
        setLayout(new BorderLayout());

        // Create top panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(255, 221, 153));
        this.welcomeLabel = new JLabel();
        this.welcomeLabel.setFont(new Font(null, Font.BOLD, 20));
        changeCustomerName(customerName);  // Use helper method to set welcomeLabel text

        this.logoutButton = new JButton("Logout");
        this.makeNewReservationButton = new JButton("Make new reservation");
        this.viewReservationsButton = new JButton("View your reservations");

        topPanel.add(this.logoutButton);
        topPanel.add(this.makeNewReservationButton);
        topPanel.add(this.viewReservationsButton);
        topPanel.add(this.welcomeLabel);

        // Create center panel
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BorderLayout());

        // Create cards panel which lets customer perform actions in sequence
        this.customerCards = new JPanel();
        this.customerCards.setLayout(new CardLayout());

        this.pickReservationDateCustomerCard = new PickReservationDateCustomerCard();
        this.pickRoomCustomerCard = new PickRoomCustomerCard();
        this.selectGuestsCustomerCard = new SelectGuestsCustomerCard();
        this.paymentCustomerCard = new PaymentCustomerCard();
        this.confirmReservationCustomerCard = new ConfirmReservationCustomerCard();
        this.viewReservationsCustomerCard = new ViewReservationsCustomerCard(new ReservationListPanel(true));

        this.customerCards.add(this.pickReservationDateCustomerCard, CustomerPanel.PICK_DATE_PANEL);
        this.customerCards.add(this.pickRoomCustomerCard, CustomerPanel.PICK_ROOM_PANEL);
        this.customerCards.add(this.selectGuestsCustomerCard, CustomerPanel.ADD_GUESTS_PANEL);
        this.customerCards.add(this.paymentCustomerCard, CustomerPanel.PAYMENT_PANEL);
        this.customerCards.add(this.confirmReservationCustomerCard, CustomerPanel.RESERVATION_CONFIRM_PANEL);
        this.customerCards.add(this.viewReservationsCustomerCard, CustomerPanel.VIEW_RESERVATIONS_PANEL);

        // Add relevant panels to middle panel
        middlePanel.add(customerCards, BorderLayout.CENTER);
        this.cancelButtonPanel = createCancelButtonPanel();
        middlePanel.add(this.cancelButtonPanel, BorderLayout.SOUTH);

        // Add all panels to the customer panel (and message label)
        add(topPanel, BorderLayout.NORTH);
        add(middlePanel, BorderLayout.CENTER);

        this.messageLabel = new JLabel("Any important messages will appear here", SwingConstants.CENTER);
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
        // On next button, go forward to next panel (but when going to next panel, if we are proceeding
        //   from the final card to first card, we must also submit reservation data to database
        CardLayout layout = (CardLayout)customerCards.getLayout();
        cardIndex = (++cardIndex) % CustomerPanel.NUM_MAKE_RESERVATION_CARDS;  // Increment card index, looping to 0 if necessary
        layout.next(customerCards);  // This moves to next card in sequence, wrapping to front if at last card
    }

    /**
     * Switches main panel on screen to the
     * make new reservation view, where customers
     * can go through a number of panels to
     * make a new reservation
     */
    public void goToMakeNewReservationView() {
        CardLayout layout = (CardLayout)this.customerCards.getLayout();
        layout.show(this.customerCards, CustomerPanel.PICK_DATE_PANEL);
        this.cancelButtonPanel.setVisible(true);
        cardIndex = 0;  // Reset card index back to 0
    }

    /**
     * Switches main panel on screen to switch to the
     * view reservation view, where customers can see
     * previous reservations they have made
     */
    public void goToViewReservationView() {
        CardLayout layout = (CardLayout)this.customerCards.getLayout();
        layout.show(this.customerCards, CustomerPanel.VIEW_RESERVATIONS_PANEL);
        this.cancelButtonPanel.setVisible(false);
    }

    /**
     * Resets all components in the panel
     * to have default values, and jumps back
     * to first panel in the cards
     */
    public void resetAllFields() {
        this.pickReservationDateCustomerCard.resetAllFields();
        this.pickRoomCustomerCard.resetAllFields();
        this.selectGuestsCustomerCard.resetAllFields();
        this.paymentCustomerCard.resetAllFields();
        this.confirmReservationCustomerCard.resetAllFields();
        this.messageLabel.setText("Any important messages will appear here");
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
     * Adds a new ActionListener to listen for the make new reservation button
     * being pressed
     * @param listener the new ActionListener to associate with the make new reservation button
     */
    public void addMakeNewReservationButtonListener(ActionListener listener) {
        this.makeNewReservationButton.addActionListener(listener);
    }

    /**
     * Adds a new ActionListener to listen for the view reservations button
     * being pressed
     * @param listener the new ActionListener to associate with the view reservations button
     */
    public void addViewReservationsButtonListener(ActionListener listener) {
        this.viewReservationsButton.addActionListener(listener);
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
                // On cancel button, reset all fields
                System.err.println("cancel");
                resetAllFields();
            }
        });
        JPanel cancelButtonPanel = new JPanel();
        cancelButtonPanel.add(cancelButton);
        cancelButtonPanel.setBackground(new Color(255, 187, 51));
        return cancelButtonPanel;
    }

    /**
     * Returns the pick reservation date card in this customer panel
     * @return the pick reservation date card in this customer panel
     */
    public PickReservationDateCustomerCard getPickReservationDateCustomerCard() {
        return this.pickReservationDateCustomerCard;
    }

    /**
     * Returns the pick room card in this customer panel
     * @return the pick room card in this customer panel
     */
    public PickRoomCustomerCard getPickRoomCustomerCard() {
        return this.pickRoomCustomerCard;
    }

    /**
     * Returns the select guests card in this customer panel
     * @return the select guests card in this customer panel
     */
    public SelectGuestsCustomerCard getSelectGuestsCustomerCard() {
        return this.selectGuestsCustomerCard;
    }

    /**
     * Returns the payment card in this customer panel
     * @return the payment card in this customer panel
     */
    public PaymentCustomerCard getPaymentCustomerCard(){
        return this.paymentCustomerCard;
    }

    /**
     * Returns the confirm reservation card in this customer panel
     * @return the confirm reservation card in this customer panel
     */
    public ConfirmReservationCustomerCard getConfirmReservationCustomerCard() {
        return this.confirmReservationCustomerCard;
    }

    /**
     * Returns the view reservation card in this customer panel
     * @return the view reservation card in this customer panel
     */
    public ViewReservationsCustomerCard getViewReservationsCustomerCard() {
        return this.viewReservationsCustomerCard;
    }
}
