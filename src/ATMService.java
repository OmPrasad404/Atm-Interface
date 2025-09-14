import java.util.*;

public class ATMService {
    private Map<String, User> users;
    private Map<String, Account> accounts;
    private Map<String, List<Transaction>> transactionHistory;
    private int transactionCounter;

    public ATMService() {
        users = new HashMap<>();
        accounts = new HashMap<>();
        transactionHistory = new HashMap<>();
        transactionCounter = 1;
        initializeData();
    }

    private void initializeData() {
        // Initialize demo users and accounts
        User user1 = new User("user123", "1234", "Om");
        User user2 = new User("user456", "5678", "Ram");

        users.put("user123", user1);
        users.put("user456", user2);

        Account acc1 = new Account("ACC001", "user123", 5000.0);
        Account acc2 = new Account("ACC002", "user456", 3000.0);

        accounts.put("user123", acc1);
        accounts.put("user456", acc2);

        transactionHistory.put("user123", new ArrayList<>());
        transactionHistory.put("user456", new ArrayList<>());
    }

    public User authenticateUser(String userId, String pin) {
        User user = users.get(userId);
        if (user != null && user.authenticate(userId, pin)) {
            return user;
        }
        return null;
    }

    public double getBalance(String userId) {
        Account account = accounts.get(userId);
        return account != null ? account.getBalance() : 0;
    }

    public boolean withdraw(String userId, double amount) {
        Account account = accounts.get(userId);
        if (account != null && account.withdraw(amount)) {
            addTransaction(userId, "WITHDRAW", amount, "Cash withdrawal");
            return true;
        }
        return false;
    }

    public void deposit(String userId, double amount) {
        Account account = accounts.get(userId);
        if (account != null) {
            account.deposit(amount);
            addTransaction(userId, "DEPOSIT", amount, "Cash deposit");
        }
    }

    public boolean transfer(String fromUserId, String toUserId, double amount) {
        Account fromAccount = accounts.get(fromUserId);
        Account toAccount = accounts.get(toUserId);

        if (fromAccount != null && toAccount != null && fromAccount.transfer(toAccount, amount)) {
            addTransaction(fromUserId, "TRANSFER_OUT", amount, "Transfer to " + toUserId);
            addTransaction(toUserId, "TRANSFER_IN", amount, "Transfer from " + fromUserId);
            return true;
        }
        return false;
    }

    public List<Transaction> getTransactionHistory(String userId) {
        return transactionHistory.getOrDefault(userId, new ArrayList<>());
    }

    private void addTransaction(String userId, String type, double amount, String description) {
        String transactionId = "TXN" + String.format("%06d", transactionCounter++);
        Transaction transaction = new Transaction(transactionId, type, amount, description);
        transactionHistory.get(userId).add(transaction);
    }

    public Set<String> getAllUserIds() {
        return users.keySet();
    }
}
