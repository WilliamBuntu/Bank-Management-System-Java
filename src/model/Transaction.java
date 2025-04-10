package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private final String type;
    private final double amount;
    private final LocalDateTime dateTime;
    private final double balanceAfterTransaction;

    public Transaction(String type, double amount, double balanceAfterTransaction) {
        this.type = type;
        this.amount = amount;
        this.dateTime = LocalDateTime.now();
        this.balanceAfterTransaction = balanceAfterTransaction;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public double getBalanceAfterTransaction() {
        return balanceAfterTransaction;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("%s: $%.2f on %s, Balance: $%.2f",
                type, amount, dateTime.format(formatter), balanceAfterTransaction);
    }
}