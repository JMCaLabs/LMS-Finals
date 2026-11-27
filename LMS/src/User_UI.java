import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class User_UI extends JFrame{
    private JTable tblCourse;
    private JPanel pnlMain;
    private JPanel cardCont;
    private JPanel cardTeacher;
    private JPanel cardStudent;
    private JScrollPane scrCourse;


    public User_UI(User u) {
        super("Courses");

        CardLayout layout = (CardLayout) cardCont.getLayout();

        String[] headers = {"Course ID", "Name"};
        DefaultTableModel t = new DefaultTableModel(headers, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblCourse.setModel(t);

        for(Course c : u.getAssignedCourses()) {
            t.addRow(new String[]{c.getCourseID(), c.getCourseName()});
        }

        tblCourse.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tblCourse.rowAtPoint(e.getPoint());
                int col = tblCourse.columnAtPoint(e.getPoint());

                if(row >= 0 && col >= 0) {
                    if(u instanceof Student)
                        //Redirect to student version of Course UI
                        layout.show(cardCont, "student");
                    else if(u instanceof Teacher)//Redirect to teacher version of Course UI
                        layout.show(cardCont, "teacher");

                }
            }
        });

        setContentPane(pnlMain);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    //Sample main
    public static void main(String[] args) {
        User u = new Teacher("23-6912-112", "Malinao", "Hallo");
        u.assignCourse(new Course("CSIT222", "DSA"));
        u.assignCourse(new Course("CSIT214", "Ethics"));

        new User_UI(u);
    }
}