import java.text.SimpleDateFormat;
import java.util.Date;

public class Income {
    private int IncomeId;
    private int userId;
    private double amount;
    private String Source;
    private Date dateReceived;
    private String description;
    static int counter = 1;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public int getIncomeId() {
        return IncomeId;
    }

    public int getUserId() {
        return userId;
    }

    public double getAmount() {
        return amount;
    }

    public String getSource() {
        return Source;
    }

    public Date getdateReceived() {
        return dateReceived;
    }

    public String getDescription() {
        return description;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setSource(String Source) {
        this.Source = Source;
    }

    public void setdateReceived(Date dateReceived) {
        this.dateReceived = dateReceived;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Income(int userId, double amount, String Source, Date dateReceived, String description) {
        this.IncomeId = counter;
        this.userId = userId;
        this.amount = amount;
        this.Source = Source;
        this.dateReceived = dateReceived;
        this.description = description;
        counter++;
    }

    public Income(int IncomeId, int userId, double amount, String Source, Date dateReceived, String description) {
        this.IncomeId = IncomeId;
        this.userId = userId;
        this.amount = amount;
        this.Source = Source;
        this.dateReceived = dateReceived;
        this.description = description;
    }

    @Override
    public String toString() {
        return IncomeId + "," + userId + "," + amount + "," + Source + "," + dateFormat.format(dateReceived) + "," + description;
    }
}

