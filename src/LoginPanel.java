import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * JPanel that specifically draws the
 * sign in / sign up panels for users
 * to enter their information to in
 * order to login
 */
public class LoginPanel extends JPanel {

    // Fields for the sign in half of the login panel
    private JTextField signInUsername;
    private JPasswordField signInPassword;
    private JButton signInButton;

    // Fields for the create account half of the login panel
    private JTextField createAccountFirstName;
    private JTextField createAccountLastName;
    private JTextField createAccountUsername;
    private JPasswordField createAccountPassword;
    private JTextField createAccountAge;
    private JButton createAccountButton;

    // One last label for drawing messages directed to user
    private JLabel messageLabel;

    public LoginPanel(){
        super();
        setLayout(new BorderLayout());

        // Create left half of panel (sign in)
        JPanel signInPanel = new JPanel();
        signInPanel.setLayout(new BorderLayout());
        JLabel signInLabel = new JLabel("Sign in", SwingConstants.CENTER);
        signInLabel.setFont(new Font(null, Font.BOLD, 20));
        signInLabel.setBackground(new Color(0, 255, 128));
        signInLabel.setOpaque(true);

        JPanel signInFieldsPanel = new JPanel();
        signInFieldsPanel.setLayout(new GridLayout(0, 1));  // 1 component per line
        signInFieldsPanel.setBackground(new Color(0, 204, 102));
        JLabel signInUsernameLabel = new JLabel("Username", SwingConstants.CENTER);
        this.signInUsername = new JTextField();
        JLabel signInPasswordLabel = new JLabel("Password", SwingConstants.CENTER);
        this.signInPassword = new JPasswordField();
        this.signInPassword.setEchoChar('*');  // use * instead of typed characters for the password

        signInFieldsPanel.add(signInUsernameLabel);
        signInFieldsPanel.add(this.signInUsername);
        signInFieldsPanel.add(signInPasswordLabel);
        signInFieldsPanel.add(this.signInPassword);

        this.signInButton = new JButton("Sign in");

        signInPanel.add(signInLabel, BorderLayout.NORTH);
        signInPanel.add(signInFieldsPanel, BorderLayout.CENTER);
        signInPanel.add(this.signInButton, BorderLayout.SOUTH);

        // Create right half of panel (create account)
        JPanel createAccountPanel = new JPanel();
        createAccountPanel.setLayout(new BorderLayout());
        JLabel createAccountLabel = new JLabel("Create account", SwingConstants.CENTER);
        createAccountLabel.setFont(new Font(null, Font.BOLD, 20));
        createAccountLabel.setBackground(new Color(102, 224, 255));
        createAccountLabel.setOpaque(true);

        JPanel createAccountFieldsPanel = new JPanel();
        createAccountFieldsPanel.setLayout(new GridLayout(0, 1));  // 1 component per line
        createAccountFieldsPanel.setBackground(new Color(0, 204, 255));
        JLabel createAccountFirstNameLabel = new JLabel("First name", SwingConstants.CENTER);
        this.createAccountFirstName = new JTextField();
        JLabel createAccountLastNameLabel = new JLabel("Last name", SwingConstants.CENTER);
        this.createAccountLastName = new JTextField();
        JLabel createAccountUsernameLabel = new JLabel("Username", SwingConstants.CENTER);
        this.createAccountUsername = new JTextField();
        JLabel createAccountPasswordLabel = new JLabel("Password", SwingConstants.CENTER);
        this.createAccountPassword = new JPasswordField();
        this.createAccountPassword.setEchoChar('*');  // use * instead of typed characters for the password
        JLabel createAccountAgeLabel = new JLabel("Age", SwingConstants.CENTER);
        this.createAccountAge = new JTextField();

        createAccountFieldsPanel.add(createAccountFirstNameLabel);
        createAccountFieldsPanel.add(this.createAccountFirstName);
        createAccountFieldsPanel.add(createAccountLastNameLabel);
        createAccountFieldsPanel.add(this.createAccountLastName);
        createAccountFieldsPanel.add(createAccountUsernameLabel);
        createAccountFieldsPanel.add(this.createAccountUsername);
        createAccountFieldsPanel.add(createAccountPasswordLabel);
        createAccountFieldsPanel.add(this.createAccountPassword);
        createAccountFieldsPanel.add(createAccountAgeLabel);
        createAccountFieldsPanel.add(this.createAccountAge);

        this.createAccountButton = new JButton("Create account");

        createAccountPanel.add(createAccountLabel, BorderLayout.NORTH);
        createAccountPanel.add(createAccountFieldsPanel, BorderLayout.CENTER);
        createAccountPanel.add(this.createAccountButton, BorderLayout.SOUTH);

        // Add the two panels to the Login panel as well as the message label
        JPanel middleLoginPanel = new JPanel();
        middleLoginPanel.setLayout(new GridLayout(0, 2));  // 2 components per line
        middleLoginPanel.add(signInPanel);
        middleLoginPanel.add(createAccountPanel);
        add(middleLoginPanel, BorderLayout.CENTER);

        this.messageLabel = new JLabel("Any important messages will appear here", SwingConstants.CENTER);
        this.messageLabel.setForeground(Color.RED);
        this.messageLabel.setBackground(Color.LIGHT_GRAY);
        this.messageLabel.setOpaque(true);
        add(messageLabel, BorderLayout.SOUTH);
    }

