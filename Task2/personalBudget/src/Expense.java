import java.text.SimpleDateFormat;
import java.util.Date;

public class Expense {
    private int expenseId;
    private int userId;
    private double amount;
    private String category;
    private Date date;
    private String paymentMethod;
    private String description;
    static int  counter = 1;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public int getExpenseId() {
        return expenseId;
    }

    public int getUserId() {
        return userId;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Expense(int userId, double amount, String category, Date date,String paymentMethod ,String description) {
        this.expenseId = counter;
        this.userId = userId;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.paymentMethod = paymentMethod;
        this.description = description;
        counter++;
    }
    public Expense(int expenseId,int userId, double amount, String category, Date date,String paymentMethod ,String description) {
        this.expenseId = expenseId;
        this.userId = userId;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.paymentMethod = paymentMethod;
        this.description = description;
    }

    @Override
    public String toString() {
        return expenseId + "," + userId + "," + amount + "," + category + "," + dateFormat.format(date) + "," + paymentMethod + "," + description;
    }
}
