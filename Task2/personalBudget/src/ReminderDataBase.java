import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Handles persistent storage and retrieval of reminder data.
 * Manages loading from and saving to a file, and provides CRUD operations for reminders.
 * @author Omar Sayed
 */
public class ReminderDataBase {
    private ArrayList<Reminder> reminders;
    private String fileName;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    /**
     * Constructs a ReminderDataBase with the specified file name.
     * Automatically loads existing reminders from the file during initialization.
     * @param fileName the name of the file to use for data persistence
     */
    public ReminderDataBase(String fileName) {
        this.fileName = fileName;
        this.reminders = new ArrayList<>();
        loadFromFile();
    }

    /**
     * Loads reminder data from the file into memory.
     * Parses each line of the file into Reminder objects and updates the reminder counter.
     */
    private void loadFromFile() {
        File file = new File(fileName);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 5);
                if (parts.length < 5) continue;

                int reminderId = Integer.parseInt(parts[0]);
                int userId = Integer.parseInt(parts[1]);
                String message = parts[2];
                Date reminderDate = dateFormat.parse(parts[3]);
                boolean isSent = Boolean.parseBoolean(parts[4]);

                Reminder reminder = new Reminder(reminderId, userId, message, reminderDate, isSent);
                reminders.add(reminder);
            }
            if(reminders.toArray().length > 0) Reminder.counter = reminders.get(reminders.toArray().length - 1).getReminderId() + 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves all reminder records from memory to the file.
     * Writes each Reminder object as a line in the file.
     */
    private void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (Reminder r : reminders) {
                bw.write(r.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new reminder to the database and persists it to file.
     * @param reminder the Reminder object to be saved
     * @return true if the reminder was successfully added, false otherwise
     */
    public boolean save(Reminder reminder) {
        boolean added = reminders.add(reminder);
        if (added) saveToFile();
        return added;
    }

    /**
     * Retrieves reminders for a specific user or all reminders if userId is -1.
     * @param userId the ID of the user whose reminders to retrieve, or -1 for all reminders
     * @return an ArrayList of Reminder objects matching the criteria
     */
    public ArrayList<Reminder> findByUserId(int userId) {
        ArrayList<Reminder> result = new ArrayList<>();
        for (Reminder r : reminders) {
            if (userId == -1 || r.getUserId() == userId) {
                result.add(r);
            }
        }
        return result;
    }

    /**
     * Updates an existing reminder in the database.
     * @param uodatedReminder the Reminder object with updated information
     * @return true if the reminder was found and updated, false otherwise
     */
    public boolean update(Reminder uodatedReminder) {
        for (int i = 0; i < reminders.size(); i++) {
            Reminder r = reminders.get(i);
            if (r.getReminderId() == uodatedReminder.getReminderId()) {
                reminders.set(i, uodatedReminder);
                saveToFile();
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes a reminder from the database.
     * @param reminderId the ID of the reminder to be deleted
     * @return true if the reminder was found and deleted, false otherwise
     */
    public boolean delete(int reminderId) {
        for (int i = 0; i < reminders.size(); i++) {
            if (reminders.get(i).getReminderId() == reminderId) {
                reminders.remove(i);
                if(reminders.toArray().length > 0) Reminder.counter = reminders.get(reminders.toArray().length - 1).getReminderId() + 1;
                saveToFile();
                return true;
            }
        }
        return false;
    }
}