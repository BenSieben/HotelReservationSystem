import javax.swing.*;
import java.awt.*;

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
        this.reservations = reservations;
        add(this.reservations);
    }

    /**
     * Sets the reservations to show in this card
     * @param reservations the ReservationListPanel to show on this card
     */
    public void reloadReservationsList(ReservationListPanel reservations) {
        remove(this.reservations);
        this.reservations = reservations;
        add(this.reservations);
        revalidate();
        repaint();
    }
}