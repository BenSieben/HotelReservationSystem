import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Manager card which shows information on the "best customer"
 * of the hotel
 */
public class CustomerRankingManagerCard extends JPanel {

    // Array of all the oclumn names in the table
    private static String[] COLUMN_NAMES = {"Customer ID", "First Name", "Last Name", "Booking Count", "Overall Rank"};

    public CustomerRankingManagerCard() {
        setLayout(new GridLayout(0, 1));  // 1 component per row
        setOpaque(false);
        TitledBorder newBorder = new TitledBorder("Customer rankings");
        newBorder.setTitleFont(new Font(null, Font.BOLD, 16));
        setBorder(newBorder);

        // TODO Create content
        Object[][] sampleDetails = {{"ID", "FN", "LN", "BC", "OR"}};
        setRankingsPane(sampleDetails);
    }

    public void setRankingsPane(Object[][] newData) {
        // Make new JTable
        JTable rankingDetails = new JTable(newData, CustomerRankingManagerCard.COLUMN_NAMES);
        JScrollPane rankingDetailsPane = new JScrollPane(rankingDetails,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //rankingDetails.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);  // Don't have many columns, so auto resize is OK
        rankingDetails.setFillsViewportHeight(true);
        rankingDetails.setOpaque(false);

        // Clear old panel
        removeAll();

        // Create new panel
        setLayout(new BorderLayout());
        add(rankingDetailsPane, BorderLayout.CENTER);

        // If the newData is empty, show another screen indicating no reservations found
        if(newData.length == 0) {
            JLabel noReservationsLabel = new JLabel("No customer rankings were found!");
            noReservationsLabel.setHorizontalAlignment(SwingConstants.CENTER);
            removeAll();
            add(noReservationsLabel);
        }

        // Refresh screen
        revalidate();
        repaint();
    }

}
