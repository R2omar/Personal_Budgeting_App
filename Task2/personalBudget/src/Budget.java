import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents a budget with its associated details including time period, amount, and remaining balance.
 * Tracks budget allocations for specific categories over defined date ranges.
 * @author Kholod Ahmed
 */
public class Budget {
    static int counter = 1;
    private int budgetId;
    private int userId;
    private String category;
    private Date startDate;
    private Date endDate;
    private double amount;
    private double remaining;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Constructs a new Budget with auto-incremented ID and initializes remaining amount to full amount.
     * @param userId the ID of the user this budget belongs to
     * @param category the budget category/type
     * @param startDate the start date of the budget period
     * @param endDate the end date of the budget period
     * @param amount the total allocated budget amount
     */
    public Budget(int userId, String category, Date startDate, Date endDate, double amount) {
        this.budgetId = counter++;
        this.userId = userId;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.remaining = amount;
    }

    /**
     * Constructs a Budget with all fields specified (including remaining amount).
     * @param budgetId the specific ID to assign to this budget
     * @param userId the ID of the user this budget belongs to
     * @param category the budget category/type
     * @param startDate the start date of the budget period
     * @param endDate the end date of the budget period
     * @param amount the total allocated budget amount
     * @param remaining the remaining/unspent budget amount
     */
    public Budget(int budgetId, int userId, String category, Date startDate, Date endDate, double amount, double remaining) {
        this.budgetId = budgetId;
        this.userId = userId;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.remaining = remaining;
    }

    /**
     * @return the unique identifier of this budget
     */
    public int getBudgetId() {
        return budgetId;
    }

    /**
     * @return the ID of the user this budget belongs to
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @return the category/type of this budget
     */
    public String getCategory() {
        return category;
    }

    /**
     * @return the start date of the budget period
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @return the end date of the budget period
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @return the total allocated amount for this budget
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @return the remaining/unspent amount in this budget
     */
    public double getRemaining() {
        return remaining;
    }

    /**
     * Updates the total budget amount (resets remaining amount to new value).
     * @param amount the new budget amount
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Updates the budget category.
     * @param category the new category/type
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Updates the budget period start date.
     * @param startDate the new start date
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Updates the budget period end date.
     * @param endDate the new end date
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Updates the remaining budget amount.
     * @param remaining the new remaining amount
     */
    public void setRemaining(double remaining) {
        this.remaining = remaining;
    }

    /**
     * Updates both category and amount (resets remaining to new amount).
     * @param category the new budget category
     * @param amount the new budget amount
     */
    public void updateBudget(String category, double amount) {
        this.category = category;
        this.amount = amount;
        this.remaining = amount;
    }

    /**
     * Returns a string representation of the budget in CSV format.
     * @return comma-separated string containing all budget fields
     */
    @Override
    public String toString() {
        return budgetId + "," + userId + "," + category + "," +
                dateFormat.format(startDate) + "," + dateFormat.format(endDate) + "," +
                amount + "," + remaining;
    }
}