import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class User_UI extends JFrame{
    private JPanel pnlMainUser;
    private JTable courseTbl;

    public User_UI(User u) {
        DefaultTableModel t = new DefaultTableModel(0, 0);

        courseTbl.setModel(t);

        for(Course c : u.getAssignedCourses()) {
            t.addRow(new Object[]{c.getCourseName()});
        }
    }

    public static void main(String[] args) {
        
    }
}
