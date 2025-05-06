import java.util.List;

public class ExpenseController {
    private ExpenseDataBase database;

    public ExpenseController(ExpenseDataBase database) {
        this.database = database;
    }

    public boolean addExpense(Expense expense) {
        return expense.getAmount() > 0 &&  database.save(expense);
    }

    public boolean updateExpense(Expense expense) {
        List<Expense> userExpenses = database.findByUserId(expense.getUserId());
        for (Expense exp : userExpenses) {
            if (exp.getExpenseId() == expense.getExpenseId()) {
                return database.update(expense);
            }
        }
        return false;
    }

    public boolean deleteExpense(int userId, int expenseId) {
        List<Expense> userExpenses = database.findByUserId(userId);
        for (Expense exp : userExpenses) {
            if (exp.getExpenseId() == expenseId) {
                return database.delete(expenseId);
            }
        }
        return false;
    }

    public List<Expense> listExpenses(int userId) {
        return database.findByUserId(userId);
    }
}
