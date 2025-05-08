public interface IReminderNotification extends INotification {
    void cancelNotification(int userId,int reminderId);
    public void scheduleReminder(Reminder reminder);
}