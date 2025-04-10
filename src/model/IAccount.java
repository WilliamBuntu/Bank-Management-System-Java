package model;

public interface IAccount {
    boolean deposit(double amount);
    boolean withdraw(double amount);
    double getBalance();
    void displayTransactionHistory(int count);
    String getAccountNumber();
    String getAccountType();
}
