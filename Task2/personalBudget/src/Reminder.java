import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents a reminder notification with its scheduling details and status.
 * Tracks reminder ID, associated user, message content, scheduled date/time, and delivery status.
 * @author Omar Sayed
 */
public class Reminder {
    private int reminderId;
    private int userId;
    private String message;
    private Date reminderDate;
    private boolean isSent;

    static int counter = 1;

    /**
     * Constructs a new Reminder with auto-incremented ID and default unsent status.
     * @param userId the ID of the user associated with this reminder
     * @param message the content of the reminder message
     * @param reminderDate the date and time when the reminder should be triggered
     */
    public Reminder(int userId, String message, Date reminderDate) {
        this.reminderId = counter;
        this.userId = userId;
        this.message = message;
        this.reminderDate = reminderDate;
        this.isSent = false;
        counter++;
    }

    /**
     * Constructs a Reminder with all fields specified.
     * @param reminderId the specific ID to assign to this reminder
     * @param userId the ID of the user associated with this reminder
     * @param message the content of the reminder message
     * @param reminderDate the date and time when the reminder should be triggered
     * @param isSent whether the reminder has already been sent
     */
    public Reminder(int reminderId, int userId, String message, Date reminderDate, boolean isSent) {
        this.reminderId = reminderId;
        this.userId = userId;
        this.message = message;
        this.reminderDate = reminderDate;
        this.isSent = isSent;
    }

    /**
     * @return the unique identifier of this reminder
     */
    public int getReminderId() {
        return reminderId;
    }

    /**
     * @return the ID of the user associated with this reminder
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @return the message content of this reminder
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return the scheduled date and time for this reminder
     */
    public Date getReminderDate() {
        return reminderDate;
    }

    /**
     * @return true if the reminder has been sent, false otherwise
     */
    public boolean isSent() {
        return isSent;
    }

    /**
     * Marks this reminder as having been sent.
     */
    public void markAsSent() {
        this.isSent = true;
    }

    /**
     * Marks this reminder as not having been sent.
     */
    public void markAsNotSent() {
        this.isSent = false;
    }

    /**
     * Updates the message content of this reminder.
     * @param message the new message content
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Updates the scheduled date/time for this reminder.
     * @param reminderDate the new date and time for the reminder
     */
    public void setReminderDate(Date reminderDate) {
        this.reminderDate = reminderDate;
    }

    /**
     * Returns a string representation of the reminder in CSV format.
     * @return a comma-separated string containing all reminder fields
     */
    @Override
    public String toString() {
        return reminderId + "," + userId + "," + message + "," +
                new SimpleDateFormat("yyyy-MM-dd HH:mm").format(reminderDate) + "," + isSent;
    }
}