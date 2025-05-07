import java.util.List;


public class IncomeController {

    private IncomeRepository database;

    public IncomeController(IncomeRepository database) {
        this.database = database;
    }

    public boolean addIncome(Income income) {
        return income.getAmount() > 0 &&  database.save(income);
    }

    public boolean updateIncome(Income income) {
        List<Income> userIncome = database.findByUserId(income.getUserId());
        for (Income inc : userIncome) {
            if (inc.getIncomeId() == income.getIncomeId()) {
                return database.update(income);
            }
        }
        return false;
    }

    public boolean deleteIncome(int userId, int IncomeId) {
        List<Income> userExpenses = database.findByUserId(userId);
        for (Income exp : userExpenses) {
            if (exp.getIncomeId() == IncomeId) {
                return database.delete(IncomeId);
            }
        }
        return false;
    }

    public List<Income> listIncome(int userId) {
        return database.findByUserId(userId);
    }
}
