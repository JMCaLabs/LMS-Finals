import java.util.ArrayList;
import java.util.List;

public class Student extends User{
    List<Submission> submissions;
    public Student(String ID, String name, String password) {
        super(ID, name, password);
        submissions = new ArrayList<Submission>();
    }
    public List<Submission> getSubmissions() {
        return submissions;
    }
}
