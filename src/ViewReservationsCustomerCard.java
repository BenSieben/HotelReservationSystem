import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Extra middle panel in the cards of the customer panel's CustomerReservationCardPanels,
 * where customer can see all their existing reservations
 */
public class ViewReservationsCustomerCard extends JPanel {

    // Current ReservationListPanel shown in this card
    private ReservationListPanel reservations;

    /**
     * Creates a new ViewReservationsCustomerCard
     */
    public ViewReservationsCustomerCard(ReservationListPanel reservations) {
        TitledBorder newBorder = new TitledBorder("Your reservations");
        newBorder.setTitleFont(new Font(null, Font.BOLD, 16));
        setBorder(newBorder);
        setBackground(new Color(255, 204, 102));
        setLayout(new GridLayout(0, 1)); // 1 component per row
        this.reservations = reservations;
        add(this.reservations);
    }

    /**
     * Adds an action listener to the cancel reservation button on the reservation list panel
     * @param listener the listener to add to the cancel reservation button
     */
    public void addCancelReservationButtonListener(ActionListener listener) {
        this.reservations.addCancelReservationButtonListener(listener);
    }

    /**
     * Sets reservations in the reservation list panel to match newDetails
     * @param newDetails reservation data to display
     */
    public void setReservationDetailsPane(Object[][] newDetails) {
        this.reservations.setReservationDetailsPane(newDetails);
    }

    /**
     * Returns currently selected reservation to cancel in the reservation list panel
     * @return currently selected reservation to cancel in the reservation list panel
     */
    public String getCurrentlySelectedReservationToCancel() {
        return this.reservations.getCurrentlySelectedReservationToCancel();
    }

}