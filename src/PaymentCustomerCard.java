import javax.swing.*;
import java.awt.*;

/**
 * Fourth middle panel in the cards of the customer panel's CustomerReservationCardPanels,
 * where user picks payment type for room reservation (and sees reservation cost)
 */
public class PaymentCustomerCard extends CustomerReservationCardPanel {

    /**
     * Creates a new PaymentCustomerCard
     */
    public PaymentCustomerCard() {
        super("Previous", "Next");

        addBorderForMiddlePanel("New reservation step 4: choose payment type");
    }

    /**
     * Resets all fields in the panel
     */
    public void resetAllFields() {

    }
}