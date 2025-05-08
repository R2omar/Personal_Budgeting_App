import java.text.SimpleDateFormat;
import java.util.Date;

public class Reminder {
    private int reminderId;
    private int userId;
    private String message;
    private Date reminderDate;
    private boolean isSent;

    private static int counter = 1;

    public Reminder(int userId, String message, Date reminderDate) {
        this.reminderId = counter;
        this.userId = userId;
        this.message = message;
        this.reminderDate = reminderDate;
        this.isSent = false;
        counter++;
    }
    public Reminder(int reminderId,int userId, String message, Date reminderDate,boolean isSent) {
        this.reminderId = reminderId ;
        this.userId = userId;
        this.message = message;
        this.reminderDate = reminderDate;
        this.isSent = isSent;
    }

    public int getReminderId() {
        return reminderId;
    }

    public int getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }

    public Date getReminderDate() {
        return reminderDate;
    }

    public boolean isSent() {
        return isSent;
    }

    public void markAsSent() {
        this.isSent = true;
    }
    public void markAsNotSent() {
        this.isSent = false;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public void setReminderDate(Date reminderDate) {
        this.reminderDate = reminderDate;
    }

    @Override
    public String toString() {
        return reminderId + "," + userId + "," + message + "," + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(reminderDate) + "," + isSent;
    }

}
