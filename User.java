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
    public void assignCourse(Course course){
        assignedCourses.add(course);
    }
    @Override
    public String toString(){
        return ID + " - " + name;
    }
}
