import javax.swing.*;
import java.awt.event.*;
import java.util.List;
import java.awt.*;

public class Login extends JFrame{
    private JPanel loginPanel;
    private JTextField IDTextField;
    private JPasswordField passwordPasswordField;
    private JButton loginButton;
    private JButton createNewAccountButton;
    private JButton showButton;
    private char defaultEchoChar; // set in the constructor, once the form's fields exist
    User currentUser = null;

    private void logInUser(){

        IDTextField.putClientProperty( "FlatLaf.style", "arc: 100" );
        //IDTextField.putClientProperty("JTextField.leadingIcon", new FlatSVGIcon("icons/user.svg"));
        IDTextField.putClientProperty("FlatLaf.style", "arc: 100; margin: 6,10,6,10");

        passwordPasswordField.putClientProperty("JTextField.placeholderText", "Password");
        passwordPasswordField.putClientProperty("JPasswordField.showRevealButton", true);
        passwordPasswordField.putClientProperty("FlatLaf.style", "arc: 100; margin: 6,10,6,10");


        loginButton.putClientProperty("FlatLaf.style",
                "arc: 100; font: bold; background: #4F46E5; foreground: #ffffff; margin: 8,16,8,16");
        createNewAccountButton.putClientProperty("JButton.buttonType", "borderless");

        getRootPane().setDefaultButton(loginButton); // Enter key logs in
        List<User> users = Main.getUsers();
        String enteredID = IDTextField.getText().trim();
        String enteredPassword = new String(passwordPasswordField.getPassword());
        currentUser = null; // forget any earlier attempt, or a wrong ID could log in as the last user
        try{
            if(enteredID.isEmpty() || enteredPassword.isEmpty()){
                throw new LogInException.EmptyFieldsException();
            }
            for(User user : users){
                if(enteredID.equals(user.getID())){
                    if(enteredPassword.equals(user.getPassword())){
                        currentUser = user;
                    }else{
                        throw new LogInException.CredentialsMismatchException();
                    }
                }
            }
            if(currentUser == null){
                throw new LogInException.UserDNEException();
            }
            JOptionPane.showMessageDialog(
                    null,
                    "Welcome " + currentUser.getID() + " " + currentUser.getClass().getName() + " " + currentUser.getName()
            );
            // Open the dashboard for this kind of user.
        }catch(LogInException e){
            String message = e.getMessage();
            JOptionPane.showMessageDialog(
                    null,
                    message,
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void showPassword(JPasswordField passwordField){
        if(passwordField.getEchoChar() == 0){
            passwordField.setEchoChar(defaultEchoChar);
        }else{
            passwordField.setEchoChar((char) 0);
        }
    }

    public Login(List<User> users){
        defaultEchoChar = passwordPasswordField.getEchoChar();
        setContentPane(loginPanel);
        setTitle("Login");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        IDTextField.putClientProperty("JTextField.placeholderText", "##-####-###");

        loginButton.addActionListener(e -> logInUser());
        showButton.addActionListener(e -> showPassword(passwordPasswordField));
        createNewAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new createNewUser();
                dispose();
            }
        });
        setVisible(true); // last, once the window is fully set up
    }
}
