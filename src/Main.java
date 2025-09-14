import javax.swing.*;

/**
 * Main Controller class for ATM Interface Application
 * This class serves as the entry point and controller for the entire ATM system
 */
public class Main {
    private ATMService atmService;
    private ATMGUI atmGUI;

    public Main() {
        initializeApplication();
    }

    /**
     * Initialize the ATM application components
     */
    private void initializeApplication() {
        try {
            // Set system look and feel for better UI experience
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException |
                 IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.err.println("Warning: Could not set system look and feel. Using default.");
            // Using System.err instead of printStackTrace for better error handling
        }

        // Initialize ATM service
        atmService = new ATMService();

        // Initialize and start GUI
        startGUI();
    }

    /**
     * Start the GUI application
     */
    private void startGUI() {
        SwingUtilities.invokeLater(() -> {
            atmGUI = new ATMGUI(atmService);
            System.out.println("ATM Interface started successfully!");
            printWelcomeMessage();
        });
    }

    /**
     * Print welcome message with demo account information
     */
    private void printWelcomeMessage() {
        System.out.println("========================================");
        System.out.println("         ATM INTERFACE SYSTEM          ");
        System.out.println("========================================");
        System.out.println("Demo Accounts Available:");
        System.out.println("1. User ID: user123, PIN: 1234 (Om)");
        System.out.println("2. User ID: user456, PIN: 5678 (Ram)");
        System.out.println("========================================");
    }


    /**
     * Main method - Entry point of the application
     */
    public static void main(String[] args) {
        System.out.println("Starting ATM Interface Application...");
        new Main();
    }
}