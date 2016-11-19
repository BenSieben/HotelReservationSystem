import javax.swing.*;
import java.util.ArrayList;

/**
 * Panel which holds a single reservation receipt (in
 * a JTable). This view is useful in both showing a customer
 * their final receipt before finalizing their order
 * and letting a customer / manager see a list of
 * reservations
 */
public class ReservationListPanel extends JPanel {

    // All the table(s) in this reservation list panel
    private ArrayList<JTable> reservationDetails;

    public ReservationListPanel() {

    }
}
