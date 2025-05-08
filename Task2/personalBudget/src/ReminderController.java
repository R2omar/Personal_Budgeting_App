import java.util.*;

public class ReminderController {
    private ReminderDataBase database;
    private IReminderNotification reminderNotification;


    public ReminderController(ReminderDataBase database) {
        this.database = database;
        this.reminderNotification = new reminderNotification(database);
    }


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

    public boolean deleteReminder(int userId, int reminderId) {
        List<Reminder> userExpenses = database.findByUserId(userId);
        for (Reminder rem : userExpenses) {
            if (rem.getReminderId() == reminderId) {
                reminderNotification.cancelNotification(userId,reminderId);
                return database.delete(reminderId);
            }
        }
        return false;
    }

    public List<Reminder> listReminders(int userId) {
        return database.findByUserId(userId);
    }


}
