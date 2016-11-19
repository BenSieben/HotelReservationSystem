import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Manager card which shows revenue at the hotel in specified months
 */
public class ViewRevenueManagerCard extends JPanel {

    // The column names used in the JTable on this card
    public static final String[] COLUMN_NAMES = {"Time Range", "Revenue"};

    // Top components
    private JTextField yearMonthTextField;
    private JButton yearMonthSubmitButton;

    // Middle panel (the JPane / JTable components don't need to be accessible after creation)
    private JPanel middlePanel;

    public ViewRevenueManagerCard() {
        setLayout(new BorderLayout());
        setOpaque(false);

        // Create top panel in this view
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(0, 1));  // 1 component per row
        topPanel.setOpaque(false);

        JPanel enterYearPanel = new JPanel();
        enterYearPanel.setOpaque(false);
        JLabel yearMonthLabel = new JLabel("Enter year / month to get revenue of (YYYY-MM): ");
        this.yearMonthTextField = new JTextField(10);
        enterYearPanel.add(yearMonthLabel);
        enterYearPanel.add(this.yearMonthTextField);

        this.yearMonthSubmitButton = new JButton("Show revenue of entered date");

        topPanel.add(enterYearPanel);
        topPanel.add(this.yearMonthSubmitButton);

        // Create sample middle panel in this view
        Object[][] sampleData = {{"Revenues for month / weeks goes here", "after entering valid date"}};
        setRevenuePane(sampleData);

        // Add top panel to this view (the setRevenue call just above adds the middle panel)
        add(topPanel, BorderLayout.NORTH);

        TitledBorder newBorder = new TitledBorder("View monthly / weekly revenue");
        newBorder.setTitleFont(new Font(null, Font.BOLD, 16));
        setBorder(newBorder);
    }

    /**
     * Resets the panel's fields back to defaults
     */
    public void resetAllFields() {
        this.yearMonthTextField.setText("");
        Object[][] sampleData = {{"Revenues for month / weeks goes here", "after entering valid date"}};
        setRevenuePane(sampleData);
    }

    /**
     * Returns the text currently in the year month text field on this panel
     * @return the text currently in the year month text field on this panel
     */
    public String getYearMonthText() {
        return this.yearMonthTextField.getText();
    }

    /**
     * Adds a new action listener to the year month submit button
     * @param listener action listener to add to the year month submit button
     */
    public void addYearMonthSubmitButtonListener(ActionListener listener) {
        this.yearMonthSubmitButton.addActionListener(listener);
    }

    /**
     * Creates new revenue pane according to row data and
     * column names
     * @param rowData data to show in the revenue pane
     */
    public void setRevenuePane(Object[][] rowData) {
        // Remove old revenue pane (if it exists)
        if(this.middlePanel != null) {
            remove(this.middlePanel);
        }

        // Set up revenue pane to hold new details
        JTable revenueTable = new JTable(rowData, ViewRevenueManagerCard.COLUMN_NAMES);
        JScrollPane revenuePane = new JScrollPane(revenueTable);
        revenueTable.setFillsViewportHeight(true);

        // Change middle panel to use this new revenue data
        this.middlePanel = new JPanel();
        middlePanel.setLayout(new GridLayout(0, 1));  // 1 component per row
        middlePanel.setOpaque(false);

        this.middlePanel.add(revenuePane);

        // Use the new middle panel to display
        add(this.middlePanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}
