import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static <T> List<T> loadListData(String filename){
        File file = new File(filename);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private static <T> void saveListData(List<T> list, String filename){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(list);
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        List<User> users = loadListData("data/users.dat");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            saveListData(users, "data/users.dat");
            System.out.println("All data saved safely on exit.");
        }));

        new Login(users);
    }
}