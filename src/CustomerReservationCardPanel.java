import javax.swing.*;
import javax.swing.border.TitledBorder;
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

    /**
     * Creates a new CustomerReservationCardPanel
     * @param previousButtonText text to put on previous button for this card
     * @param nextButtonText text to put on next button for this card
     */
    public CustomerReservationCardPanel(String previousButtonText, String nextButtonText) {
        super();
        setLayout(new BorderLayout());
        setBackground(new Color(255, 204, 102));

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
        if(newMiddlePanel != null) {
            this.remove(this.middlePanel);
            this.middlePanel = newMiddlePanel;
            this.middlePanel.setOpaque(false);
            this.add(this.middlePanel);
            revalidate();
            repaint();
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

    /**
     * Adds a title border around the current middle panel
     * @param middlePanelDescription text to put in title border
     */
    public void addBorderForMiddlePanel(String middlePanelDescription) {
        TitledBorder newBorder = new TitledBorder(middlePanelDescription);
        newBorder.setTitleFont(new Font(null, Font.BOLD, 16));
        this.middlePanel.setBorder(newBorder);
    }
}