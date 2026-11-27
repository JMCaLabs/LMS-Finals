import java.io.Serializable;
import java.time.LocalDateTime;

public class Submission implements Serializable {
    private final Assignment assignment;
    private String answer;
    private double score;
    private String feedback = "";
    private boolean graded;

    public Submission(Student student, Assignment assignment, String answer) throws LMSException {
        if (assignment == null) {
            throw new LMSException("A submission needs a student and an assignment.");
        }
        this.assignment = assignment;
        this.answer = answer;
    }
    public double getScore() {
        return score;
    }
    public Assignment getAssignment() {
        return assignment;
    }
    public String getAnswer() {
        return answer;
    }
    public String getFeedback() {
        return feedback;
    }
    public boolean isGraded() {
        return graded;
    }

}
