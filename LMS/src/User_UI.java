import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class User_UI extends JFrame{
    private JPanel pnlMainUser;
    private JTable tblCourse;
    private JPanel pnlMain;
    private JScrollPane scrCourse;


    public User_UI(User u, String title) {
        super(title);
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

        tblCourse.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = tblCourse.rowAtPoint(e.getPoint());
                int col = tblCourse.columnAtPoint(e.getPoint());

                if(row >= 0 && col >= 0) {
                    if(u instanceof Student) {
                        //Redirect to student version of Course UI
                        System.out.println("Student Interface");
                    } else if(u instanceof Teacher) {
                        //Redirect to teacher version of Course UI
                        System.out.println("Teacher Interface");
                    }
                }
            }
        });
    }
    
    //Sample main
    public static void main(String[] args) {
        User u = new Teacher("23-6912-112", "Malinao", "Hallo");
        u.assignCourse(new Course("CSIT222", "DSA"));
        u.assignCourse(new Course("CSIT214", "Ethics"));

        User_UI ui = new User_UI(u, "Courses");

        ui.setContentPane(ui.pnlMain);
        ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ui.pack();
        ui.setLocationRelativeTo(null);
        ui.setVisible(true);
    }
}
