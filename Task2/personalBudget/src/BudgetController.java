import java.util.List;
import java.util.Scanner;


public class BudgetController {
    private BudgetDatabase database;
    private NotificationController notificationController;

    public BudgetController(BudgetDatabase database, NotificationController notificationController) {
        this.database = database;
        this.notificationController = notificationController;
    }

    public boolean setBudget(Budget budget) {
        if (budget.getAmount() > 0) {
            boolean saved = database.save(budget);
            if (saved) {
                notificationController.NotifyUser("A new budget has been created");
            }
            return saved;
        }
        return false;
    }

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

    public List<Budget> getBudgetSummary(int userId) {
        return database.findByUserId(userId);
    }



    public boolean updateremainingBudgets(Budget updatedremaining) {
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
