package application;

public class BalanceSheet {
    private double debits;
    private double credits;

    // Constructor
    public BalanceSheet(double debits, double credits) {
        this.debits = debits;
        this.credits = credits;
    }

    // Getters
    public double getDebits() {
        return debits;
    }

    public double getCredits() {
        return credits;
    }

    // Setters (optional)
    public void setDebits(double debits) {
        this.debits = debits;
    }

    public void setCredits(double credits) {
        this.credits = credits;
    }

    // toString for easy display in TextFlow
    @Override
    public String toString() {
        return "Debits: $" + String.format("%.2f", debits) + "\nCredits: $" + String.format("%.2f", credits);
    }

    // Helper method to check if balanced (for game logic)
    public boolean isBalanced() {
        return Math.abs(debits - credits) < 0.01; // Small tolerance for floating-point comparison
    }
}