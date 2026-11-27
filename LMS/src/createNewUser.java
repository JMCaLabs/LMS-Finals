import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatClientProperties;

public class createNewUser extends JFrame{
    private JTextField nameTextField;
    private JTextField idTextField;
    private JPasswordField passPasswordField;
    private JPasswordField checkPassPasswordField;
    private JLabel nameLabel;
    private JLabel idLabel;
    private JButton createNewUserButton;
    private JButton checkPassShowButton;
    private JButton passShowButton;
    private JRadioButton studentRadioButton;
    private JRadioButton teacherRadioButton;
    private JPanel createUserPanel;
    private JButton loginButton;
    private char defaultEchoChar; // set in the constructor, once the form's fields exist

    // ID must look like 12-3456-789
    private static final String ID_PATTERN = "\\d{2}-\\d{4}-\\d{3}";
    // Letters, spaces, hyphens and periods (for middle initials), e.g. "Ryan Rey A. Gotingco"
    private static final String NAME_PATTERN = "[\\p{L} .-]+";

    private void creatingNewUser(){
        List<User> users = Main.getUsers();
        String enteredID = idTextField.getText().trim();
        String enteredName = nameTextField.getText().trim();
        String enteredPassword = new String(passPasswordField.getPassword());
        String confirmPassword= new String(checkPassPasswordField.getPassword());
        User addedUser = null;

//        enteredID.putClientProperty("JTextField.placeholderText", "##-####-##");
//        enteredName.putClientProperty("JTextField.placeholderText", "Enter Name");
        try{
            if(enteredID.isEmpty() || enteredName.isEmpty() || enteredPassword.isEmpty()){
                throw new LogInException.EmptyFieldsException();
            }
            if(!enteredID.matches(ID_PATTERN)){
                throw new LogInException.InvalidIdFormatException();
            }
            if(!enteredName.matches(NAME_PATTERN)){
                throw new LogInException.InvalidNameFormatException();
            }
            if(!enteredPassword.equals(confirmPassword)){
                throw new LogInException.PasswordsDoNotMatchException();
            }
            for(User user : users){
                if(enteredID.equals(user.getID())){
                    throw new LogInException.UserAlreadyExistsException();
                }
            }
            if(studentRadioButton.isSelected()){
                addedUser = new Student(enteredID, enteredName, enteredPassword);
                users.add(addedUser);
            }else if(teacherRadioButton.isSelected()){
                addedUser = new Teacher(enteredID, enteredName, enteredPassword);
                users.add(addedUser);
            }
            if(addedUser == null){
                throw new LogInException.UserTypeNotSelectedException();
            }
            JOptionPane.showMessageDialog(
                    null,
                    "You have successfully created a new " + addedUser.getClass().getSimpleName() + " User"
            );
            new Login(users);
            dispose();
        }catch(LogInException e){
            String message = e.getMessage();
            JOptionPane.showMessageDialog(
                    null,
                    message,
                    "Create Account Error",
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

    public createNewUser(){
        FlatDarkLaf.setup();
        List<User> users = Main.getUsers();
        defaultEchoChar = passPasswordField.getEchoChar();
        setContentPane(createUserPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        idTextField.putClientProperty("JTextField.placeholderText", "##-####-###");
        nameTextField.putClientProperty("JTextField.placeholderText", "(First Name, Middle Initial, Family Name)");

        createNewUserButton.addActionListener(e -> creatingNewUser());
        passShowButton.addActionListener(e -> showPassword(passPasswordField));
        checkPassShowButton.addActionListener(e -> showPassword(checkPassPasswordField));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login(users);
                dispose();
            }
        });
        setVisible(true); // last, once the window is fully set up
    }
}
