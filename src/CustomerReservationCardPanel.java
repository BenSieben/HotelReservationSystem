import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Class which holds the basic functionality of any
 * given card panel used in the cards of the customer panel
 */
public class CustomerReservationCardPanel extends JPanel {

    // The actual middlePanel is what the main view of each customer reservation card panel
    //   is, which is the primary difference between different cards in the customer panel's customerCards
    private JPanel middlePanel;

    // DirectionalButtons are usually the same for any customer reservation card panel
    private DirectionalButtonCollection directionalButtons;

    public CustomerReservationCardPanel(String previousButtonText, String nextButtonText) {
        super();
        setLayout(new BorderLayout());

        // Set up middle panel and directional buttons
        this.middlePanel = new JPanel();
        this.middlePanel.setBackground(new Color(255, 204, 102));
        this.directionalButtons = new DirectionalButtonCollection(previousButtonText,
                nextButtonText,
                new Color(255, 187, 51));

        // Add components to main panel
        add(this.middlePanel, BorderLayout.CENTER);
        add(this.directionalButtons, BorderLayout.SOUTH);
    }

    /**
     * Sets the middle panel for this CustomerReservationCardPanel
     * @param newMiddlePanel the panel to place in the middle of this CustomerReservationCardPanel
     */
    public void setMiddlePanel(JPanel newMiddlePanel) {
        if(middlePanel != null) {
            this.middlePanel = newMiddlePanel;
        }
    }

    /**
     * Adds previous button listener on this panel's directional button collection
     * @param listener listener to attach to previous button on this panel
     */
    public void addPreviousButtonListener(ActionListener listener) {
        this.directionalButtons.addPreviousButtonListener(listener);
    }

    /**
     * Adds previous button listener on this panel's directional button collection
     * @param listener listener to attach to previous button on this panel
     */
    public void addNextButtonListener(ActionListener listener) {
        this.directionalButtons.addNextButtonListener(listener);
    }
}