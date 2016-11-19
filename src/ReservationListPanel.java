import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Panel which holds a single reservation receipt (in
 * a JTable). This view is useful in both showing a customer
 * their final receipt before finalizing their order
 * and letting a customer / manager see a list of
 * reservations (where these reservations can be cancelled)
 */
public class ReservationListPanel extends JPanel {

    // Column names for the table in this panel
    public static final String[] COLUMN_NAMES = {"Booking ID", "First Name", "Last Name", "Start Time", "End Time", "# Guests",
            "Daily Cost", "Room Type", "Floor", "Capacity", "Beds", "Bathrooms", "Has Windows", "Smoking Allowed"};

    // Whether or not reservations are cancellable (whether or not
    //   cancel buttons will be drawn / get to have action listeners added)
    private boolean reservationsAreCancellable;

    // The table in this reservation list panel (and the scroll pane it is in)
    private JTable reservationDetails;
    private JScrollPane reservationDetailsPane;

    // The cancel button below the reservation table to cancel a reservation, and a combo box to pick a reservation to cancel
    private JButton cancelReservationButton;
    private JComboBox<String> cancelComboBox;

    /**
     * Constructs a new reservation list panel
     * @param reservationsAreCancellable whether or not the listed reservations are cancellable
     */
    public ReservationListPanel(boolean reservationsAreCancellable) {
        setLayout(new BorderLayout());
        setOpaque(false);

        this.reservationsAreCancellable = reservationsAreCancellable;
        Object[][] sampleDetails = {{"ID", "FN", "LN", "ST", "ET", "#G", "$DC", "RT", "F", "C", "Be", "Ba", "HW", "SA"}};
        setReservationDetailsPane(sampleDetails);
    }

    /**
     * Changes the reservations listed in the table (also adjusts combo box to reflect
     * these new reservations)
     * @param newDetails
     */
    public void setReservationDetailsPane(Object[][] newDetails) {
        // Make new JTable
        this.reservationDetails = new JTable(newDetails, ReservationListPanel.COLUMN_NAMES);
        this.reservationDetailsPane = new JScrollPane(this.reservationDetails);
        this.reservationDetails.setFillsViewportHeight(true);
        this.reservationDetails.setOpaque(false);

        // Clear old panel
        removeAll();

        // Create new panel
        setLayout(new BorderLayout());
        add(this.reservationDetailsPane, BorderLayout.CENTER);

        // Set up new combo box (if necessary)
        if(reservationsAreCancellable) {
            add(createCancelPanel(generateCancelSelectionsFromNewDetails(newDetails)), BorderLayout.SOUTH);
        }

        // If the newDetails is empty, show another screen indicating no reservations found
        if(newDetails.length == 0) {
            JLabel noReservationsLabel = new JLabel("No reservations were found!");
            noReservationsLabel.setHorizontalAlignment(SwingConstants.CENTER);
            removeAll();
            add(noReservationsLabel);
        }

        // Refresh screen
        revalidate();
        repaint();
    }

    /**
     * Takes the newDetails to be put in the JTable on the panel to produce
     * a String array of items to put into the new combo box to reflect the
     * new JTable contents
     * @param newDetails the new content of the JTable in this panel
     * @return a String array of items to put in new combo box
     */
    private String[] generateCancelSelectionsFromNewDetails(Object[][] newDetails) {
        String[] comboItems = new String[newDetails.length];
        for (int i = 0; i < newDetails.length; i++) {
            String comboItemText = (String)newDetails[i][0];  // Grab first element from each inner array (the booking ID)
            comboItems[i] = comboItemText;
        }
        return comboItems;
    }

    /**
     * Creates a new cancel panel which has a cancel button and combo box
     * to pick a reservation to cancel
     * @param cancelSelections array containing all cancel reservation options
     * @return a JPanel containing necessary elements to cancel reservations
     */
    private JPanel createCancelPanel(String[] cancelSelections) {
        JPanel cancelPanel = new JPanel();
        cancelPanel.setOpaque(false);
        if(this.cancelComboBox != null) {
            this.cancelComboBox.removeAllItems();
            for (int i = 0; i < cancelSelections.length; i++) {
                this.cancelComboBox.addItem(cancelSelections[i]);
            }
        }
        else {
            this.cancelComboBox = new JComboBox<String>(cancelSelections);
        }
        if(this.cancelReservationButton == null) {
            this.cancelReservationButton = new JButton("Cancel selected reservation (in combo box)");
        }

        cancelPanel.add(this.cancelComboBox);
        cancelPanel.add(this.cancelReservationButton);
        return cancelPanel;
    }

    /**
     * Adds an action listener for the cancel reservation button
     * @param listener action listener to listen on the cancel reservation button
     */
    public void addCancelReservationButtonListener(ActionListener listener) {
        if(this.cancelReservationButton != null) {
            this.cancelReservationButton.addActionListener(listener);
        }
    }

    /**
     * Returns the currently selected reservation to delete from the list
     * of reservations
     * @return null if reservations are cancellable (no combo box exists in that
     * version), or else the currently selected string in the combo box selecting
     * the reservation to cancel
     */
    public String getCurrentlySelectedReservationToCancel() {
        if(this.cancelComboBox != null) {
            return (String)this.cancelComboBox.getSelectedItem();
        }
        return null;
    }
}
