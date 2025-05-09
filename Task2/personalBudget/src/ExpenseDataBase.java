import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ExpenseDataBase {
    private ArrayList<Expense> expenses;
    private String fileName;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public ExpenseDataBase(String fileName) {
        this.fileName = fileName;
        this.expenses = new ArrayList<>();
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

                int expId = Integer.parseInt(parts[0]);
                int userId = Integer.parseInt(parts[1]);
                double amount = Double.parseDouble(parts[2]);
                String category = parts[3];
                Date date = dateFormat.parse(parts[4]);
                String paymentMethod = parts[5];
                String description = parts[6];

                Expense expense = new Expense(expId,userId, amount, category, date,paymentMethod, description);
                expenses.add(expense);
            }
           if(expenses.toArray().length > 0) Expense.counter = expenses.get(expenses.toArray().length - 1).getExpenseId() + 1 ;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
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

    public boolean save(Expense expense) {
        boolean added = expenses.add(expense);
        if (added) saveToFile();
        return added;
    }

    public ArrayList<Expense> findByUserId(int userId) {
        ArrayList<Expense> result = new ArrayList<>();
        for (Expense e : expenses) {
            if (e.getUserId() == userId) {
                result.add(e);
            }
        }
        return result;
    }

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

    public boolean delete(int expenseId) {
        for (int i = 0; i < expenses.size(); i++) {
            if (expenses.get(i).getExpenseId() == expenseId) {
                expenses.remove(i);
                if(expenses.toArray().length > 0) Expense.counter = expenses.get(expenses.toArray().length - 1).getExpenseId() + 1 ;
                saveToFile();
                return true;
            }
        }
        return false;
    }


}
