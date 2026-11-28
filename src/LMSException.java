// General LMS exception from the class diagram (thrown by Assignment, and later Submission).
// Login errors have their own LogInException.
public class LMSException extends Exception {
    public LMSException(String message) { super(message); }

    //Empty fields for both course and name.
    public static class EmpCourseFieldException extends LMSException {
        public EmpCourseFieldException() {
            super("Please enter the course Name and ID.");
        }
    }
    //No course/s created.
    public static class NoExistingCourseException extends LMSException {
        public NoExistingCourseException() {
            super("Please create a course first.");
        }
    }
    //No Course with such ID. Check list of courses
    public static class SelectedNoCourseId extends LMSException {
        public SelectedNoCourseId() {
            super("No course has the ID "); //append with entered ID
        }
    }
    //Enrolling for a course the user is already a part of. Check list of courses
    public static class AlreadyInCourseException extends LMSException {
        public AlreadyInCourseException() {
            super("You are already in course "); //append with name and/or ID of course
        }
    }
    //Already submitted assignment. Check submission
    public static class AssignmentAlreadySubmittedException extends LMSException {
        public AssignmentAlreadySubmittedException() {
            super("You already submitted this assignment."); //append with name and/or ID of course
        }
    }
    //No selected assignment. Check list of assignments
    public static class NoSelectedAssignmentException extends LMSException {
        public NoSelectedAssignmentException() {
            super("Select an assignment first."); //append with name and/or ID of course
        }
    }
}
