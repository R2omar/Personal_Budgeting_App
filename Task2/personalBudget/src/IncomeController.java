import java.util.List;

/**
 * Controller class for managing income-related operations.
 * Acts as an intermediary between the application and the income data repository,
 * providing methods to add, update, delete, and list income records.
 * @author Kholod Ahmed
 */
public class IncomeController {

    private IncomeRepository database;

    /**
     * Constructs an IncomeController with the specified repository.
     * @param database the income repository to be used for data operations
     */
    public IncomeController(IncomeRepository database) {
        this.database = database;
    }

    /**
     * Adds a new income record to the repository if the amount is valid.
     * @param income the income object to be added
     * @return true if the income was successfully saved and the amount is positive, false otherwise
     */
    public boolean addIncome(Income income) {
        return income.getAmount() > 0 && database.save(income);
    }

    /**
     * Updates an existing income record in the repository.
     * @param income the income object with updated information
     * @return true if the income was found and successfully updated, false otherwise
     */
    public boolean updateIncome(Income income) {
        List<Income> userIncome = database.findByUserId(income.getUserId());
        for (Income inc : userIncome) {
            if (inc.getIncomeId() == income.getIncomeId()) {
                return database.update(income);
            }
        }
        return false;
    }

    /**
     * Deletes an income record from the repository.
     * @param userId the ID of the user whose income is to be deleted
     * @param IncomeId the ID of the income record to be deleted
     * @return true if the income was found and successfully deleted, false otherwise
     */
    public boolean deleteIncome(int userId, int IncomeId) {
        List<Income> userExpenses = database.findByUserId(userId);
        for (Income exp : userExpenses) {
            if (exp.getIncomeId() == IncomeId) {
                return database.delete(IncomeId);
            }
        }
        return false;
    }

    /**
     * Retrieves a list of all income records for a specific user.
     * @param userId the ID of the user whose incomes are to be retrieved
     * @return a list of Income objects belonging to the specified user
     */
    public List<Income> listIncome(int userId) {
        return database.findByUserId(userId);
    }
}