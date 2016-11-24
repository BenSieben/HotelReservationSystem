import javax.swing.*;
import java.awt.*;

/**
 * First middle panel in the cards of the customer panel's CustomerReservationCardPanels,
 * where user picks date start / end for reservation
 */
public class PickReservationDateCustomerCard extends CustomerReservationCardPanel {

    // Start date and end date are (currently) entered via text field
    private JTextField startDay, endDay;

    /**
     * Creates a new PickReservationDateCustomerCard
     */
    public PickReservationDateCustomerCard() {
        super("Previous", "Next");

        // Create a middle panel to set for this card
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new GridLayout(0, 1));  // 1 component per row

        JPanel startDayPanel = new JPanel();
        JLabel startDateLabel = new JLabel("Start date (YYYY-MM-DD)");
        this.startDay = new JTextField(20);
        this.startDay.setText("2016-11-24");
        startDayPanel.add(startDateLabel);
        startDayPanel.add(this.startDay);
        startDayPanel.setOpaque(false);

        JPanel endDayPanel = new JPanel();
        JLabel endDayLabel = new JLabel("End date (YYYY-MM-DD)");
        this.endDay = new JTextField(20);
        this.endDay.setText("2016-11-29");
        endDayPanel.add(endDayLabel);
        endDayPanel.add(this.endDay);
        endDayPanel.setOpaque(false);

        // Add all components to middle panel in right places
        middlePanel.add(startDayPanel);
        middlePanel.add(endDayPanel);

        // Use this middle panel as the new middle panel, and add a border describing it
        setMiddlePanel(middlePanel);
        addBorderForMiddlePanel("New reservation step 1: pick reservation date");
    }

    /**
     * Resets all fields in the pick reservation date
     * customer card
     */
    public void resetAllFields() {
        this.startDay.setText("");
        this.endDay.setText("");
    }

    /**
     * Returns text currently in start day field
     * @return text currently in start day field
     */
    public String getStartDateText() {
        return this.startDay.getText();
    }

    /**
     * Returns text currently in end day field
     * @return text currently in end day field
     */
    public String getEndDateText() {
        return this.endDay.getText();
    }
}