import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

// One dashboard for every account type. The form has every button;
// the constructor hides the ones the logged-in user isn't allowed to use.
public class Sample_Dashboard extends JFrame{
    private JPanel pnlRoot;
    private JButton logoutButton;
    private JComboBox<Course> courseComboBox;
    private JButton courseButton;
    private JTabbedPane tabbedPane1;
    private JLabel welcomeLabel;
    private JList<String> assignmentList;
    private JTextArea detailsArea;
    private JButton addAssignmentButton;
    private JButton submitButton;
    private JButton gradeButton;
    private JTable gradesTable;
    private JList<String> peopleList;

    private final User currentUser;

    public Sample_Dashboard(User user){
        currentUser = user;
        setContentPane(pnlRoot);
        setTitle("LMS Dashboard");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        welcomeLabel.setText("Welcome, " + user.getClass().getSimpleName() + " " + user.getName() + " (" + user.getID() + ")");

        // Same screen for everyone; the account type decides which buttons show
        boolean isTeacher = user instanceof Teacher;
        boolean isStudent = user instanceof Student;
        addAssignmentButton.setVisible(isTeacher);
        gradeButton.setVisible(isTeacher);
        submitButton.setVisible(isStudent);
        courseButton.setVisible(isTeacher || isStudent);
        courseButton.setText(isTeacher ? "Create Course" : "Join Course");

        detailsArea.setEditable(false);
        detailsArea.setLineWrap(true);
        detailsArea.setWrapStyleWord(true);

        loadCourses();

        courseComboBox.addActionListener(e -> refresh());
        assignmentList.addListSelectionListener(e -> {
            if(!e.getValueIsAdjusting()){
                showDetails();
            }
        });
        courseButton.addActionListener(e -> {
            if(isTeacher){
                createCourse();
            }else{
                joinCourse();
            }
        });
        addAssignmentButton.addActionListener(e -> addAssignment());
        submitButton.addActionListener(e -> submitAssignment());
        gradeButton.addActionListener(e -> gradeSubmission());
        logoutButton.addActionListener(e -> {
            new Login(Main.getUsers());
            dispose();
        });

        refresh();
        setVisible(true); // last, once the window is fully set up
    }

    // Every course that exists, found through the users who belong to it
    private static List<Course> allCourses(){
        List<Course> courses = new ArrayList<>();
        for(User user : Main.getUsers()){
            for(Course course : user.getAssignedCourses()){
                if(!courses.contains(course)){
                    courses.add(course);
                }
            }
        }
        return courses;
    }

    private static Submission findSubmission(Student student, Assignment assignment){
        for(Submission submission : student.getSubmissions()){
            if(submission.getAssignment() == assignment){
                return submission;
            }
        }
        return null;
    }

    private static List<Student> studentsIn(Course course){
        List<Student> students = new ArrayList<>();
        for(User user : course.getUsers()){
            if(user instanceof Student student){
                students.add(student);
            }
        }
        return students;
    }

    // Admins see every course; teachers and students see the ones they're in
    private void loadCourses(){
        courseComboBox.removeAllItems();
        List<Course> courses = currentUser instanceof Admin ? allCourses() : currentUser.getAssignedCourses();
        for(Course course : courses){
            courseComboBox.addItem(course);
        }
    }

    private Course selectedCourse(){
        return (Course) courseComboBox.getSelectedItem();
    }

    private Assignment selectedAssignment(){
        Course course = selectedCourse();
        int index = assignmentList.getSelectedIndex();
        if(course == null || index < 0){
            return null;
        }
        return course.getAssignments().get(index);
    }

    private void refresh(){
        Course course = selectedCourse();

        DefaultListModel<String> assignments = new DefaultListModel<>();
        DefaultListModel<String> people = new DefaultListModel<>();
        if(course != null){
            for(Assignment assignment : course.getAssignments()){
                assignments.addElement(assignment.getTitle());
            }
            for(User user : course.getUsers()){
                people.addElement(user.getClass().getSimpleName() + ": " + user.getName() + " (" + user.getID() + ")");
            }
        }
        assignmentList.setModel(assignments);
        peopleList.setModel(people);

        showDetails();
        refreshGrades();
    }

    private void showDetails(){
        Assignment assignment = selectedAssignment();
        if(assignment == null){
            detailsArea.setText(selectedCourse() == null ? "No courses yet." : "Select an assignment.");
            return;
        }
        String details = assignment.getTitle()
                + "\nDue: " + (assignment.getDueDate().isEmpty() ? "No due date" : assignment.getDueDate())
                + "\nMax score: " + assignment.getMaxScore()
                + "\n\n" + assignment.getInstructions();

        // Students also see where they stand on this assignment
        if(currentUser instanceof Student student){
            Submission submission = findSubmission(student, assignment);
            if(submission == null){
                details += "\n\nStatus: Not submitted";
            }else if(!submission.isGraded()){
                details += "\n\nStatus: Submitted, waiting for a grade";
            }else{
                details += "\n\nStatus: Graded " + submission.getScore() + " / " + assignment.getMaxScore()
                        + "\nFeedback: " + submission.getFeedback();
            }
        }
        detailsArea.setText(details);
    }

