import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class User implements Serializable {
    private String ID;
    private String name;
    private String password;
    private List<Course> assignedCourses;

    public User(String ID, String name, String password){
        this.ID = ID;
        this.name = name;
        this.password = password;
        assignedCourses = new ArrayList<Course>();
    }
    public String getID() {
        return ID;
    }
    public String getName() {
        return name;
    }
    public String getPassword(){
        return password;
    }
    public List<Course> getAssignedCourses(){
        return assignedCourses;
    }
    // Keeps both sides in sync, so course.getUsers() lists everyone in that course
    public void assignCourse(Course course){
        if(!assignedCourses.contains(course)){
            assignedCourses.add(course);
        }
        if(!course.getUsers().contains(this)){
            course.getUsers().add(this);
        }
    }
    @Override
    public String toString(){
        return ID + " - " + name;
    }
}
