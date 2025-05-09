import java.util.*;

/**
 * Controller class for managing reminder operations.
 * Acts as an intermediary between the application and reminder data storage,
 * handling business logic for reminder scheduling and notifications.
 * @author Omar Sayed
 */
public class ReminderController {
    private ReminderDataBase database;
    private IReminderNotification reminderNotification;

    /**
     * Constructs a ReminderController with the specified database.
     * Initializes the reminder notification system.
     * @param database the ReminderDataBase instance to use for data operations
     */
    public ReminderController(ReminderDataBase database) {
        this.database = database;
        this.reminderNotification = new ReminderNotification(database);
    }

    /**
     * Adds a new reminder if its date is in the future.
     * Automatically schedules the reminder notification upon successful creation.
     * @param reminder the Reminder object to add
     * @return true if the reminder was successfully added and scheduled, false otherwise
     */
    public boolean addReminder(Reminder reminder) {
        if (reminder.getReminderDate().after(new Date())) {
            boolean saved = database.save(reminder);
            if (saved) {
                reminderNotification.scheduleReminder(reminder);
                return true;
            }
        }
        return false;
    }

    /**
     * Updates an existing reminder.
     * Reschedules the notification if the reminder date is in the future.
     * @param reminder the updated Reminder object
     * @return true if the reminder was found and updated, false otherwise
     */
    public boolean updateReminder(Reminder reminder) {
        List<Reminder> userReminders = database.findByUserId(reminder.getUserId());
        for (Reminder rem : userReminders) {
            if (rem.getReminderId() == reminder.getReminderId()) {
                if (reminder.getReminderDate().after(new Date())) {
                    reminder.markAsNotSent();
                    reminderNotification.scheduleReminder(reminder);
                }
                return database.update(reminder);
            }
        }
        return false;
    }

    /**
     * Deletes a reminder for a specific user.
     * Cancels any pending notification before deletion.
     * @param userId the ID of the user who owns the reminder
     * @param reminderId the ID of the reminder to delete
     * @return true if the reminder was found and deleted, false otherwise
     */
    public boolean deleteReminder(int userId, int reminderId) {
        List<Reminder> userExpenses = database.findByUserId(userId);
        for (Reminder rem : userExpenses) {
            if (rem.getReminderId() == reminderId) {
                reminderNotification.cancelNotification(userId, reminderId);
                return database.delete(reminderId);
            }
        }
        return false;
    }

    /**
     * Retrieves all reminders for a specific user.
     * @param userId the ID of the user whose reminders to retrieve
     * @return a List of Reminder objects belonging to the specified user
     */
    public List<Reminder> listReminders(int userId) {
        return database.findByUserId(userId);
    }
}