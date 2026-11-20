import javax.swing.*;

public class Assignment {
    private String assignmentID;
    private String title;
    private String instruction;
    private double maxScore;
    private double score;
    private JTextField textField1;

    public Assignment(String assignmentID, String title, String instruction){
        this.assignmentID = assignmentID;
        this.title=title;
        this.instruction=instruction;
        this.score=score;
    }
    String getAssignmentID(){
        return assignmentID;
    }
    String getTitle(){
        return title;
    }

    String getInstruction(){
        return instruction;
    }



    void setMaxScore(double maxScore){
        this.maxScore = maxScore;
    }


}
