import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Implementation of IReminderNotification that handles scheduled reminders and notifications.
 * Manages reminder scheduling, cancellation, and persistent notification logging.
 * @author Omar Sayed
 */
public class ReminderNotification implements IReminderNotification {
    private ReminderDataBase database;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final String NOTIFICATION_FOLDER = "users_notifications";
    private final Map<ArrayList<Integer>, Timer> scheduledTimers = new HashMap<>();

    /**
     * Constructs a reminderNotification instance and ensures the notification folder exists.
     * @param database the ReminderDataBase instance to use for persistence
     */
    public ReminderNotification(ReminderDataBase database) {
        new File(NOTIFICATION_FOLDER).mkdirs();
        this.database = database;
    }

    /**
     * Logs a notification message to the user's notification file.
     * @param userId the ID of the user to notify
     * @param message the notification message content
     */
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

    /**
     * Cancels a pending reminder notification before it triggers.
     * @param userId the ID of the user whose reminder should be canceled
     * @param reminderId the ID of the specific reminder to cancel
     */
    public void cancelNotification(int userId, int reminderId) {
        Timer timer = scheduledTimers.get(new ArrayList<Integer>(List.of(userId,reminderId)));
        if (timer != null) {
            timer.cancel();
            scheduledTimers.remove(new ArrayList<Integer>(List.of(userId,reminderId)));
            System.out.println("Cancelled pending notification for user " + userId);
        }
    }

    /**
     * Schedules a new reminder to trigger at the specified date/time.
     * @param reminder the Reminder object containing all scheduling details
     */
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