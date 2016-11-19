import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Second middle panel in the cards of the customer panel's CustomerReservationCardPanels,
 * where user picks a room to reserve (and can also see room details after picking
 * rooms)
 */
public class PickRoomCustomerCard extends CustomerReservationCardPanel {

    // The middle JPanel
    private JPanel middlePanel;

    // The two halves of this panel both get set via queries, so they are fields that can be changed
    private JScrollPane roomListPane, roomDetailsPane;

    // The array list of room list buttons that are currently in the room list pane
    private ArrayList<JButton> roomListButtons;

    // The JTable which holds last selected room's details
    private JTable roomDetailsTable;

    /**
     * Creates a new PickRoomCustomerCard
     */
    public PickRoomCustomerCard() {
        super("Previous", "Next");

        resetAllFields();
    }

    /**
     * Resets all fields in the panel to default values
     */
    public void resetAllFields() {
        setRoomListPane(new ArrayList<String>());  // Empty array list of rooms is default

        Object[][] sampleDetails = {{"Details go here", "after picking room"}};
        setRoomDetailsPane(sampleDetails);
    }

    /**
     * Sets the room list panel to have a list of buttons
     * which all correspond to rooms available to select
     * details for
     * @param rooms text to place on each button in the room list pane
     */
    public void setRoomListPane(ArrayList<String> rooms) {
        // Set up the room list pane to hold the new buttons
        JPanel roomListPanel = new JPanel();
        roomListPanel.setLayout(new GridLayout(0, 1));  // 1 component per line
        this.roomListButtons = new ArrayList<JButton>();

        for (int i = 0; i < rooms.size(); i++) {  // For each string in rooms, make a new JButton and add it to roomListButtons / roomListPanel
            JButton roomButton = new JButton(rooms.get(i));
            this.roomListButtons.add(roomButton);
            roomListPanel.add(roomButton);
        }

        if(rooms.size() == 0) { // Handle exceptional case when there are no available rooms
            JLabel sorryLabel = new JLabel("It looks like no rooms are available in this time frame. Sorry!");
            sorryLabel.setHorizontalAlignment(SwingConstants.CENTER);
            roomListPanel.add(sorryLabel);
        }

        this.roomListPane = new JScrollPane(roomListPanel);

        // Change middle panel to use this new list of buttons
        this.middlePanel = new JPanel();
        this.middlePanel.setLayout(new GridLayout(0, 2)); // 2 elements per line
        this.middlePanel.add(this.roomListPane);
        if(this.roomDetailsPane != null) { // Now add back the table (if it exists)
            this.middlePanel.add(this.roomDetailsPane);
        }
        else {
            this.middlePanel.add(new JPanel());
        }
        setMiddlePanel(this.middlePanel);
        addBorderForMiddlePanel("New reservation step 2: pick room");
    }

    /**
     * Maps the input array list of action listeners to the same-indexed
     * button in the room list pane
     * @param listeners list of action listeners to attach to the same-indexed
     *                  buttons in the room list pane
     */
    public void setRoomListPaneListeners(ArrayList<ActionListener> listeners) {
        for (int i = 0; i < listeners.size(); i++) {
            this.roomListButtons.get(i).addActionListener(listeners.get(i));
        }
    }


    /**
     * Creates new room details pane according to row data and
     * column names
     * @param rowData data to show in the details pane
     */
    public void setRoomDetailsPane(Object[][] rowData) {
        // Set up room details pane to hold new details
        String[] columnNames = {"Detail", "Value"};
        this.roomDetailsTable = new JTable(rowData, columnNames);
        this.roomDetailsPane = new JScrollPane(this.roomDetailsTable);
        this.roomDetailsTable.setFillsViewportHeight(true);

        // Change middle panel to use this new room details
        this.middlePanel = new JPanel();
        this.middlePanel.setLayout(new GridLayout(0,  2));  // 2 components per row
        if(this.roomListPane != null) {
            this.middlePanel.add(this.roomListPane);
        }
        else {
            this.middlePanel.add(new JPanel());
        }
        this.middlePanel.add(this.roomDetailsPane);
        setMiddlePanel(this.middlePanel);
        addBorderForMiddlePanel("New reservation step 2: pick room");
    }

    /**
     * Returns how many rooms are currently shown
     * in the room list pane
     * @return number of rooms in room list pane
     */
    public int getNumberOfRooms() {
        return roomListButtons.size();
    }


    /**
     * Returns a map of all details of the last selected room pointing
     * to their actual values
     * @return a map of details pointing to their values for the last selected room
     */
    public HashMap<String, Object> getLastSelectedRoomDetails() {
        HashMap<String, Object> details = new HashMap<String, Object>();
        for (int i = 0; i < this.roomDetailsTable.getRowCount(); i++) {
            details.put((String)this.roomDetailsTable.getValueAt(i, 0), this.roomDetailsTable.getValueAt(i, 1));
        }
        return details;
    }
}