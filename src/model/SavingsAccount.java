package model;

public class SavingsAccount extends Account {
    private final double minimumBalance;

    public SavingsAccount(String accountNumber, double initialDeposit, double minimumBalance) {
        super(accountNumber, initialDeposit);
        this.minimumBalance = minimumBalance;
        this.accountType = "Savings";

        if (initialDeposit < minimumBalance) {
            System.out.println("Warning: Initial deposit is below minimum balance requirement");
        }
    }

    @Override
    protected boolean canWithdraw(double amount) {
        if (balance - amount < minimumBalance) {
            System.out.println("Cannot withdraw: Would fall below minimum balance of $" + minimumBalance);
            return false;
        }
        return true;
    }

    public double getMinimumBalance() {
        return minimumBalance;
    }
}