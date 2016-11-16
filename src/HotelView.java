import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Scanner;

/**
 * A JFrame used by the program to display
 * the "view" of the program
 */
public class HotelView extends JFrame {

    // The different panels which can show up in the view
    private LoginPanel loginPanel;
    private GuestPanel guestPanel;
    private ManagerPanel managerPanel;

    // The CardLayout JPanel and constants to refer to each of the panels the CardLayout JPanel holds
    private JPanel cards; //  The CardLayout JPanel which holds all the panels for the frame
    public final static String LOGIN_PANEL = "Login Panel";
    public final static String GUEST_PANEL = "Guest Panel";
    public final static String MANAGER_PANEL = "Manager Panel";

    /**
     * Constructs a brand new HotelView
     */
    public HotelView() {
        super();

        // Set up some constants for the JFrame
        setTitle("Hotel Reservation System");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);  // same as JFrame.EXIT_ON_CLOSE
        setPreferredSize(new Dimension(1000, 700));
        setMinimumSize(new Dimension(1000, 700));
        setLocationRelativeTo(null); // will center the frame when it initially opens

        // Add all the panels to the cards JPanel, which uses CardLayout to flip
        //   between the views (this behavior is facilitated through the changeCard
        //   function below)
        this.cards = new JPanel();
        this.cards.setLayout(new CardLayout());

        this.loginPanel = new LoginPanel();
        this.guestPanel = new GuestPanel();
        this.managerPanel = new ManagerPanel();

        this.cards.add(this.loginPanel, HotelView.LOGIN_PANEL);
        this.cards.add(this.guestPanel, HotelView.GUEST_PANEL);
        this.cards.add(this.managerPanel, HotelView.MANAGER_PANEL);

        add(this.cards);

        // Finalize some constants for the JFrame to make it visible
        pack();
        setVisible(true);
    }

    /**
     * Changes the frame's currently shown JPanel to be
     * the JPanel associated with the argument string,
     * newCard (does nothing if newCard is invalid)
     * @param newCard the string associated with the JPanel
     *                to show (ex: HotelView.LOGIN_PANEL)
     */
    public void changeCard(String newCard) {
        CardLayout cl = (CardLayout)(this.cards.getLayout());
        if(LOGIN_PANEL.equals(newCard)
                || GUEST_PANEL.equals(newCard)
                || MANAGER_PANEL.equals(newCard)) {
            cl.show(this.cards, newCard);
        }
        else {
            //  Tell user that their newCard is unrecognizable
            System.err.println("Error: newCard \"" + newCard + "\" is not recognized by HotelView!");
        }
    }

    public HashMap<String, Object> signUpView() {
        try {
            String first, last, username, password = null;
            int age = 0;
            HashMap<String, Object> userInput = new HashMap<String, Object>();
            Scanner scanner = new Scanner(System.in);

            System.out.println("--------SignUp-------");

            System.out.println("Enter First Name:");
            first = scanner.nextLine();

            System.out.println("Enter Last Name:");
            last = scanner.nextLine();

            System.out.println("Enter Username:");
            username = scanner.nextLine();

            System.out.println("Enter Password:");
            password = scanner.nextLine();

            System.out.println("Enter Age:");
            age = Integer.parseInt(scanner.nextLine());


            userInput.put("first_name", first);
            userInput.put("last_name", last);
            userInput.put("username", username);
            userInput.put("password", password);
            userInput.put("age", age);

            return userInput;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public HashMap<String, Object> signInView() {
        try {
            String username, password = null;
            HashMap<String, Object> userInput = new HashMap<String, Object>();
            Scanner scanner = new Scanner(System.in);

            System.out.println("--------SignIn-------");

            System.out.println("Enter Username:");
            username = scanner.nextLine();

            System.out.println("Enter Password:");
            password = scanner.nextLine();


            userInput.put("username", username);
            userInput.put("password", password);

            return userInput;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void displayError(String arg) {
        System.out.println("Error! " + arg);
    }

    public void displaySuccess(String arg) {
        System.out.println("Success! " + arg);
    }

    /**
     * Returns the login panel attached to the view
     * @return the login panel attached to the view
     */
    public LoginPanel getLoginPanel() {
        return this.loginPanel;
    }

    /**
     * Returns the guest panel attached to the view
     * @return the guest panel attached to the view
     */
    public GuestPanel getGuestPanel() {
        return this.guestPanel;
    }

    /**
     * Returns the manager panel attached to the view
     * @return the manager panel attached to the view
     */
    public ManagerPanel getManagerPanel() {
        return this.managerPanel;
    }
}
