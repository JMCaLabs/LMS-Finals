import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static private final String USERS_FILE = "data/users.dat";
    static private final String COURSE_FILE = "data/course.dat";
    private static final List<User> users = loadListData(USERS_FILE);
    private static final List<Course> course = loadListData(COURSE_FILE);

    public static List<User> getUsers() {
        return users;
    }
    public static List<Course> getCourse(){
        return course;
    }
    public static void saveUsers() {
        saveListData(users, USERS_FILE);
    }

    @SuppressWarnings("unchecked")
    private static <T> List<T> loadListData(String filename){
        File file = new File(filename);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            // The file couldn't be read (e.g. a saved class was changed). Keep a copy
            // before starting empty, because the next save would overwrite it.
            File backup = new File(filename + ".bak");
            try {
                Files.copy(file.toPath(), backup.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException copyError) {
                copyError.printStackTrace();
            }
            JOptionPane.showMessageDialog(
                    null,
                    "Saved data in " + filename + " could not be loaded, so the app is starting empty.\n"
                            + "A copy of the old file was kept as " + backup.getPath() + ".",
                    "Load Error",
                    JOptionPane.WARNING_MESSAGE
            );
            return new ArrayList<>();
        }
    }

    private static synchronized <T> void saveListData(List<T> list, String filename){
        // Create the "data" folder first if it doesn't exist yet,
        // otherwise FileOutputStream fails and nothing is saved.
        File folder = new File(filename).getParentFile();
        if (folder != null && !folder.exists()) {
            folder.mkdirs();
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(list);
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // FlatLaf's optional native library makes Java 24+ print "restricted method" warnings.
        // The look and feel works the same without it.
        System.setProperty("flatlaf.useNativeLibrary", "false");
        FlatDarkLaf.setup();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            saveListData(users, USERS_FILE);
            System.out.println("All data saved safely on exit.");
        }));

        SwingUtilities.invokeLater(Login::new);
    }
}
