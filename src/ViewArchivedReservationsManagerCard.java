import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Manager card which shows the archived reservations at the hotel
 */
public class ViewArchivedReservationsManagerCard extends JPanel {

    private ReservationListPanel archivedReservationsList;

    public ViewArchivedReservationsManagerCard() {
        setLayout(new GridLayout(0, 1));  // 1 component per row
        setOpaque(false);
        this.archivedReservationsList = new ReservationListPanel(false);
        add(this.archivedReservationsList);
        TitledBorder newBorder = new TitledBorder("View reservation archive");
        newBorder.setTitleFont(new Font(null, Font.BOLD, 16));
        setBorder(newBorder);
    }

    /**
     * Sets archived reservations in the archived reservation list panel to match newDetails
     * @param newDetails reservation data to display
     */
    public void setReservationDetailsPane(Object[][] newDetails) {
        this.archivedReservationsList.setReservationDetailsPane(newDetails);
    }
}
