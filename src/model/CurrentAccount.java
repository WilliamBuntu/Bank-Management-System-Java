package model;

public class CurrentAccount extends Account {
    private final double overdraftLimit;

    public CurrentAccount(String accountNumber, double initialDeposit, double overdraftLimit) {
        super(accountNumber, initialDeposit);
        this.overdraftLimit = overdraftLimit;
        this.accountType = "Current";
    }

    @Override
    protected boolean canWithdraw(double amount) {
        if (balance - amount < -overdraftLimit) {
            System.out.println("Cannot withdraw: Would exceed overdraft limit of $" + overdraftLimit);
            return false;
        }
        return true;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }
}