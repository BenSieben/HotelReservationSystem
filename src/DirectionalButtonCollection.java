import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * A collection of two buttons which is used for each "card"
 * in the customer panel to let user go to next step and
 * previous step in making a new reservation
 */
public class DirectionalButtonCollection extends JPanel {

    // The two buttons in the panel are cancel, previous, and next
    private JButton previousButton, nextButton;

    /**
     * Crates a new DirectionalButtonCollection
     * @param previousText text to put on previous button
     * @param nextText text to put on next button
     * @param backgroundColor color to use in background behind the buttons
     */
    public DirectionalButtonCollection(String previousText, String nextText, Color backgroundColor) {
        // Create the JButtons and add them to the DirectionalButtonCollection
        this.previousButton = new JButton(previousText);
        this.nextButton = new JButton(nextText);

        add(previousButton);
        add(nextButton);

        setBackground(backgroundColor);
    }

    /**
     * Adds new listener to listen for when previous button is pressed
     * @param listener new listener to listen for previous button
     */
    public void addPreviousButtonListener(ActionListener listener) {
        this.previousButton.addActionListener(listener);
    }

    /**
     * Adds new listener to listen for when next button is pressed
     * @param listener new listener to listen for next button
     */
    public void addNextButtonListener(ActionListener listener) {
        this.nextButton.addActionListener(listener);
    }
}