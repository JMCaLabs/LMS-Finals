import java.util.*;
public class Course {
    private List users;
    private String courseID;
    private String courseName;
    public Course(String courseID, String courseName){
        this.courseID = courseID;
        this.courseName = courseName;
        users = new ArrayList<User>();
    }
    public List getUsers() {
        return users;
    }
    public String getCourseID() {
        return courseID;
    }
    public String getCourseName() {
        return courseName;
    }
    @Override
    public String toString(){
        return courseID + " - " + courseName;
    }
}
