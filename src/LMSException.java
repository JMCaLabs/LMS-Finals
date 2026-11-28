// General LMS exception from the class diagram (thrown by Assignment, and later Submission).
// Login errors have their own LogInException.
public class LMSException extends Exception {
    public LMSException(String message) { super(message); }
}
