import java.util.List;
import java.util.Scanner;

/**
 * Controller class for managing budget operations.
 * Handles business logic for budget creation, updates, deletions, and notifications.
 * @author Kholod Ahmed
 */
public class BudgetController {
    private BudgetDatabase database;
    private NotificationController notificationController;

    /**
     * Constructs a BudgetController with the specified database and notification controller.
     * @param database the BudgetDatabase instance for data persistence
     * @param notificationController the NotificationController for sending user alerts
     */
    public BudgetController(BudgetDatabase database, NotificationController notificationController) {
        this.database = database;
        this.notificationController = notificationController;
    }

    /**
     * Creates a new budget if the amount is positive.
     * Sends a notification upon successful creation.
     * @param budget the Budget object to create
     * @return true if the budget was successfully saved, false otherwise
     */
    public boolean setBudget(Budget budget) {
        if (budget.getAmount() > 0) {
            boolean saved = database.save(budget);
            if (saved) {
                notificationController.NotifyUser("A new budget has been created\n");
            }
            return saved;
        }
        return false;
    }

    /**
     * Updates an existing budget.
     * Sends a notification upon successful update.
     * @param updatedBudget the Budget object with updated information
     * @return true if the budget was found and updated, false otherwise
     */
    public boolean updateBudget(Budget updatedBudget) {
        List<Budget> userBudgets = database.findByUserId(updatedBudget.getUserId());
        for (Budget b : userBudgets) {
            if (b.getBudgetId() == updatedBudget.getBudgetId()) {
                boolean updated = database.update(updatedBudget);
                if (updated) {
                    notificationController.NotifyUser("The budget has been updated");
                }
                return updated;
            }
        }
        return false;
    }

    /**
     * Deletes a budget for a specific user.
     * Sends a notification upon successful deletion.
     * @param userId the ID of the user who owns the budget
     * @param budgetId the ID of the budget to delete
     * @return true if the budget was found and deleted, false otherwise
     */
    public boolean deleteBudget(int userId, int budgetId) {
        List<Budget> userBudgets = database.findByUserId(userId);
        for (Budget b : userBudgets) {
            if (b.getBudgetId() == budgetId) {
                boolean deleted = database.delete(budgetId);
                if (deleted) {
                    notificationController.NotifyUser("The budget has been deleted");
                }
                return deleted;
            }
        }
        return false;
    }

    /**
     * Retrieves all budgets for a specific user.
     * @param userId the ID of the user whose budgets to retrieve
     * @return a List of Budget objects belonging to the specified user
     */
    public List<Budget> getBudgetSummary(int userId) {
        return database.findByUserId(userId);
    }

    /**
     * Updates the remaining amount of a budget.
     * Sends a notification upon successful update.
     * @param updatedremaining the Budget object with updated remaining amount
     * @return true if the budget was found and updated, false otherwise
     */
    public boolean updateRemainingBudgets(Budget updatedremaining) {
        List<Budget> userBudgets = database.findByUserId(updatedremaining.getUserId());
        for (Budget b : userBudgets) {
            if (b.getBudgetId() == updatedremaining.getBudgetId()) {
                boolean updated = database.update(updatedremaining);
                if (updated) {
                    notificationController.NotifyUser("The remaining has been updated");
                }
                return updated;
            }
        }
        return false;
    }
}