import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private String transactionId;
    private String type;
    private double amount;
    private LocalDateTime timestamp;
    private String description;

    public Transaction(String transactionId, String type, double amount, String description) {
        this.transactionId = transactionId;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.timestamp = LocalDateTime.now();
    }

    public String getTransactionDetails() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return String.format("ID: %s | Type: %s | Amount: $%.2f | Time: %s | %s",
                           transactionId, type, amount, timestamp.format(formatter), description);
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}

