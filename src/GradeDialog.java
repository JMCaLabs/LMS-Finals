import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

// One window for grading: pick a student, read their answer, enter score and feedback.
// It stays open when something is wrong, so the teacher doesn't lose what they typed.
public class GradeDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox<String> studentComboBox;
    private JTextArea answerArea;
    private JLabel scoreLabel;
    private JTextField scoreField;
    private JTextArea feedbackArea;

    private final Assignment assignment;
    private final List<Submission> submissions;
    private boolean graded = false;

    public GradeDialog(Frame owner, Assignment assignment, List<Submission> submissions) {
        super(owner, "Grade: " + assignment.getTitle(), true);
        this.assignment = assignment;
        this.submissions = submissions;
        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonOK);

        scoreLabel.setText("Score (out of " + formatScore(assignment.getMaxScore()) + ")");
        answerArea.setEditable(false); // the teacher reads the answer but can't change it
        for (JTextArea area : new JTextArea[]{answerArea, feedbackArea}) {
            area.setRows(5);
            area.setLineWrap(true);
            area.setWrapStyleWord(true);
        }

        for (Submission submission : submissions) {
            Student student = submission.getStudent();
            studentComboBox.addItem(student.getName() + " (" + student.getID() + ")"
                    + (submission.isGraded() ? " - graded" : ""));
        }
        studentComboBox.addActionListener(e -> showSubmission());
        showSubmission();

        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        pack();
        setLocationRelativeTo(owner);
    }

    // True only if OK was pressed and a grade was saved on the submission
    public boolean isGraded() {
        return graded;
    }

    // 95.0 shows as "95", 92.5 stays "92.5"
    private static String formatScore(double score) {
        return score == Math.floor(score) ? String.valueOf((long) score) : String.valueOf(score);
    }

    private Submission selectedSubmission() {
        return submissions.get(studentComboBox.getSelectedIndex());
    }

    // Fills the boxes for the chosen student; an old grade is shown so it can be changed
    private void showSubmission() {
        Submission submission = selectedSubmission();
        answerArea.setText(submission.getAnswer());
        answerArea.setCaretPosition(0);
        scoreField.setText(submission.isGraded() ? formatScore(submission.getScore()) : "");
        feedbackArea.setText(submission.isGraded() ? submission.getFeedback() : "");
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
        scoreField.requestFocusInWindow();
        scoreField.selectAll();
    }

    private void onOK() {
        double score;
        try {
            score = Double.parseDouble(scoreField.getText().trim());
        } catch (NumberFormatException ex) {
            showError("Score must be a number, e.g. " + formatScore(assignment.getMaxScore()) + ".");
            return;
        }
        try {
            selectedSubmission().grade(score, feedbackArea.getText());
        } catch (LMSException ex) {
            showError(ex.getMessage());
            return;
        }
        graded = true;
        dispose();
    }

    private void onCancel() {
        dispose();
    }
}
