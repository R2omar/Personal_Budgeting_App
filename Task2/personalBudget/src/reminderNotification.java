import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class reminderNotification implements IReminderNotification {
    private ReminderDataBase database;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final String NOTIFICATION_FOLDER = "users_notifications";
    private final Map<ArrayList<Integer>, Timer> scheduledTimers = new HashMap<>();
    public reminderNotification(ReminderDataBase database) {
        new File(NOTIFICATION_FOLDER).mkdirs();
        this.database = database;
    }

    @Override
    public void notifyUser(int userId, String message) {
        String filename = NOTIFICATION_FOLDER + "/user_" + userId + "_notifications.txt";
        String timestamp = dateFormat.format(new Date());
        String notificationContent = String.format(
                "[%s] NOTIFICATION: %s%n",
                timestamp,
                message
        );

        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write(notificationContent);
            System.out.println("\nNotification logged to " + filename);
        } catch (IOException e) {
            System.err.println("Error writing notification: " + e.getMessage());
        }
    }

    public void cancelNotification(int userId,int reminderId) {
        Timer timer = scheduledTimers.get(new ArrayList<Integer>(List.of(userId,reminderId)));
        if (timer != null) {
            timer.cancel();
            scheduledTimers.remove(new ArrayList<Integer>(List.of(userId,reminderId)));
            System.out.println("Cancelled pending notification for user " + userId);
        }
    }
    public void scheduleReminder(Reminder reminder) {
        Timer timer = new Timer(true);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                notifyUser(reminder.getUserId(),reminder.getMessage());
                reminder.markAsSent();
                database.update(reminder);
            }
        };
        timer.schedule(task, reminder.getReminderDate());
        scheduledTimers.put(new ArrayList<Integer>(List.of(reminder.getUserId(),reminder.getReminderId())), timer);
    }


}