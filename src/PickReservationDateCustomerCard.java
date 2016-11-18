import javax.swing.*;
import java.awt.*;

/**
 * First middle panel in the cards of the customer panel's CustomerReservationCardPanels,
 * where user picks date start / end for reservation
 */
public class PickReservationDateCustomerCard extends CustomerReservationCardPanel {

    // Start date and end date are (currently) entered via text field
    private JTextField startDate, endDate;

    /**
     * Creates a new PickReservationDateCustomerCard
     */
    public PickReservationDateCustomerCard() {
        super("Previous", "Next");

        // Create a middle panel to set for this card
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new GridLayout(0, 1));  // 1 component per row

        JPanel startDatePanel = new JPanel();
        JLabel startDateLabel = new JLabel("Start Date (YYYY-MM-DD)");
        this.startDate = new JTextField(20);
        startDatePanel.add(startDateLabel);
        startDatePanel.add(this.startDate);
        startDatePanel.setOpaque(false);

        JPanel endDatePanel = new JPanel();
        JLabel endDateLabel = new JLabel("End Date (YYYY-MM-DD)");
        this.endDate = new JTextField(20);
        endDatePanel.add(endDateLabel);
        endDatePanel.add(this.endDate);
        endDatePanel.setOpaque(false);

        // Add all components to middle panel in right places
        middlePanel.add(startDatePanel);
        middlePanel.add(endDatePanel);

        // Use this middle panel as the new middle panel, and add a border describing it
        setMiddlePanel(middlePanel);
        addBorderForMiddlePanel("Step 1: Pick Reservation Date");
        repaint();
    }

    /**
     * Returns text currently in start date field
     * @return text currently in start date field
     */
    public String getStartDateText() {
        return this.startDate.getText();
    }

    /**
     * Returns text currently in end date field
     * @return text currently in end date field
     */
    public String getEndDateText() {
        return this.endDate.getText();
    }
}