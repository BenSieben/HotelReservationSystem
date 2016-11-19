import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Manager card which shows revenue at the hotel in specified months
 */
public class ViewRevenueReservationsManagerCard extends JPanel {

    private JTextField yearMonthTextField;
    private JButton yearMonthSubmitButton;
    private JScrollPane revenuePane;
    private JTable revenueTable;

    public ViewRevenueReservationsManagerCard() {
        setLayout(new GridLayout(0, 1));  // 1 component per row
        setOpaque(false);
        // TODO add components to view revenue panel
        TitledBorder newBorder = new TitledBorder("View Monthly / Weekly Revenue");
        newBorder.setTitleFont(new Font(null, Font.BOLD, 16));
        setBorder(newBorder);
    }
}
