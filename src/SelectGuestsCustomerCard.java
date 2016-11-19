import javax.swing.*;
import java.awt.*;

/**
 * Third middle panel in the cards of the customer panel's CustomerReservationCardPanels,
 * where user picks guests to add to the room they are reserving
 */
public class SelectGuestsCustomerCard extends CustomerReservationCardPanel {

    // Field where user enters number of guest(s) to bring in their reservation
    private JTextField numGuests;

    /**
     * Creates a new SelectGuestsCustomerCard
     */
    public SelectGuestsCustomerCard() {
        super("Previous", "Next");

        // Create the middle panel for this card
        JPanel middlePanel = new JPanel();
        JLabel numGuestsLabel = new JLabel("How many guests do you plan to bring with you?");
        this.numGuests = new JTextField(10);
        middlePanel.add(numGuestsLabel);
        middlePanel.add(this.numGuests);
        numGuestsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.numGuests.setHorizontalAlignment(SwingConstants.CENTER);

        // Use middle panel
        setMiddlePanel(middlePanel);
        addBorderForMiddlePanel("New reservation step 3: add guest(s)");
    }

    /**
     * Resets all fields in the panel
     */
    public void resetAllFields() {
        this.numGuests.setText("");
    }

    /**
     * Returns text currently in numGuests text field
     * @return text currently in numGuests text field
     */
    public String getNumGuestsText() {
        return this.numGuests.getText();
    }
}