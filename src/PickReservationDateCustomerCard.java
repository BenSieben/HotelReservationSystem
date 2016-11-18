import javax.swing.*;
import java.awt.*;

/**
 * First middle panel in the cards of the customer panel's CustomerReservationCardPanels,
 * where user picks date start / end for reservation
 */
public class PickReservationDateCustomerCard extends CustomerReservationCardPanel {

    // Start date and end date are (currently) entered via text field
    private JTextField startDay, startTime, endDay, endTime;

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
        startDayPanel.add(startDateLabel);
        startDayPanel.add(this.startDay);
        startDayPanel.setOpaque(false);

        JPanel startTimePanel = new JPanel();
        JLabel startTimeLabel = new JLabel("Check-in time (in 24-hour time format) (HH:MM)");
        // The seconds / milliseconds can be defaulted to zero
        this.startTime = new JTextField(20);
        startTimePanel.add(startTimeLabel);
        startTimePanel.add(this.startTime);
        startTimePanel.setOpaque(false);

        JPanel endDayPanel = new JPanel();
        JLabel endDayLabel = new JLabel("End date (YYYY-MM-DD)");
        this.endDay = new JTextField(20);
        endDayPanel.add(endDayLabel);
        endDayPanel.add(this.endDay);
        endDayPanel.setOpaque(false);

        JPanel endTimePanel = new JPanel();
        JLabel endTimeLabel = new JLabel("Check-out time (in 24-hour time format) (HH:MM)");
        // The seconds / milliseconds can be defaulted to zero
        this.endTime = new JTextField(20);
        endTimePanel.add(endTimeLabel);
        endTimePanel.add(this.endTime);
        endTimePanel.setOpaque(false);

        // Add all components to middle panel in right places
        middlePanel.add(startDayPanel);
        middlePanel.add(startTimePanel);
        middlePanel.add(endDayPanel);
        middlePanel.add(endTimePanel);

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
        this.startTime.setText("");
        this.endDay.setText("");
        this.endTime.setText("");
    }

    /**
     * Returns text currently in start day field
     * @return text currently in start day field
     */
    public String getStartDateText() {
        return this.startDay.getText();
    }

    /**
     * Returns text currently in start time field
     * @return text currently in start time field
     */
    public String getStartTimeText() {
        return this.startTime.getText();
    }

    /**
     * Returns text currently in end day field
     * @return text currently in end day field
     */
    public String getEndDateText() {
        return this.endDay.getText();
    }

    /**
     * Returns text currently in end time field
     * @return text currently in end time field
     */
    public String getEndTimeText() {
        return this.endTime.getText();
    }
}