    /**
     * Clears all text fields in the panel to
     * revert to empty strings and other defaults
     */
    public void clearAllFields() {
        this.signInUsername.setText("");
        this.signInPassword.setText("");
        this.createAccountFirstName.setText("");
        this.createAccountLastName.setText("");
        this.createAccountUsername.setText("");
        this.createAccountPassword.setText("");
        this.createAccountAge.setText("");
        this.messageLabel.setText("Any important error messages will appear here");
    }

    /**
     * Get the text currently in sign in username text field
     * @return the sign in username text
     */
    public String getSignInUsernameText() {
        return this.signInUsername.getText();
    }

    /**
     * Get the text currently in sign in password text field
     * @return the sign in password text field
     */
    public String getSignInPasswordText() {
        return String.valueOf(this.signInPassword.getPassword());
    }

    /**
     * Add a new ActionListener to listen for action events
     * on the sign in button
     */
    public void addSignInButtonListener(ActionListener listener) {
        this.signInButton.addActionListener(listener);
    }

    /**
     * Get the text currently in create account first name text field
     * @return the create account first name text
     */
    public String getCreateAccountFirstName() {
        return this.createAccountFirstName.getText();
    }

    /**
     * Get the text currently in create account last name text field
     * @return the create account last name text
     */
    public String getCreateAccountLastName() {
        return this.createAccountLastName.getText();
    }

    /**
     * Get the text currently in create account username text field
     * @return the create account username text
     */
    public String getCreateAccountUsername() {
        return this.createAccountUsername.getText();
    }

    /**
     * Get the text currently in create account password text field
     * @return the create account password text
     */
    public String getCreateAccountPassword() {
        return String.valueOf(this.createAccountPassword.getPassword());
    }

    /**
     * Get the number currently in create account age text field
     * @return the create account age number
     */
    public int getCreateAccountAge() {
        String ageText = this.createAccountAge.getText();
        try {
            return Integer.parseInt(ageText);
        }
        catch (NumberFormatException ex) {  // If the current age is invalid, give back -1
            return -1;
        }
    }

    /**
     * Add a new ActionListener to listen for action events
     * on the create account button
     */
    public void addCreateAccountButtonListener(ActionListener listener) {
        this.createAccountButton.addActionListener(listener);
    }

    /**
     * Sets the message on the login panel to be the newMessage
     * @param newMessage text to place in the message label
     */
    public void setMessageLabel(String newMessage) {
        if(newMessage != null) {
            this.messageLabel.setText(newMessage);
        }
    }
}
