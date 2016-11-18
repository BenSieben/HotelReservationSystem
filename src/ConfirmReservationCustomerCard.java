import javax.swing.*;
import java.awt.*;

/**
 * Fifth middle panel in the cards of the customer panel's CustomerReservationCardPanels,
 * where user sees final receipt of reservation and can finalize their reservation
 */
public class ConfirmReservationCustomerCard extends CustomerReservationCardPanel {

    /**
     * Creates a new ConfirmReservationCustomerCard
     */
    public ConfirmReservationCustomerCard() {
        super("Previous", "Next");

        addBorderForMiddlePanel("New reservation step 4: choose payment type");
    }

    /**
     * Resets all fields in the panel
     */
    public void resetAllFields() {

    }
}