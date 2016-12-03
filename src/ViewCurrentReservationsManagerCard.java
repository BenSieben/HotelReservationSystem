import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Manager card which shows the current reservations at the hotel
 */
public class ViewCurrentReservationsManagerCard extends JPanel {

    private ReservationListPanel currentReservationsList;

    public ViewCurrentReservationsManagerCard() {
        setLayout(new GridLayout(0, 1));  // 1 component per row
        setOpaque(false);
        this.currentReservationsList = new ReservationListPanel(true, false);
        add(this.currentReservationsList);
        TitledBorder newBorder = new TitledBorder("View / cancel current reservations");
        newBorder.setTitleFont(new Font(null, Font.BOLD, 16));
        setBorder(newBorder);
    }

    /**
     * Adds an action listener to the cancel reservation button on the current reservation list panel
     * @param listener the listener to add to the cancel reservation button
     */
    public void addCancelReservationButtonListener(ActionListener listener) {
        this.currentReservationsList.addCancelReservationButtonListener(listener);
    }

    /**
     * Sets current reservations in the current reservation list panel to match newDetails
     * @param newDetails reservation data to display
     */
    public void setReservationDetailsPane(Object[][] newDetails) {
        this.currentReservationsList.setReservationDetailsPane(newDetails);
    }

    /**
     * Returns currently selected reservation to cancel in the current reservations list panel
     * @return currently selected reservation to cancel in the current reservations list panel
     */
    public String getCurrentlySelectedReservationToCancel() {
        return this.currentReservationsList.getCurrentlySelectedReservationToCancel();
    }
}
