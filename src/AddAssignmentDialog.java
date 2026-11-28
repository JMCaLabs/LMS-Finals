import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// One window for all the assignment details, instead of four popups.
// It stays open when something is wrong, so the teacher doesn't retype everything.
public class AddAssignmentDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField titleField;
    private JTextField dueDateField;
    private JTextField maxScoreField;
    private JTextArea instructionsArea;

    private final Course course;
    private boolean created = false;

    public AddAssignmentDialog(Frame owner, Course course) {
        super(owner, "Add Assignment to " + course, true);
        this.course = course;
        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonOK);

        instructionsArea.setLineWrap(true);
        instructionsArea.setWrapStyleWord(true);
        dueDateField.putClientProperty("JTextField.placeholderText", "e.g. Dec 1, 2026 (optional)");
        maxScoreField.putClientProperty("JTextField.placeholderText", "e.g. 100");

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

    // True only if OK was pressed and the assignment was added to the course
    public boolean isCreated() {
        return created;
    }

    private void showError(String message, JComponent field) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
        field.requestFocusInWindow();
    }

    private void onOK() {
        String title = titleField.getText().trim();
        String instructions = instructionsArea.getText().trim();
        String dueDate = dueDateField.getText().trim();
        String maxScoreText = maxScoreField.getText().trim();

        if (title.isEmpty()) {
            showError("Please enter a title.", titleField);
            return;
        }
        double maxScore;
        try {
            maxScore = Double.parseDouble(maxScoreText);
        } catch (NumberFormatException ex) {
            showError("Max score must be a number, e.g. 100.", maxScoreField);
            return;
        }
        try {
            course.createAssignment(title, instructions, dueDate, maxScore);
        } catch (LMSException ex) {
            showError(ex.getMessage(), maxScoreField);
            return;
        }
        created = true;
        dispose();
    }

    private void onCancel() {
        dispose();
    }
}
