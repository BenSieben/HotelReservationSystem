import javax.swing.*;
import java.awt.*;

/**
 * Second middle panel in the cards of the customer panel's CustomerReservationCardPanels,
 * where user picks a room to reserve (and can also see room details after picking
 * rooms)
 */
public class PickRoomCustomerCard extends CustomerReservationCardPanel {

    /**
     * Creates a new PickRoomCustomerCard
     */
    public PickRoomCustomerCard() {
        super("Previous", "Next");

        addBorderForMiddlePanel("New reservation step 2: pick room");
    }

    /**
     * Resets all fields in the panel
     */
    public void resetAllFields() {

    }
}