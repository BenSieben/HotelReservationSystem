import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Manager card which shows the current reservations at the hotel
 */
public class ViewCurrentReservationsManagerCard extends JPanel {

    private ReservationListPanel currentReservationsList;

    public ViewCurrentReservationsManagerCard() {
        // TODO set up
        setLayout(new GridLayout(0, 1));  // 1 component per row
        setOpaque(false);
        this.currentReservationsList = new ReservationListPanel(true);
        add(this.currentReservationsList);
        TitledBorder newBorder = new TitledBorder("View / Cancel Reservations");
        newBorder.setTitleFont(new Font(null, Font.BOLD, 16));
        setBorder(newBorder);
    }
}
