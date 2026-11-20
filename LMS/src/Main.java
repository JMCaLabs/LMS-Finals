import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        User currentUser = null;
        Teacher teacher = new Teacher("25-1932-23", "Teacher 1", "00000000");
        teacher.assignCourse(new Course("1567", "CSIT125"));
        teacher.assignCourse(new Course("231", "CSIT111"));
        Student student = new Student("25-9849-135", "Student Name", "00000000");
        Teacher teacher2 = new Teacher("25-2131-23", "Teacher 2", "00000000");
        List<User> users = new ArrayList<User>();
        users.add(teacher);
        users.add(student);
        users.add(teacher2);
        String inputID = "25-9849-135";
        String inputName = "Student Name";
        String inputPass = "00000000";

        //THIS IS FOR LOGIN (makes a loop that goes through each user in the list users
        // checks if ID, name, and input pass is the same, if yes it saves that user into current user
        System.out.println(currentUser);
        new createNewUser(users);
    }
}