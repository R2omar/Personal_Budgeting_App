/**
 * Extends the basic notification functionality to include reminder-specific operations.
 * Provides methods for scheduling and canceling reminder notifications.
 * @author Omar Sayed
 */
public interface IReminderNotification extends INotification {
    /**
     * Cancels a previously scheduled reminder notification.
     *
     * @param userId the ID of the user whose reminder should be canceled
     * @param reminderId the ID of the specific reminder to cancel
     */
    void cancelNotification(int userId, int reminderId);

    /**
     * Schedules a new reminder notification to be delivered at the appropriate time.
     *
     * @param reminder the Reminder object containing all necessary details
     *                 (time, message, user ID, etc.) for scheduling
     */
    public void scheduleReminder(Reminder reminder);
}