import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class UserDatabase {
    public static void Save1(User user) {
        try {
            FileWriter w = new FileWriter("NS.txt", true);
            w.write(user.getUsername() + ", " + user.getPassword() + ", " + user.getEmail() + ", " + user.getUserID() + "\n");
            w.close();
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }

    public static User findByEmail(String email, List<User> NS) {
        for (User user : NS) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    public static User findById(int id, List<User> NS) {
        for (User user : NS) {
            if (user.getUserID() == id) {
                return user;
            }
        }
        return null;
    }

    public static void updatePassword(User userToUpdate, String newPass) {
        try {
            File f = new File("NS.txt");
            List<String> updatedLines = new ArrayList<>();
            Scanner scanner = new Scanner(f);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(", ");
                if (parts.length == 4 && parts[2].trim().equals(userToUpdate.getEmail())) {
                    updatedLines.add(userToUpdate.getUsername() + ", " + newPass + ", " + userToUpdate.getEmail() + ", " + userToUpdate.getUserID());
                } else {
                    updatedLines.add(line);
                }
            }
            scanner.close();

            FileWriter writer = new FileWriter("NS.txt");
            for (String updatedLine : updatedLines) {
                writer.write(updatedLine + "\n");
            }
            writer.close();

            System.out.println("Password updated and saved successfully!");

        } catch (IOException e) {
            System.out.println("Failed to update the file.");
        }
    }
}
