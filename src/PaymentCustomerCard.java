import javax.swing.*;
import java.awt.*;

/**
 * Fourth middle panel in the cards of the customer panel's CustomerReservationCardPanels,
 * where user picks payment type for room reservation (and sees reservation cost)
 */
public class PaymentCustomerCard extends CustomerReservationCardPanel {

    // This label shows the user the total cost of their room reservation for their indicated time period
    private JLabel totalCost;

    // The combo box holds all accepted payment types
    private JComboBox<String> paymentTypes;

    /**
     * Creates a new PaymentCustomerCard
     */
    public PaymentCustomerCard() {
        super("Previous", "Next");
        resetAllFields();
    }

    /**
     * Resets all fields in the panel
     */
    public void resetAllFields() {
        String[] defaultPaymentTypes = {"Payment types should be loaded here"};
        resetAllFields(defaultPaymentTypes);
    }

    /**
     * Resets all fields in the panel, setting the payment types to hold
     * whatever is in the payment types array
     * @param paymentTypes the payment types to show in the payment types combo box
     */
    private void resetAllFields(String[] paymentTypes) {
        // Create middle panel to hold all components
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new GridLayout(0, 1));

        // Create components to add to middle panel
        JPanel costPanel = new JPanel();
        costPanel.setOpaque(false);
        JLabel totalCostLabel = new JLabel("Total Cost: ");
        this.totalCost = new JLabel("Cost goes here!");
        costPanel.add(totalCostLabel);
        costPanel.add(this.totalCost);

        JPanel paymentPanel = new JPanel();
        paymentPanel.setOpaque(false);
        JLabel paymentLabel = new JLabel("Please pick a payment type: ");

        this.paymentTypes = new JComboBox<String>(paymentTypes);
        paymentPanel.add(paymentLabel);
        paymentPanel.add(this.paymentTypes);

        // Add components to middle panel
        middlePanel.add(costPanel);
        middlePanel.add(paymentPanel);

        // Set middlePanel as the middlePanel for this panel
        setMiddlePanel(middlePanel);
        addBorderForMiddlePanel("New reservation step 4: choose payment type");
    }

    /**
     * Sets the total cost to display as whatever is in totalCostText
     * @param totalCostText cost to display to user (be sure to include monetary unit!)
     */
    public void setTotalCost(String totalCostText) {
        if(totalCostText != null) {
            this.totalCost.setText(totalCostText);
        }
    }

    /**
     * Sets the payment types that the user can pick from
     * @param paymentTypes
     */
    public void setPaymentTypes(String[] paymentTypes) {
        this.paymentTypes.removeAllItems();
        for (int i = 0; i < paymentTypes.length; i++) {
            this.paymentTypes.addItem(paymentTypes[i]);
        }
    }

    /**
     * Returns the total cost of the reservation
     * @return the total cost of the reservation
     */
    public String getTotalCostText() {
        return this.totalCost.getText();
    }

    /**
     * Returns the currently selected payment type in the
     * payment type combo box
     * @return currently selected payment type
     */
    public String getCurrentlySelectedPaymentType() {
        return (String)this.paymentTypes.getSelectedItem();
    }
}