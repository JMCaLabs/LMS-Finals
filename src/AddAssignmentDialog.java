import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

// One window for all the assignment details, instead of four popups.
// It stays open when something is wrong, so the teacher doesn't retype everything.
public class AddAssignmentDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField titleField;
    private JTextField maxScoreField;
    private JTextArea instructionsArea;
    private JComboBox<String> monthComboBox;
    private JComboBox<Integer> dayComboBox;
    private JComboBox<Integer> yearComboBox;
    private JCheckBox noDueDateCheckBox;

    private final Course course;
    private boolean created = false;

    public AddAssignmentDialog(Frame owner, Course course) {
        super(owner, "Add Assignment to " + course, true);
        this.course = course;
        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonOK);

        instructionsArea.setLineWrap(true);
        instructionsArea.setWrapStyleWord(true);
        setUpDueDate();
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

    // Saved the same way it used to be typed, e.g. "Dec 1, 2026"
    private static final DateTimeFormatter DUE_DATE_FORMAT = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);

    // Fills the month, day and year lists and starts them on one week from today
    private void setUpDueDate() {
        for (Month month : Month.values()) {
            monthComboBox.addItem(month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
        }
        LocalDate start = LocalDate.now();
        for (int year = start.getYear(); year <= start.getYear() + 2; year++) {
            yearComboBox.addItem(year);
        }

        monthComboBox.setSelectedIndex(start.getMonthValue() - 1);
        yearComboBox.setSelectedItem(start.getYear());
        updateDays();
        dayComboBox.setSelectedItem(start.getDayOfMonth());

        // A different month or year can have a different number of days (Feb has 28 or 29)
        monthComboBox.addActionListener(e -> updateDays());
        yearComboBox.addActionListener(e -> updateDays());

        // Ticking "No due date" greys out the three boxes
        noDueDateCheckBox.addActionListener(e -> {
            boolean useDate = !noDueDateCheckBox.isSelected();
            monthComboBox.setEnabled(useDate);
            dayComboBox.setEnabled(useDate);
            yearComboBox.setEnabled(useDate);
        });
    }

    // Rebuilds the day list for the chosen month and year, keeping the chosen day if it still fits
    private void updateDays() {
        Integer chosenDay = (Integer) dayComboBox.getSelectedItem();
        YearMonth yearMonth = YearMonth.of((Integer) yearComboBox.getSelectedItem(), monthComboBox.getSelectedIndex() + 1);
        int lastDay = yearMonth.lengthOfMonth();

        dayComboBox.removeAllItems();
        for (int day = 1; day <= lastDay; day++) {
            dayComboBox.addItem(day);
        }
        dayComboBox.setSelectedItem(chosenDay == null ? 1 : Math.min(chosenDay, lastDay));
    }

    private LocalDate selectedDueDate() {
        return LocalDate.of((Integer) yearComboBox.getSelectedItem(),
                monthComboBox.getSelectedIndex() + 1,
                (Integer) dayComboBox.getSelectedItem());
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
        String dueDate = "";
        String maxScoreText = maxScoreField.getText().trim();

        if (title.isEmpty()) {
            showError("Please enter a title.", titleField);
            return;
        }
        if (!noDueDateCheckBox.isSelected()) {
            LocalDate due = selectedDueDate();
            if (due.isBefore(LocalDate.now())) {
                showError("The due date can't be in the past.", dayComboBox);
                return;
            }
            dueDate = due.format(DUE_DATE_FORMAT);
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
