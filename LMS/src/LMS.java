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

    private final String fileUsers = "user.dat";

    public LMS(){

        lmsUIScreen.setPreferredSize(new Dimension(400, 200));

//        ArrayList<User> listToSave = new ArrayList<>();
//
//        @SuppressWarnings("unchecked")
//        public static ArrayList<User> loadList(User) {
//            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(User))) {
//                return (ArrayList<Student>) ois.readObject();
//            } catch (IOException | ClassNotFoundException e) {
//                e.printStackTrace();
//                return new ArrayList<>();
//            }
//        }
//
//
//            // 1. Create and populate the ArrayList
//
//            listToSave.add(new Student("Alice", 101));
//            listToSave.add(new Student("Bob", 102));
//
//            // 2. Save the ArrayList to a file
//            saveList(listToSave, filename);
//
//            // 3. Load the ArrayList back from the file
//            ArrayList<Student> loadedList = loadList(filename);
//                System.out.println("Loaded Students: " + loadedList);
//
//        public static void saveList(ArrayList<Student> list, String filename) {
//            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
//                oos.writeObject(list);
//                System.out.println("List saved successfully.");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
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






