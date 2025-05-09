import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Repository class for managing income data persistence.
 * Handles loading from and saving to a file, and provides CRUD operations for income records.
 * @author Kholod Ahmed
 */
public class IncomeRepository {
    private ArrayList<Income> Incomes;
    private String fileName;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Constructs an IncomeRepository with the specified file name.
     * Automatically loads existing income data from the file.
     * @param fileName the name of the file to use for data persistence
     */
    public IncomeRepository(String fileName) {
        this.fileName = fileName;
        this.Incomes = new ArrayList<>();
        loadFromFile();
    }

    /**
     * Loads income data from the file into memory.
     * Parses each line of the file into Income objects.
     * Automatically updates the Income counter to maintain unique IDs.
     */
    private void loadFromFile() {
        File file = new File(fileName);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 6);
                if (parts.length < 6) continue;

                int incomeId = Integer.parseInt(parts[0]);
                int userId = Integer.parseInt(parts[1]);
                double amount = Double.parseDouble(parts[2]);
                String Source = parts[3];
                Date dateReceived = dateFormat.parse(parts[4]);

                String description = parts[5];

                Income income = new Income(incomeId, userId, amount, Source, dateReceived, description);
                Incomes.add(income);
            }
            if (Incomes.toArray().length > 0) Income.counter = Incomes.get(Incomes.toArray().length - 1).getIncomeId() + 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves all income records from memory to the file.
     * Writes each Income object as a line in the file.
     */
    private void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (Income e : Incomes) {
                bw.write(e.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new income record to the repository and persists it to file.
     * @param income the income object to be saved
     * @return true if the income was successfully added, false otherwise
     */
    public boolean save(Income income) {
        boolean added = Incomes.add(income);
        if (added) saveToFile();
        return added;
    }

    /**
     * Retrieves all income records for a specific user.
     * @param userId the ID of the user whose incomes to retrieve
     * @return an ArrayList of Income objects belonging to the specified user
     */
    public ArrayList<Income> findByUserId(int userId) {
        ArrayList<Income> result = new ArrayList<>();
        for (Income e : Incomes) {
            if (e.getUserId() == userId) {
                result.add(e);
            }
        }
        return result;
    }

    /**
     * Updates an existing income record in the repository.
     * @param updatedIncome the income object with updated information
     * @return true if the income was found and updated, false otherwise
     */
    public boolean update(Income updatedIncome) {
        for (int i = 0; i < Incomes.size(); i++) {
            Income e = Incomes.get(i);
            if (e.getIncomeId() == updatedIncome.getIncomeId()) {
                Incomes.set(i, updatedIncome);
                saveToFile();
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes an income record from the repository.
     * @param incomeId the ID of the income to be deleted
     * @return true if the income was found and deleted, false otherwise
     */
    public boolean delete(int incomeId) {
        for (int i = 0; i < Incomes.size(); i++) {
            if (Incomes.get(i).getIncomeId() == incomeId) {
                Incomes.remove(i);
                if (Incomes.toArray().length > 0) Income.counter = Incomes.get(Incomes.toArray().length - 1).getIncomeId() + 1;
                saveToFile();
                return true;
            }
        }
        return false;
    }
}