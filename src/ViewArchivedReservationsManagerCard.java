import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Manager card which shows the archived reservations at the hotel
 */
public class ViewArchivedReservationsManagerCard extends JPanel {

    private ReservationListPanel archivedReservationsList;

    public ViewArchivedReservationsManagerCard() {
        // TODO set up panel
        setLayout(new GridLayout(0, 1));  // 1 component per row
        setOpaque(false);
        this.archivedReservationsList = new ReservationListPanel(false);
        add(this.archivedReservationsList);
        TitledBorder newBorder = new TitledBorder("View Reservation Archive");
        newBorder.setTitleFont(new Font(null, Font.BOLD, 16));
        setBorder(newBorder);
    }
}
