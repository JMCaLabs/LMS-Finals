//Still figuring out.

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LMS {
    
    private JTextField userNameTxtField;
    private JPanel lmsUIScreen;
    private JTextField userIDTxtField;
    private JLabel userName;
    private JLabel userPass;
    private JButton submitButton;
    private String name;
    private String password;
    private ArrayList<User> users;
    private User currentUser;

    private final String fileUsers = "user.dat";

    private ArrayList<User> loadusers(){
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileUsers))) {
                return (ArrayList<User>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return new ArrayList<>();
            }
        }

    
    private void searchUser() {
    
        String enteredID = userIDTxtField.getText().trim();
        String enteredPassword = new String(pswrdPswrdField.getText())
    
        for (User user : users) {
            try{
                if (user.getPassword().equalsIgnoreCase(enteredPassword) &&
                user.getID().equals(enteredID)) {
                    if(user.getPassword().equals(enteredPassword){
                        currentUser = user
                        
                        User_UI nextPanel = new User_UI(currentUser); 
                    }
                }
                    JOptionPane.showMessageDialog(
                        lmsUIScreen,
                        "User found! Welcome, " user.getName()
                        
                );
                        
            } catch()
            
    
                return;
            }
        }
    
        JOptionPane.showMessageDialog(
            lmsUIScreen,
            "User does not exist or the information is incorrect."
        );
    }

    

    public LMS(List<User> users){

        lmsUIScreen.setPreferredSize(new Dimension(400, 200));

        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);

        studentLMS = new StudentLMS();
        teacherLMS = new TeacherLMS();

        mainPanel.add(lmsUIScreen, "Login");
        mainPanel.add(studentLMS.getPanel(), "Student");
        mainPanel.add(teacherLMS.getPanel(), "Teacher");


        // load array of users;
        users = loadusers();

        // search inputed id and username. then see if they match;
        submitButton.addActionListener(e -> searchUser());

        
        }
            public static void main(String[] args) {
            JFrame frame = new JFrame("LMS");

            LMS lmsUI = new LMS();

            frame.setContentPane(lmsUI.lmsUIScreen);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }

    }

    
}





