import java.util.List;

/**
 * Controller class that manages operations related to user expenses.
 * It interacts with the {@link ExpenseDataBase} to perform CRUD operations.
 * @author Omar Sayed
 */
public class ExpenseController {
    private ExpenseDataBase database;

    /**
     * Constructs an ExpenseController with the given database.
     *
     * @param database the ExpenseDataBase instance to be used
     */
    public ExpenseController(ExpenseDataBase database) {
        this.database = database;
    }

    /**
     * Adds a new expense to the database if the amount is greater than 0.
     *
     * @param expense the expense to be added
     * @return true if the expense is valid and successfully saved; false otherwise
     */
    public boolean addExpense(Expense expense) {
        return expense.getAmount() > 0 && database.save(expense);
    }

    /**
     * Updates an existing expense in the database if it exists for the user.
     *
     * @param expense the expense object containing updated information
     * @return true if the update is successful; false if the expense is not found
     */
    public boolean updateExpense(Expense expense) {
        List<Expense> userExpenses = database.findByUserId(expense.getUserId());
        for (Expense exp : userExpenses) {
            if (exp.getExpenseId() == expense.getExpenseId()) {
                return database.update(expense);
            }
        }
        return false;
    }

    /**
     * Deletes an expense for a given user and expense ID.
     *
     * @param userId    the ID of the user who owns the expense
     * @param expenseId the ID of the expense to be deleted
     * @return true if the expense is found and deleted; false otherwise
     */
    public boolean deleteExpense(int userId, int expenseId) {
        List<Expense> userExpenses = database.findByUserId(userId);
        for (Expense exp : userExpenses) {
            if (exp.getExpenseId() == expenseId) {
                return database.delete(expenseId);
            }
        }
        return false;
    }

    /**
     * Retrieves all expenses associated with a specific user.
     *
     * @param userId the ID of the user
     * @return a list of expenses belonging to the user
     */
    public List<Expense> listExpenses(int userId) {
        return database.findByUserId(userId);
    }
}
