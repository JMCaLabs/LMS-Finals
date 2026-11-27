import javax.swing.*;
import java.awt.event.*;
import java.util.List;

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
