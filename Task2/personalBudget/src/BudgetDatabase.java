import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Handles persistent storage and retrieval of budget data.
 * Manages loading from and saving to a file, and provides CRUD operations for budgets.
 * @author Kholod Ahmed
 */
public class BudgetDatabase {
    private ArrayList<Budget> budgets;
    private String fileName;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Constructs a BudgetDatabase with the specified file name.
     * Automatically loads existing budgets from the file during initialization.
     * @param fileName the name of the file to use for data persistence
     */
    public BudgetDatabase(String fileName) {
        this.fileName = fileName;
        this.budgets = new ArrayList<>();
        loadFromFile();
    }

    /**
     * Loads budget data from the file into memory.
     * Parses each line of the file into Budget objects and updates the budget counter.
     */
    private void loadFromFile() {
        File file = new File(fileName);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 7);
                if (parts.length < 7) continue;

                int budgetId = Integer.parseInt(parts[0]);
                int userId = Integer.parseInt(parts[1]);
                String category = parts[2];
                Date startDate = dateFormat.parse(parts[3]);
                Date endDate = dateFormat.parse(parts[4]);
                double amount = Double.parseDouble(parts[5]);
                double remaining = Double.parseDouble(parts[6]);

                Budget b = new Budget(budgetId, userId, category, startDate, endDate, amount, remaining);
                budgets.add(b);
            }
            if (budgets.toArray().length > 0) {
                Budget.counter = budgets.get(budgets.toArray().length - 1).getBudgetId() + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves all budget records from memory to the file.
     * Writes each Budget object as a line in the file.
     */
    private void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (Budget b : budgets) {
                bw.write(b.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new budget to the database and persists it to file.
     * @param budget the Budget object to be saved
     * @return true if the budget was successfully added, false otherwise
     */
    public boolean save(Budget budget) {
        boolean added = budgets.add(budget);
        if (added) saveToFile();
        return added;
    }

    /**
     * Retrieves all budgets for a specific user.
     * @param userId the ID of the user whose budgets to retrieve
     * @return an ArrayList of Budget objects belonging to the specified user
     */
    public ArrayList<Budget> findByUserId(int userId) {
        ArrayList<Budget> result = new ArrayList<>();
        for (Budget b : budgets) {
            if (b.getUserId() == userId) {
                result.add(b);
            }
        }
        return result;
    }

    /**
     * Updates an existing budget in the database.
     * @param updatedBudget the Budget object with updated information
     * @return true if the budget was found and updated, false otherwise
     */
    public boolean update(Budget updatedBudget) {
        for (int i = 0; i < budgets.size(); i++) {
            Budget b = budgets.get(i);
            if (b.getBudgetId() == updatedBudget.getBudgetId()) {
                budgets.set(i, updatedBudget);
                saveToFile();
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes a budget from the database.
     * @param budgetId the ID of the budget to be deleted
     * @return true if the budget was found and deleted, false otherwise
     */
    public boolean delete(int budgetId) {
        for (int i = 0; i < budgets.size(); i++) {
            if (budgets.get(i).getBudgetId() == budgetId) {
                budgets.remove(i);
                saveToFile();
                return true;
            }
        }
        return false;
    }
}