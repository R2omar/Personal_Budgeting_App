/**
 * Defines a contract for user notification functionality.
 * Implementations of this interface are responsible for sending notifications to users.
 * @author Omar Sayed
 */
public interface INotification {
    /**
     * Sends a notification message to a specific user.
     *
     * @param userId the unique identifier of the user to notify
     * @param message the notification message to be delivered
     */
    void notifyUser(int userId, String message);
}