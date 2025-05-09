

import java.text.SimpleDateFormat;
import java.util.Date;

public class Budget {
     static int counter = 1;
    private int budgetId;  // Changed to int
    private int userId;
    private String category;
    private Date startDate;
    private Date endDate;
    private double amount;
    private double remaining;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public Budget(int userId, String category, Date startDate, Date endDate, double amount) {
        this.budgetId = counter++;  // budgetId is now an int and auto-incremented
        this.userId = userId;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.remaining = amount;
    }

    public Budget(int budgetId, int userId, String category, Date startDate, Date endDate, double amount, double remaining) {
        this.budgetId = budgetId;
        this.userId = userId;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.remaining = remaining;
    }

    public int getBudgetId() {
        return budgetId;  // Changed to int
    }

    public int getUserId() {
        return userId;
    }

    public String getCategory() {
        return category;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public double getAmount() {
        return amount;
    }

    public double getRemaining() {
        return remaining;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setRemaining(double remaining) {
        this.remaining = remaining;

    }

    public void updateBudget(String category, double amount) {
        this.category = category;
        this.amount = amount;
        this.remaining = amount;
    }





    @Override
    public String toString() {
        return budgetId + "," + userId + "," + category + "," +
                dateFormat.format(startDate) + "," + dateFormat.format(endDate) + "," +
                amount + "," + remaining;
    }
}
