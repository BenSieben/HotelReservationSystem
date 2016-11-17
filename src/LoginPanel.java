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

    // Fields for the sign up half of the login panel
    private JTextField signUpFirstName;
    private JTextField signUpLastName;
    private JTextField signUpUsername;
    private JPasswordField signUpPassword;
    private JTextField signUpAge;
    private JButton signUpButton;

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

        // Create right half of panel (sign up)
        JPanel signUpPanel = new JPanel();
        signUpPanel.setLayout(new BorderLayout());
        JLabel signUpLabel = new JLabel("Create account", SwingConstants.CENTER);
        signUpLabel.setFont(new Font(null, Font.BOLD, 20));
        signUpLabel.setBackground(new Color(102, 224, 255));
        signUpLabel.setOpaque(true);

        JPanel signUpFieldsPanel = new JPanel();
        signUpFieldsPanel.setLayout(new GridLayout(0, 1));  // 1 component per line
        signUpFieldsPanel.setBackground(new Color(0, 204, 255));
        JLabel signUpFirstNameLabel = new JLabel("First name", SwingConstants.CENTER);
        this.signUpFirstName = new JTextField();
        JLabel signUpLastNameLabel = new JLabel("Last name", SwingConstants.CENTER);
        this.signUpLastName = new JTextField();
        JLabel signUpUsernameLabel = new JLabel("Username", SwingConstants.CENTER);
        this.signUpUsername = new JTextField();
        JLabel signUpPasswordLabel = new JLabel("Password", SwingConstants.CENTER);
        this.signUpPassword = new JPasswordField();
        this.signUpPassword.setEchoChar('*');  // use * instead of typed characters for the password
        JLabel signUpAgeLabel = new JLabel("Age", SwingConstants.CENTER);
        this.signUpAge = new JTextField();

        signUpFieldsPanel.add(signUpFirstNameLabel);
        signUpFieldsPanel.add(this.signUpFirstName);
        signUpFieldsPanel.add(signUpLastNameLabel);
        signUpFieldsPanel.add(this.signUpLastName);
        signUpFieldsPanel.add(signUpUsernameLabel);
        signUpFieldsPanel.add(this.signUpUsername);
        signUpFieldsPanel.add(signUpPasswordLabel);
        signUpFieldsPanel.add(this.signUpPassword);
        signUpFieldsPanel.add(signUpAgeLabel);
        signUpFieldsPanel.add(this.signUpAge);

        this.signUpButton = new JButton("Create account");

        signUpPanel.add(signUpLabel, BorderLayout.NORTH);
        signUpPanel.add(signUpFieldsPanel, BorderLayout.CENTER);
        signUpPanel.add(this.signUpButton, BorderLayout.SOUTH);

        // Add the two panels to the Login panel as well as the message label
        JPanel middleLoginPanel = new JPanel();
        middleLoginPanel.setLayout(new GridLayout(0, 2));  // 2 components per line
        middleLoginPanel.add(signInPanel);
        middleLoginPanel.add(signUpPanel);
        add(middleLoginPanel, BorderLayout.CENTER);

        this.messageLabel = new JLabel("Any important messages will appear here", SwingConstants.CENTER);
        this.messageLabel.setForeground(Color.RED);
        this.messageLabel.setBackground(Color.LIGHT_GRAY);
        this.messageLabel.setOpaque(true);
        add(messageLabel, BorderLayout.SOUTH);
    }

    /**
     * Resets all components in the panel
     * to have default values
     */
    public void resetAllFields() {
        this.signInUsername.setText("");
        this.signInPassword.setText("");
        this.signUpFirstName.setText("");
        this.signUpLastName.setText("");
        this.signUpUsername.setText("");
        this.signUpPassword.setText("");
        this.signUpAge.setText("");
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
     * Get the text currently in sign up first name text field
     * @return the sign up first name text
     */
    public String getSignUpFirstName() {
        return this.signUpFirstName.getText();
    }

    /**
     * Get the text currently in sign up last name text field
     * @return the sign up last name text
     */
    public String getSignUpLastName() {
        return this.signUpLastName.getText();
    }

    /**
     * Get the text currently in sign up username text field
     * @return the sign up username text
     */
    public String getSignUpUsername() {
        return this.signUpUsername.getText();
    }

    /**
     * Get the text currently in sign up password text field
     * @return the sign up password text
     */
    public String getSignUpPassword() {
        return String.valueOf(this.signUpPassword.getPassword());
    }

    /**
     * Get the number currently in sign up age text field
     * @return the sign up age number (-1 in reject cases)
     */
    public int getSignUpAge() {
        String ageText = this.signUpAge.getText();
        try {
            int age = Integer.parseInt(ageText);
            if(age >= 18 && age <= 200) {
                return age;
            }
            return -1;  // If 18 <= age <= 200 is not satisfied, return -1 (i.e., reject the age)
        }
        catch (NumberFormatException ex) {  // If the current age is not a number, give back -1
            return -1;
        }
    }

    /**
     * Add a new ActionListener to listen for action events
     * on the sign up button
     */
    public void addSignUpButtonListener(ActionListener listener) {
        this.signUpButton.addActionListener(listener);
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
