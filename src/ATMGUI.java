import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ATMGUI extends JFrame {
    private ATMService atmService;
    private User currentUser;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    // Login components
    private JTextField userIdField;
    private JPasswordField pinField;

    // Main menu components
    private JLabel welcomeLabel;
    private JLabel balanceLabel;

    public ATMGUI(ATMService atmService) {
        this.atmService = atmService;
        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("ATM Interface");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        createLoginPanel();
        createMainMenuPanel();
        createTransactionPanel();
        createWithdrawPanel();
        createDepositPanel();
        createTransferPanel();

        add(mainPanel);
        setVisible(true);
    }

    private void createLoginPanel() {
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel titleLabel = new JLabel("ATM System Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setForeground(new Color(25, 25, 112));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.insets = new Insets(20, 0, 30, 0);
        loginPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1; gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel userIdLabel = new JLabel("User ID:");
        userIdLabel.setFont(new Font("Arial", Font.BOLD, 14));
        loginPanel.add(userIdLabel, gbc);

        userIdField = new JTextField(25);
        userIdField.setFont(new Font("Arial", Font.PLAIN, 14));
        userIdField.setPreferredSize(new Dimension(300, 35));
        gbc.gridx = 1;
        loginPanel.add(userIdField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        JLabel pinLabel = new JLabel("PIN:");
        pinLabel.setFont(new Font("Arial", Font.BOLD, 14));
        loginPanel.add(pinLabel, gbc);

        pinField = new JPasswordField(25);
        pinField.setFont(new Font("Arial", Font.PLAIN, 14));
        pinField.setPreferredSize(new Dimension(300, 35));
        gbc.gridx = 1;
        loginPanel.add(pinField, gbc);

        JButton loginButton = new JButton("LOGIN");
        loginButton.setFont(new Font("Arial", Font.BOLD, 18));
        loginButton.setBackground(new Color(220, 20, 60));
        loginButton.setForeground(Color.BLACK);
        loginButton.setPreferredSize(new Dimension(200, 45));
        loginButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        loginButton.setFocusPainted(false);
        loginButton.setOpaque(true);
        loginButton.setBorderPainted(true);
        loginButton.addActionListener(this::handleLogin);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; gbc.insets = new Insets(30, 0, 0, 0);
        loginPanel.add(loginButton, gbc);

        mainPanel.add(loginPanel, "LOGIN");
    }

    private void createMainMenuPanel() {
        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.setBackground(new Color(240, 248, 255));

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(25, 25, 112));
        welcomeLabel = new JLabel("Welcome!");
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        balanceLabel = new JLabel("Balance: $0.00");
        balanceLabel.setForeground(Color.WHITE);
        balanceLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        headerPanel.add(welcomeLabel);
        headerPanel.add(Box.createHorizontalStrut(20));
        headerPanel.add(balanceLabel);
        menuPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        buttonPanel.setBackground(new Color(240, 248, 255));

        String[] buttonLabels = {"Transaction History", "Withdraw", "Deposit", "Transfer", "Logout", "Quit"};
        String[] actions = {"HISTORY", "WITHDRAW", "DEPOSIT", "TRANSFER", "LOGOUT", "QUIT"};

        for (int i = 0; i < buttonLabels.length; i++) {
            JButton button = createMenuButton(buttonLabels[i], actions[i]);
            buttonPanel.add(button);
        }

        menuPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(menuPanel, "MENU");
    }

    private JButton createMenuButton(String text, String action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.BLACK);
        button.setPreferredSize(new Dimension(180, 50));
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorderPainted(true);
        button.addActionListener(e -> handleMenuAction(action));
        return button;
    }

    private void createTransactionPanel() {
        JPanel transactionPanel = new JPanel(new BorderLayout());
        transactionPanel.setBackground(new Color(240, 248, 255));

        JLabel titleLabel = new JLabel("Transaction History", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        transactionPanel.add(titleLabel, BorderLayout.NORTH);

        JTextArea historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(historyArea);
        transactionPanel.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back to Menu");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(new Color(220, 20, 60));
        backButton.setForeground(Color.BLACK);
        backButton.setPreferredSize(new Dimension(150, 40));
        backButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        backButton.setFocusPainted(false);
        backButton.setOpaque(true);
        backButton.setBorderPainted(true);
        backButton.addActionListener(e -> {
            updateBalance();
            cardLayout.show(mainPanel, "MENU");
        });
        transactionPanel.add(backButton, BorderLayout.SOUTH);

        mainPanel.add(transactionPanel, "HISTORY");
    }

    private void createWithdrawPanel() {
        JPanel withdrawPanel = createTransactionFormPanel("Withdraw Money", "Amount to Withdraw:", "WITHDRAW");
        mainPanel.add(withdrawPanel, "WITHDRAW");
    }

    private void createDepositPanel() {
        JPanel depositPanel = createTransactionFormPanel("Deposit Money", "Amount to Deposit:", "DEPOSIT");
        mainPanel.add(depositPanel, "DEPOSIT");
    }

    private void createTransferPanel() {
        JPanel transferPanel = new JPanel(new GridBagLayout());
        transferPanel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel titleLabel = new JLabel("Transfer Money");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.insets = new Insets(20, 0, 30, 0);
        transferPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1; gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0; gbc.gridy = 1;
        transferPanel.add(new JLabel("To User ID:"), gbc);

        JTextField toUserField = new JTextField(15);
        gbc.gridx = 1;
        transferPanel.add(toUserField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        transferPanel.add(new JLabel("Amount:"), gbc);

        JTextField amountField = new JTextField(15);
        gbc.gridx = 1;
        transferPanel.add(amountField, gbc);

        JButton transferButton = new JButton("Transfer");
        transferButton.setFont(new Font("Arial", Font.BOLD, 14));
        transferButton.setBackground(new Color(34, 139, 34));
        transferButton.setForeground(Color.BLACK);
        transferButton.setPreferredSize(new Dimension(120, 40));
        transferButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        transferButton.setFocusPainted(false);
        transferButton.setOpaque(true);
        transferButton.setBorderPainted(true);
        transferButton.addActionListener(e -> handleTransfer(toUserField.getText(), amountField.getText()));
        gbc.gridx = 0; gbc.gridy = 3;
        transferPanel.add(transferButton, gbc);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(new Color(220, 20, 60));
        backButton.setForeground(Color.BLACK);
        backButton.setPreferredSize(new Dimension(120, 40));
        backButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        backButton.setFocusPainted(false);
        backButton.setOpaque(true);
        backButton.setBorderPainted(true);
        backButton.addActionListener(e -> {
            updateBalance();
            cardLayout.show(mainPanel, "MENU");
        });
        gbc.gridx = 1;
        transferPanel.add(backButton, gbc);

        mainPanel.add(transferPanel, "TRANSFER");
    }

    private JPanel createTransactionFormPanel(String title, String amountLabel, String action) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.insets = new Insets(20, 0, 30, 0);
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1; gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel(amountLabel), gbc);

        JTextField amountField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(amountField, gbc);

        JButton actionButton = new JButton(action.substring(0, 1).toUpperCase() + action.substring(1).toLowerCase());
        actionButton.setFont(new Font("Arial", Font.BOLD, 14));
        actionButton.setBackground(new Color(34, 139, 34));
        actionButton.setForeground(Color.BLACK);
        actionButton.setPreferredSize(new Dimension(120, 40));
        actionButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        actionButton.setFocusPainted(false);
        actionButton.setOpaque(true);
        actionButton.setBorderPainted(true);
        actionButton.addActionListener(e -> handleTransaction(action, amountField.getText()));
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(actionButton, gbc);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(new Color(220, 20, 60));
        backButton.setForeground(Color.BLACK);
        backButton.setPreferredSize(new Dimension(120, 40));
        backButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        backButton.setFocusPainted(false);
        backButton.setOpaque(true);
        backButton.setBorderPainted(true);
        backButton.addActionListener(e -> {
            updateBalance();
            cardLayout.show(mainPanel, "MENU");
        });
        gbc.gridx = 1;
        panel.add(backButton, gbc);

        return panel;
    }

    private void handleLogin(ActionEvent e) {
        String userId = userIdField.getText();
        String pin = new String(pinField.getPassword());

        currentUser = atmService.authenticateUser(userId, pin);
        if (currentUser != null) {
            welcomeLabel.setText("Welcome, " + currentUser.getName() + "!");
            updateBalance();
            cardLayout.show(mainPanel, "MENU");
            userIdField.setText("");
            pinField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Invalid User ID or PIN!", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleMenuAction(String action) {
        switch (action) {
            case "HISTORY":
                showTransactionHistory();
                cardLayout.show(mainPanel, "HISTORY");
                break;
            case "WITHDRAW":
                cardLayout.show(mainPanel, "WITHDRAW");
                break;
            case "DEPOSIT":
                cardLayout.show(mainPanel, "DEPOSIT");
                break;
            case "TRANSFER":
                cardLayout.show(mainPanel, "TRANSFER");
                break;
            case "LOGOUT":
                currentUser = null;
                cardLayout.show(mainPanel, "LOGIN");
                break;
            case "QUIT":
                handleApplicationExit();
                break;
        }
    }

    /**
     * Handle application exit with confirmation
     */
    private void handleApplicationExit() {
        int option = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to exit the ATM system?",
            "Exit Confirmation",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (option == JOptionPane.YES_OPTION) {
            dispose();
            System.exit(0);
        }
    }

    private void handleTransaction(String type, String amountText) {
        try {
            double amount = Double.parseDouble(amountText);
            boolean success = false;

            if (type.equals("WITHDRAW")) {
                success = atmService.withdraw(currentUser.getUserId(), amount);
                if (!success) {
                    JOptionPane.showMessageDialog(this, "Insufficient funds!", "Transaction Failed", JOptionPane.ERROR_MESSAGE);
                }
            } else if (type.equals("DEPOSIT")) {
                atmService.deposit(currentUser.getUserId(), amount);
                success = true;
            }

            if (success) {
                JOptionPane.showMessageDialog(this, "Transaction successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                updateBalance();
                cardLayout.show(mainPanel, "MENU");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleTransfer(String toUserId, String amountText) {
        try {
            double amount = Double.parseDouble(amountText);

            if (!atmService.getAllUserIds().contains(toUserId)) {
                JOptionPane.showMessageDialog(this, "Recipient user not found!", "Transfer Failed", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean success = atmService.transfer(currentUser.getUserId(), toUserId, amount);
            if (success) {
                JOptionPane.showMessageDialog(this, "Transfer successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                updateBalance();
                cardLayout.show(mainPanel, "MENU");
            } else {
                JOptionPane.showMessageDialog(this, "Transfer failed! Check your balance.", "Transfer Failed", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showTransactionHistory() {
        List<Transaction> transactions = atmService.getTransactionHistory(currentUser.getUserId());

        // Find the transaction panel and get the text area
        Component[] components = mainPanel.getComponents();
        JTextArea historyArea = null;

        for (Component comp : components) {
            if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                Component[] panelComponents = panel.getComponents();
                for (Component panelComp : panelComponents) {
                    if (panelComp instanceof JScrollPane) {
                        JScrollPane scrollPane = (JScrollPane) panelComp;
                        Component viewport = scrollPane.getViewport().getView();
                        if (viewport instanceof JTextArea) {
                            historyArea = (JTextArea) viewport;
                            break;
                        }
                    }
                }
                if (historyArea != null) break;
            }
        }

        if (historyArea != null) {
            StringBuilder history = new StringBuilder();
            if (transactions.isEmpty()) {
                history.append("No transactions found.");
            } else {
                for (Transaction transaction : transactions) {
                    history.append(transaction.getTransactionDetails()).append("\n");
                }
            }
            historyArea.setText(history.toString());
        }
    }

    private void updateBalance() {
        if (currentUser != null) {
            double balance = atmService.getBalance(currentUser.getUserId());
            balanceLabel.setText(String.format("Balance: $%.2f", balance));
        }
    }
}
