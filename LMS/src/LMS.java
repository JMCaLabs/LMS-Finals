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
    // TEST for switcing panels
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private StudentLMS studentLMS;
    private TeacherLMS teacherLMS;
    //-------------------------

    private JTextField userNameTxtField;
    private JPanel lmsUIScreen;
    private JTextField userIDTxtField;
    private JLabel userName;
    private JLabel userPass;
    private JButton submitButton;
    private String name;
    private String password;
    private ArrayList<User> users;

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
    
        String enteredName = userNameTxtField.getText().trim();
        String enteredID = userIDTxtField.getText().trim();
    
        for (User user : users) {
    
            if (user.getName().equalsIgnoreCase(enteredName) &&
                user.getID().equals(enteredID)) {

                // (TO FIGURE OUT) to swicth panel;
//                if (user.getType().equalsIgnoreCase("Student")) {
//
//                    cardLayout.show(mainPanel, "STUDENT");
//
//                } else if (user.getType().equalsIgnoreCase("Teacher")) {
//
//                    cardLayout.show(mainPanel, "TEACHER");
//                }
    
                JOptionPane.showMessageDialog(
                    lmsUIScreen,
                    "User found! Welcome, " + user.getName()
                );
    
                return;
            }
        }
    
        JOptionPane.showMessageDialog(
            lmsUIScreen,
            "User does not exist or the information is incorrect."
        );
    }


    public LMS(){

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






