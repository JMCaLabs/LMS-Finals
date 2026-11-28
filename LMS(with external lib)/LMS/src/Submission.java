import java.io.Serializable;

public class Submission implements Serializable {
    private final Student student;
    private final Assignment assignment;
    private String answer;
    private double score;
    private String feedback = "";
    private boolean graded;

    public Submission(Student student, Assignment assignment, String answer) throws LMSException {
        if (student == null || assignment == null) {
            throw new LMSException("A submission needs a student and an assignment.");
        }
        this.student = student;
        this.assignment = assignment;
        this.answer = answer;
    }
    public Student getStudent() {
        return student;
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
    public void grade(double score, String feedback) throws LMSException {
        if (score < 0 || score > assignment.getMaxScore()) {
            throw new LMSException("Score must be between 0 and " + assignment.getMaxScore() + ".");
        }
        this.score = score;
        this.feedback = feedback == null ? "" : feedback.trim();
        this.graded = true;
    }

}
