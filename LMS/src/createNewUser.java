import javax.security.auth.login.LoginException;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;

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
    private void creatingNewUser(List<User> users){
        String enteredID = idTextField.getText().trim();
        String enteredName = nameTextField.getText();
        String enteredPassword = new String(passPasswordField.getPassword());
        String confirmPassword= new String(passPasswordField.getPassword());
        User addedUser = null;
        try{
            if(!enteredPassword.equals(confirmPassword)){
                throw new Exception();
            }
            for(User user : users){
                if(enteredID.equals(user.getID())){
                    throw new Exception();
                }
            }
            if(studentRadioButton.isSelected()){
                addedUser = new Student(enteredID, enteredName, enteredPassword);
                users.add(addedUser);
            }else if(teacherRadioButton.isSelected()){
                addedUser = new Teacher(enteredID, enteredName, enteredPassword);
                users.add(addedUser);
            }
            JOptionPane.showMessageDialog(
                    null,
                    "You have successfully created a new " + addedUser.getClass().getName() + " User"
            );
            new LogIn(users);
            this.dispose();
        }catch(Exception e){
            System.out.println("Error");
        }
    }

    private void showPassword(JPasswordField passwordField){
        if(passwordField.getEchoChar() == 0){
            passwordField.setEchoChar('*');
        }else{
            passwordField.setEchoChar((char) 0);
        }
    }

    public createNewUser(List<User> users){
        setContentPane(createUserPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        createNewUserButton.addActionListener(e -> creatingNewUser(users));
        passShowButton.addActionListener(e -> showPassword(passPasswordField));
        checkPassShowButton.addActionListener(e -> showPassword(checkPassPasswordField));
    }
}
