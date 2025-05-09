import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents an expense made by a user.
 * Each expense has an ID, user ID, amount, category, date, payment method, and description.
 * @author Omar Sayed
 */
public class Expense {
    private int expenseId;
    private int userId;
    private double amount;
    private String category;
    private Date date;
    private String paymentMethod;
    private String description;

    /**
     * Static counter used to auto-increment expense IDs.
     */
    static int counter = 1;

    /**
     * Format used to display the expense date in "yyyy-MM-dd".
     */
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Returns the ID of the expense.
     *
     * @return the expense ID
     */
    public int getExpenseId() {
        return expenseId;
    }

    /**
     * Returns the user ID associated with the expense.
     *
     * @return the user ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Returns the amount of the expense.
     *
     * @return the expense amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Returns the category of the expense.
     *
     * @return the expense category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Returns the date of the expense.
     *
     * @return the expense date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Returns the description of the expense.
     *
     * @return the expense description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the amount of the expense.
     *
     * @param amount the new amount
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Sets the category of the expense.
     *
     * @param category the new category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Sets the date of the expense.
     *
     * @param date the new date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Sets the description of the expense.
     *
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the payment method used for the expense.
     *
     * @return the payment method
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * Sets the payment method of the expense.
     *
     * @param paymentMethod the new payment method
     */
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     * Constructs a new Expense object with a generated ID.
     *
     * @param userId        the ID of the user who made the expense
     * @param amount        the amount of the expense
     * @param category      the category of the expense
     * @param date          the date of the expense
     * @param paymentMethod the payment method used
     * @param description   a description of the expense
     */
    public Expense(int userId, double amount, String category, Date date, String paymentMethod, String description) {
        this.expenseId = counter;
        this.userId = userId;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.paymentMethod = paymentMethod;
        this.description = description;
        counter++;
    }

    /**
     * Constructs a new Expense object with a provided ID.
     *
     * @param expenseId     the ID of the expense
     * @param userId        the ID of the user who made the expense
     * @param amount        the amount of the expense
     * @param category      the category of the expense
     * @param date          the date of the expense
     * @param paymentMethod the payment method used
     * @param description   a description of the expense
     */
    public Expense(int expenseId, int userId, double amount, String category, Date date, String paymentMethod, String description) {
        this.expenseId = expenseId;
        this.userId = userId;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.paymentMethod = paymentMethod;
        this.description = description;
    }

    /**
     * Returns a string representation of the expense in CSV format.
     *
     * @return a string representing the expense
     */
    @Override
    public String toString() {
        return expenseId + "," + userId + "," + amount + "," + category + "," + dateFormat.format(date) + "," + paymentMethod + "," + description;
    }
}
