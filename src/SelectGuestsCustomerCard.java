import javax.swing.*;
import java.awt.*;

/**
 * Third middle panel in the cards of the customer panel's CustomerReservationCardPanels,
 * where user picks guests to add to the room they are reserving
 */
public class SelectGuestsCustomerCard extends CustomerReservationCardPanel {

    /**
     * Creates a new SelectGuestsCustomerCard
     */
    public SelectGuestsCustomerCard() {
        super("Previous", "Next");

        addBorderForMiddlePanel("New reservation step 3: add guest(s)");
    }

    /**
     * Resets all fields in the panel
     */
    public void resetAllFields() {

    }
}