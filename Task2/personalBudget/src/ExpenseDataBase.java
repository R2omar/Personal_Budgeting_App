import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A class that manages expense records stored in a file-based database.
 * It supports loading, saving, updating, and deleting expenses for users.
 * @author Omar Sayed
 */
public class ExpenseDataBase {
    private ArrayList<Expense> expenses;
    private String fileName;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Constructs an ExpenseDataBase and loads data from the specified file.
     *
     * @param fileName the name of the file to read and write expense data
     */
    public ExpenseDataBase(String fileName) {
        this.fileName = fileName;
        this.expenses = new ArrayList<>();
        loadFromFile();
    }

    /**
     * Loads all expense records from the file into memory.
     * Updates the static counter to ensure unique expense IDs.
     */
    private void loadFromFile() {
        File file = new File(fileName);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 7);
                if (parts.length < 7) continue;

                int expId = Integer.parseInt(parts[0]);
                int userId = Integer.parseInt(parts[1]);
                double amount = Double.parseDouble(parts[2]);
                String category = parts[3];
                Date date = dateFormat.parse(parts[4]);
                String paymentMethod = parts[5];
                String description = parts[6];

                Expense expense = new Expense(expId, userId, amount, category, date, paymentMethod, description);
                expenses.add(expense);
            }
            if (expenses.toArray().length > 0)
                Expense.counter = expenses.get(expenses.toArray().length - 1).getExpenseId() + 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves all in-memory expense records back to the file.
     */
    private void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (Expense e : expenses) {
                bw.write(e.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves a new expense to the in-memory list and writes it to the file.
     *
     * @param expense the expense to be added
     * @return true if the expense was added successfully; false otherwise
     */
    public boolean save(Expense expense) {
        boolean added = expenses.add(expense);
        if (added) saveToFile();
        return added;
    }

    /**
     * Finds all expenses associated with a specific user.
     *
     * @param userId the ID of the user
     * @return a list of expenses for that user
     */
    public ArrayList<Expense> findByUserId(int userId) {
        ArrayList<Expense> result = new ArrayList<>();
        for (Expense e : expenses) {
            if (e.getUserId() == userId) {
                result.add(e);
            }
        }
        return result;
    }

    /**
     * Updates an existing expense in the in-memory list and file.
     *
     * @param updatedExpense the expense object with updated fields
     * @return true if the expense was found and updated; false otherwise
     */
    public boolean update(Expense updatedExpense) {
        for (int i = 0; i < expenses.size(); i++) {
            Expense e = expenses.get(i);
            if (e.getExpenseId() == updatedExpense.getExpenseId()) {
                expenses.set(i, updatedExpense);
                saveToFile();
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes an expense by its ID and updates the file and counter.
     *
     * @param expenseId the ID of the expense to delete
     * @return true if the expense was found and removed; false otherwise
     */
    public boolean delete(int expenseId) {
        for (int i = 0; i < expenses.size(); i++) {
            if (expenses.get(i).getExpenseId() == expenseId) {
                expenses.remove(i);
                if (expenses.toArray().length > 0)
                    Expense.counter = expenses.get(expenses.toArray().length - 1).getExpenseId() + 1;
                saveToFile();
                return true;
            }
        }
        return false;
    }
}
