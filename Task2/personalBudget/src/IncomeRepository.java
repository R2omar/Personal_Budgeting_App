import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class IncomeRepository {
    private ArrayList<Income> Incomes;
    private String fileName;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public IncomeRepository(String fileName) {
        this.fileName = fileName;
        this.Incomes = new ArrayList<>();
        loadFromFile();
    }
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

                Income income = new Income(incomeId,userId, amount, Source, dateReceived, description);
                Incomes.add(income);
            }
            if(Incomes.toArray().length > 0) Income.counter = Incomes.get(Incomes.toArray().length - 1).getIncomeId() + 1 ;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
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

    public boolean save(Income income) {
        boolean added = Incomes.add(income);
        if (added) saveToFile();
        return added;
    }

    public ArrayList<Income> findByUserId(int userId) {
        ArrayList<Income> result = new ArrayList<>();
        for (Income e : Incomes) {
            if (e.getUserId() == userId) {
                result.add(e);
            }
        }
        return result;
    }

    public boolean update(Income updatedIncome) {
        for (int i = 0; i <Incomes.size(); i++) {
            Income e = Incomes.get(i);
            if (e.getIncomeId() == updatedIncome.getIncomeId()) {
                Incomes.set(i, updatedIncome);
                saveToFile();
                return true;
            }
        }
        return false;
    }

    public boolean delete(int incomeId) {
        for (int i = 0; i < Incomes.size(); i++) {
            if (Incomes.get(i).getIncomeId() == incomeId) {
                Incomes.remove(i);
                if(Incomes.toArray().length > 0) Income.counter = Incomes.get(Incomes.toArray().length - 1).getIncomeId() + 1 ;
                saveToFile();
                return true;
            }
        }
        return false;
    }


}