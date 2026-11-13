import java.io.Serializable;
import java.util.*;
public class Course implements Serializable {
    private String courseID;
    private String courseName;
    private List<User> users;
    private List<Assignment> assignments;
    public Course(String courseID, String courseName){
        this.courseID = courseID;
        this.courseName = courseName;
        users = new ArrayList<User>();
        assignments = new ArrayList<Assignment>();
    }
    public String getCourseID() {
        return courseID;
    }
    public String getCourseName() {
        return courseName;
    }
    public List<User> getUsers() {
        return users;
    }
    public List<Assignment> getAssignments(){
        return assignments;
    }
    public void createAssignment(String assignmentName, String assignmentDetails){
        assignments.add(new Assignment(assignmentName, assignmentDetails));
    }
    @Override
    public String toString(){
        return courseID + " - " + courseName;
    }
}
