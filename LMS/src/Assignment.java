import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class Assignment implements Serializable {
    private String title;
    private String instructions;
    private String dueDate;   // plain text typed by the teacher, e.g. "Dec 1, 2026" (can be empty)
    private double maxScore;

    public Assignment(String title, String instructions, String dueDate, double maxScore) throws LMSException {
        this.title = title;
        setInstructions(instructions);
        setDueDate(dueDate);
        setMaxScore(maxScore);
    }
    public void setMaxScore(double maxScore) throws LMSException {
        if (maxScore <= 0) {
            throw new LMSException("Max score must be greater than 0.");
        }
        this.maxScore = maxScore;
    }
    public String getTitle() {
        return title;
    }
    public String getInstructions() {
        return instructions;
    }
    public void setInstructions(String instructions) {
        this.instructions = instructions == null ? "" : instructions.trim();
    }
    public String getDueDate() {
        return dueDate;
    }
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate == null ? "" : dueDate.trim();
    }
    public double getMaxScore() {
        return maxScore;
    }
}
