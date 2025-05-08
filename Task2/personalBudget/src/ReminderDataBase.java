import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReminderDataBase {
    private ArrayList<Reminder> reminders;
    private String fileName;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public ReminderDataBase(String fileName) {
        this.fileName = fileName;
        this.reminders = new ArrayList<>();
        loadFromFile();
    }

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    public boolean save(Reminder reminder) {
        boolean added = reminders.add(reminder);
        if (added) saveToFile();
        return added;
    }

    public ArrayList<Reminder> findByUserId(int userId) {
        ArrayList<Reminder> result = new ArrayList<>();
        for (Reminder r : reminders) {
            if (userId == -1 || r.getUserId() == userId) {
                result.add(r);
            }
        }
        return result;
    }

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

    public boolean delete(int reminderId) {
        for (int i = 0; i < reminders.size(); i++) {
            if (reminders.get(i).getReminderId() == reminderId) {
                reminders.remove(i);
                saveToFile();
                return true;
            }
        }
        return false;
    }
}
