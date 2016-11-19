import javax.swing.*;
import java.awt.*;

/**
 * Fifth middle panel in the cards of the customer panel's CustomerReservationCardPanels,
 * where user sees final receipt of reservation and can finalize their reservation
 */
public class ConfirmReservationCustomerCard extends CustomerReservationCardPanel {

    // The column names used in the JTable on this card
    public static final String[] COLUMN_NAMES = {"Detail", "Value"};

    // The JTable which holds all the reservation details
    private JTable reservationDetailsTable;

    // The JScrollPanel which holds the table with reservation details
    private JScrollPane reservationDetailsPane;

    /**
     * Creates a new ConfirmReservationCustomerCard
     */
    public ConfirmReservationCustomerCard() {
        super("Previous", "Finish");
        resetAllFields();
    }

    /**
     * Resets all fields in the panel
     */
    public void resetAllFields() {
        Object[][] sampleReceipt = {{"Start date / end date/ etc, here", "Values here"}};
        setReservationDetailsPane(sampleReceipt);
    }

    /**
     * Creates a new receipt based on given receipt date
     * @param receiptData information to show in the reservation details pane
     */
    public void setReservationDetailsPane(Object[][] receiptData) {
        // Set up room details pane to hold new details
        this.reservationDetailsTable = new JTable(receiptData, PickRoomCustomerCard.COLUMN_NAMES);
        this.reservationDetailsPane = new JScrollPane(this.reservationDetailsTable);
        this.reservationDetailsTable.setFillsViewportHeight(true);

        // Change middle panel to use this new room details
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new GridLayout(0,  1));  // 1 component per row
        middlePanel.add(this.reservationDetailsPane);
        setMiddlePanel(middlePanel);
        addBorderForMiddlePanel("New reservation step 5: confirm reservation");
    }
}