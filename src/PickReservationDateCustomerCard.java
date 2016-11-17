import java.awt.*;

/**
 * First middle panel in the cards of the customer panel's CustomerReservationCardPanels,
 * where user picks date start / end for reservation
 */
public class PickReservationDateCustomerCard extends CustomerReservationCardPanel {

    /**
     * Creates a new PickReservationDateCustomerCard
     */
    public PickReservationDateCustomerCard() {
        super("Previous", "Next");

        setBackground(Color.BLACK);
    }
}