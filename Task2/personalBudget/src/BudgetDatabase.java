import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BudgetDatabase {
    private ArrayList<Budget> budgets;
    private String fileName;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public BudgetDatabase(String fileName) {
        this.fileName = fileName;
        this.budgets = new ArrayList<>();
        loadFromFile();
    }

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

    public boolean save(Budget budget) {
        boolean added = budgets.add(budget);
        if (added) saveToFile();
        return added;
    }

    public ArrayList<Budget> findByUserId(int userId) {
        ArrayList<Budget> result = new ArrayList<>();
        for (Budget b : budgets) {
            if (b.getUserId() == userId) {
                result.add(b);
            }
        }
        return result;
    }

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
