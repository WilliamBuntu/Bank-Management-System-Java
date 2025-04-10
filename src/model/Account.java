package model;


public abstract class Account implements IAccount {
    protected String accountNumber;
    protected double balance;
    protected TransactionHistory transactionHistory;
    protected String accountType;

    public Account(String accountNumber, double initialDeposit) {
        this.accountNumber = accountNumber;
        this.balance = initialDeposit;
        this.transactionHistory = new TransactionHistory();

        // Record initial deposit as first transaction
        if (initialDeposit > 0) {
            this.transactionHistory.addTransaction(
                    new Transaction("Initial Deposit", initialDeposit, initialDeposit)
            );
        }
    }

    @Override
    public boolean deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive");
            return false;
        }

        balance += amount;
        transactionHistory.addTransaction(
                new Transaction("Deposit", amount, balance)
        );
        return true;
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive");
            return false;
        }

        if (canWithdraw(amount)) {
            balance -= amount;
            transactionHistory.addTransaction(
                    new Transaction("Withdrawal", amount, balance)
            );
            return true;
        }

        return false;
    }

    // Abstract method to be implemented by subclasses
    protected abstract boolean canWithdraw(double amount);

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public void displayTransactionHistory(int count) {
        System.out.println("Transaction History for Account: " + accountNumber);
        transactionHistory.displayTransactions(count);
    }

    @Override
    public String getAccountNumber() {
        return accountNumber;
    }

    @Override
    public String getAccountType() {
        return accountType;
    }

    public TransactionHistory getTransactionHistory() {
        return transactionHistory;
    }
}