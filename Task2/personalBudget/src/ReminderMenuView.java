import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Provides a console-based user interface for managing reminders.
 * Handles user interactions for creating, viewing, updating, and deleting reminders.
 * @author Omar Sayed
 */
public class ReminderMenuView {
    private final ReminderController controller;
    private final User loggedInUser;
    private final Scanner scanner;
    private final SimpleDateFormat dateFormat;

    /**
     * Constructs a ReminderMenuView with the specified controller and user.
     * @param controller the ReminderController to handle business logic
     * @param loggedInUser the currently authenticated user
     */
    public ReminderMenuView(ReminderController controller, User loggedInUser) {
        this.controller = controller;
        this.loggedInUser = loggedInUser;
        this.scanner = new Scanner(System.in);
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    }

    /**
     * Guides the user through creating a new reminder.
     * Prompts for message and date/time, validates future date,
     * and displays success/error messages.
     */
    public void addReminder() {
        try {
            System.out.print("Enter Reminder Message: ");
            String message = scanner.nextLine();

            System.out.print("Enter Date and Time (yyyy-MM-dd HH:mm): ");
            Date reminderDate = dateFormat.parse(scanner.nextLine());

            Reminder reminder = new Reminder(loggedInUser.getUserID(), message, reminderDate);
            boolean success = controller.addReminder(reminder);

            if (success) {
                System.out.println("Reminder Saved successfully.");
                System.out.println("You will be notified at the specified time.");
            } else {
                System.out.println("Failed to add reminder (Date Is Before Now Date).");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Displays all reminders for the logged-in user in a formatted table.
     * Shows message, due date, and notification status for each reminder.
     */
    public void viewReminders() {
        try {
            List<Reminder> reminders = controller.listReminders(loggedInUser.getUserID());
            if (reminders.isEmpty()) {
                System.out.println("\nYou don't have any reminders set.");
            } else {
                System.out.println("\n=== Your Reminders ===");
                System.out.printf("%-5s %-30s %-20s %-10s%n", "ID", "Message", "Due Date", "Status");
                System.out.println("-------------------------------------------------------------------");

                for (int i = 0; i < reminders.size(); i++) {
                    Reminder r = reminders.get(i);
                    System.out.printf("%-5d %-30s %-20s %-10s%n",
                            (i + 1),
                            truncate(r.getMessage(), 28),
                            dateFormat.format(r.getReminderDate()),
                            r.isSent() ? "Notified" : "Pending"
                    );
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Guides the user through updating an existing reminder.
     * Allows modifying either the message or date of a selected reminder.
     */
    public void updateReminder() {
        try {
            List<Reminder> reminders = controller.listReminders(loggedInUser.getUserID());
            if (reminders.isEmpty()) {
                System.out.println("No reminders found for this user.");
                return;
            }

            viewReminders(); // Display all expenses for reference

            System.out.print("Enter Reminder ID to update: ");
            int reminderId = Integer.parseInt(scanner.nextLine());

            Reminder found = null;
            for (Reminder r : reminders) {
                if (r.getReminderId() == reminders.get(reminderId - 1).getReminderId()) {
                    found = r;
                    break;
                }
            }

            if (found == null) {
                System.out.println("Reminder ID not found.");
                return;
            }

            boolean updating = true;
            while (updating) {
                System.out.println("\nWhat would you like to update?");
                System.out.println("1. Message");
                System.out.println("2. Date");
                System.out.println("3. Finish Updating");
                System.out.print("Choose an option (1-3): ");

                String choice = scanner.nextLine();

                switch (choice) {
                    case "1" : {
                        System.out.print("Enter New Message: ");
                        String message = scanner.nextLine();
                        found.setMessage(message);
                        break;
                    }
                    case "2" : {
                        System.out.print("Enter New Date: ");
                        Date date = dateFormat.parse(scanner.nextLine());;
                        found.setReminderDate(date);
                        break;
                    }
                    case "3" : {updating = false;break;}
                    default : System.out.println("Invalid option. Try again.");
                }
            }

            boolean updated = controller.updateReminder(found);
            System.out.println(updated ? "Reminder updated successfully." : "Failed to update reminder.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Guides the user through deleting a reminder with confirmation.
     * Displays the reminder list and prompts for ID before deletion.
     */
    public void deleteReminder() {
        try {
            List<Reminder> reminders = controller.listReminders(loggedInUser.getUserID());
            if (reminders.isEmpty()) {
                System.out.println("No reminders found for this user.");
                return;
            }

            viewReminders();

            System.out.print("Enter Reminder ID to delete: ");
            int reminderId = Integer.parseInt(scanner.nextLine());

            if (reminderId < 1 || reminderId > reminders.size()) {
                System.out.println("Invalid reminder ID.");
                return;
            }

            System.out.print("Are you sure you want to delete this reminder? (yes/no): ");
            String confirmation = scanner.nextLine().toLowerCase();

            if (confirmation.equals("yes") || confirmation.equals("y")) {
                boolean deleted = controller.deleteReminder(
                        loggedInUser.getUserID(),
                        reminders.get(reminderId - 1).getReminderId()
                );
                System.out.println(deleted ? "\nReminder deleted successfully!" : "\nFailed to delete reminder.");
            } else {
                System.out.println("Deletion cancelled.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Helper method to truncate long strings with ellipsis.
     * @param str the string to truncate
     * @param length the maximum length before truncation
     * @return the truncated string with ellipsis if needed
     */
    private String truncate(String str, int length) {
        return str.length() > length ? str.substring(0, length - 3) + "..." : str;
    }
}