    // Teachers and admins see every student's grades; students see only their own
    private void refreshGrades(){
        DefaultTableModel model = new DefaultTableModel(new String[]{"Student", "Assignment", "Score", "Feedback"}, 0){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        Course course = selectedCourse();
        if(course != null){
            List<Student> students = new ArrayList<>();
            if(currentUser instanceof Student student){
                students.add(student);
            }else{
                students = studentsIn(course);
            }
            for(Student student : students){
                for(Assignment assignment : course.getAssignments()){
                    Submission submission = findSubmission(student, assignment);
                    String score;
                    if(submission == null){
                        score = "Missing";
                    }else if(!submission.isGraded()){
                        score = "Not graded";
                    }else{
                        score = submission.getScore() + " / " + assignment.getMaxScore();
                    }
                    String feedback = submission == null ? "" : submission.getFeedback();
                    model.addRow(new Object[]{student.getName(), assignment.getTitle(), score, feedback});
                }
            }
        }
        gradesTable.setModel(model);
    }

    private void showError(String message){
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void createCourse(){
        String courseID = JOptionPane.showInputDialog(this, "Course ID (e.g. CSIT222):");
        if(courseID == null || courseID.isBlank()){
            return;
        }
        courseID = courseID.trim();
        for(Course course : allCourses()){
            if(course.getCourseID().equalsIgnoreCase(courseID)){
                showError("A course with ID " + courseID + " already exists.");
                return;
            }
        }
        String courseName = JOptionPane.showInputDialog(this, "Course name:");
        if(courseName == null || courseName.isBlank()){
            return;
        }
        Course course = new Course(courseID, courseName.trim());
        currentUser.assignCourse(course);
        Main.saveUsers();
        loadCourses();
        courseComboBox.setSelectedItem(course);
        JOptionPane.showMessageDialog(this, "Course created. Students can join it with the ID " + courseID + ".");
    }

    private void joinCourse(){
        String courseID = JOptionPane.showInputDialog(this, "Enter the course ID from your teacher:");
        if(courseID == null || courseID.isBlank()){
            return;
        }
        for(Course course : allCourses()){
            if(course.getCourseID().equalsIgnoreCase(courseID.trim())){
                if(currentUser.getAssignedCourses().contains(course)){
                    showError("You're already in " + course + ".");
                    return;
                }
                currentUser.assignCourse(course);
                Main.saveUsers();
                loadCourses();
                courseComboBox.setSelectedItem(course);
                return;
            }
        }
        showError("No course has the ID " + courseID.trim() + ".");
    }

    private void addAssignment(){
        Course course = selectedCourse();
        if(course == null){
            showError("Create a course first.");
            return;
        }
        // One form with every field; it checks the inputs itself
        AddAssignmentDialog dialog = new AddAssignmentDialog(this, course);
        dialog.setVisible(true); // waits here until the dialog closes, because it's modal
        if(dialog.isCreated()){
            Main.saveUsers();
            refresh();
            assignmentList.setSelectedIndex(course.getAssignments().size() - 1); // show the new one
        }
    }

    private void submitAssignment(){
        Assignment assignment = selectedAssignment();
        if(assignment == null || !(currentUser instanceof Student student)){
            showError("Select an assignment first.");
            return;
        }
        if(findSubmission(student, assignment) != null){
            showError("You already submitted this assignment.");
            return;
        }
        String answer = JOptionPane.showInputDialog(this, "Your answer:");
        if(answer == null || answer.isBlank()){
            return;
        }
        try{
            student.getSubmissions().add(new Submission(student, assignment, answer.trim()));
            Main.saveUsers();
            showDetails();
            refreshGrades();
        }catch(LMSException ex){
            showError(ex.getMessage());
        }
    }

    private void gradeSubmission(){
        Assignment assignment = selectedAssignment();
        if(assignment == null){
            showError("Select an assignment first.");
            return;
        }
        // Submissions are stored inside each Student, so collect the ones for this assignment
        List<Submission> submissions = new ArrayList<>();
        for(Student student : studentsIn(selectedCourse())){
            Submission submission = findSubmission(student, assignment);
            if(submission != null){
                submissions.add(submission);
            }
        }
        if(submissions.isEmpty()){
            showError("No one has submitted this assignment yet.");
            return;
        }
        // One form: pick the student, read the answer, enter score and feedback
        GradeDialog dialog = new GradeDialog(this, assignment, submissions);
        dialog.setVisible(true); // waits here until the dialog closes, because it's modal
        if(dialog.isGraded()){
            Main.saveUsers();
            refreshGrades();
            tabbedPane1.setSelectedIndex(1); // jump to the Grades tab to show the result
        }
    }
}
