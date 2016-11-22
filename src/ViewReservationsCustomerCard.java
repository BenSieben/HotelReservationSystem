import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Extra middle panel in the cards of the customer panel's CustomerReservationCardPanels,
 * where customer can see all their existing currentReservations
 */
public class ViewReservationsCustomerCard extends JPanel {

    // Current ReservationListPanel shown in this card for current reservations
    private ReservationListPanel currentReservations;

    // Current ReservationListPanel shown in this card for archived reservations
    private ReservationListPanel archivedReservations;

    /**
     * Creates a new ViewReservationsCustomerCard
     */
    public ViewReservationsCustomerCard(ReservationListPanel currentReservations, ReservationListPanel archivedReservations) {
        setBackground(new Color(255, 204, 102));
        setLayout(new GridLayout(0, 1)); // 1 component per row

        // Set up the current reservation panel
        this.currentReservations = currentReservations;
        TitledBorder currentReservationsBorder = new TitledBorder("Your current reservations");
        currentReservationsBorder.setTitleFont(new Font(null, Font.BOLD, 16));
        this.currentReservations.setBorder(currentReservationsBorder);

        // Set up the archived reservation panel
        this.archivedReservations = archivedReservations;
        TitledBorder archivedReservationsBorder = new TitledBorder("Your archived reservations");
        archivedReservationsBorder.setTitleFont(new Font(null, Font.BOLD, 16));
        this.archivedReservations.setBorder(archivedReservationsBorder);

        // Add both reservation panels to this card
        add(this.currentReservations);
        add(this.archivedReservations);
    }

    /**
     * Adds an action listener to the cancel reservation button on the current reservation list panel
     * @param listener the listener to add to the cancel reservation button
     */
    public void addCancelReservationButtonListener(ActionListener listener) {
        this.currentReservations.addCancelReservationButtonListener(listener);
    }

    /**
     * Sets currentReservations in the reservation list panel to match newDetails
     * @param newDetails current reservation data to display
     */
    public void setCurrentReservationDetailsPane(Object[][] newDetails) {
        this.currentReservations.setReservationDetailsPane(newDetails);
    }

    /**
     * Sets archivedReservations in the reservation list panel to match newDetails
     * @param newDetaisls archived reservation data to display
     */
    public void setArchivedReservationsDetailsPant(Object[][] newDetaisls) {
        this.archivedReservations.setReservationDetailsPane(newDetaisls);
    }

    /**
     * Returns currently selected reservation to cancel in the current reservation list panel
     * @return currently selected reservation to cancel in the current reservation list panel
     */
    public String getCurrentlySelectedReservationToCancel() {
        return this.currentReservations.getCurrentlySelectedReservationToCancel();
    }

}