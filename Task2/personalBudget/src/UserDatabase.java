import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Provides operations for saving, searching, and updating users in a text file-based user database.
 * @author Nada Samir
 */
class UserDatabase {

    /**
     * Saves a user to the "Users.txt" file by appending their information.
     *
     * @param user the user to be saved
     */
    public static void Save1(User user) {
        try {
            FileWriter w = new FileWriter("Users.txt", true);
            w.write(user.getUsername() + ", " + user.getPassword() + ", " + user.getEmail() + ", " + user.getUserID() + "\n");
            w.close();
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }

    /**
     * Finds a user in a list by their email address.
     *
     * @param email the email to search for
     * @param Users the list of users to search in
     * @return the user with the specified email, or null if not found
     */
    public static User findByEmail(String email, List<User> Users) {
        for (User user : Users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Finds a user in a list by their user ID.
     *
     * @param id the user ID to search for
     * @param Users the list of users to search in
     * @return the user with the specified ID, or null if not found
     */
    public static User findById(int id, List<User> Users) {
        for (User user : Users) {
            if (user.getUserID() == id) {
                return user;
            }
        }
        return null;
    }

    /**
     * Updates the password of the specified user in the "Users.txt" file.
     *
     * @param userToUpdate the user whose password is to be updated
     * @param newPass the new password to set
     */
    public static void updatePassword(User userToUpdate, String newPass) {
        try {
            File f = new File("Users.txt");
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

            FileWriter writer = new FileWriter("Users.txt");
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
