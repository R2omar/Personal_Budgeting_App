import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents an income record with details about the income transaction.
 * This class stores information such as income ID, user ID, amount, source,
 * date received, and description of the income.
 * @author Kholod Ahmed
 */
public class Income {
    private int IncomeId;
    private int userId;
    private double amount;
    private String Source;
    private Date dateReceived;
    private String description;
    static int counter = 1;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Gets the income ID.
     * @return the unique identifier for this income record
     */
    public int getIncomeId() {
        return IncomeId;
    }

    /**
     * Gets the user ID associated with this income.
     * @return the user ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Gets the amount of this income.
     * @return the income amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Gets the source of this income.
     * @return the income source
     */
    public String getSource() {
        return Source;
    }

    /**
     * Gets the date when this income was received.
     * @return the date of income receipt
     */
    public Date getdateReceived() {
        return dateReceived;
    }

    /**
     * Gets the description of this income.
     * @return the income description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the amount for this income.
     * @param amount the new amount to set
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Sets the source for this income.
     * @param Source the new source to set
     */
    public void setSource(String Source) {
        this.Source = Source;
    }

    /**
     * Sets the received date for this income.
     * @param dateReceived the new date to set
     */
    public void setdateReceived(Date dateReceived) {
        this.dateReceived = dateReceived;
    }

    /**
     * Sets the description for this income.
     * @param description the new description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Constructs a new Income object with auto-incremented ID.
     * @param userId the ID of the user associated with this income
     * @param amount the amount of the income
     * @param Source the source of the income
     * @param dateReceived the date when the income was received
     * @param description the description of the income
     */
    public Income(int userId, double amount, String Source, Date dateReceived, String description) {
        this.IncomeId = counter;
        this.userId = userId;
        this.amount = amount;
        this.Source = Source;
        this.dateReceived = dateReceived;
        this.description = description;
        counter++;
    }

    /**
     * Constructs a new Income object with a specific ID.
     * @param IncomeId the specific ID to assign to this income
     * @param userId the ID of the user associated with this income
     * @param amount the amount of the income
     * @param Source the source of the income
     * @param dateReceived the date when the income was received
     * @param description the description of the income
     */
    public Income(int IncomeId, int userId, double amount, String Source, Date dateReceived, String description) {
        this.IncomeId = IncomeId;
        this.userId = userId;
        this.amount = amount;
        this.Source = Source;
        this.dateReceived = dateReceived;
        this.description = description;
    }

    /**
     * Returns a string representation of the income in CSV format.
     * @return a string containing income details separated by commas
     */
    @Override
    public String toString() {
        return IncomeId + "," + userId + "," + amount + "," + Source + "," + dateFormat.format(dateReceived) + "," + description;
    }
}