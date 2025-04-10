package model;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Bank {
    private final Map<String, IAccount> accounts;

    public Bank() {
        this.accounts = new HashMap<>();
    }

    public boolean createSavingsAccount(String accountNumber, double initialDeposit, double minimumBalance) {
        if (accounts.containsKey(accountNumber)) {
            System.out.println("Account number already exists");
            return false;
        }

        accounts.put(accountNumber, new SavingsAccount(accountNumber, initialDeposit, minimumBalance));
        return true;
    }

    public boolean createCurrentAccount(String accountNumber, double initialDeposit, double overdraftLimit) {
        if (accounts.containsKey(accountNumber)) {
            System.out.println("Account number already exists");
            return false;
        }

        accounts.put(accountNumber, new CurrentAccount(accountNumber, initialDeposit, overdraftLimit));
        return true;
    }

    public boolean createFixedDepositAccount(String accountNumber, double initialDeposit,
                                             LocalDate maturityDate, double interestRate) {
        if (accounts.containsKey(accountNumber)) {
            System.out.println("Account number already exists");
            return false;
        }

        accounts.put(accountNumber, new FixedDepositAccount(accountNumber, initialDeposit,
                maturityDate, interestRate));
        return true;
    }

    public boolean deposit(String accountNumber, double amount) {
        IAccount account = accounts.get(accountNumber);
        if (account == null) {
            System.out.println("Account not found");
            return false;
        }

        return account.deposit(amount);
    }

    public boolean withdraw(String accountNumber, double amount) {
        IAccount account = accounts.get(accountNumber);
        if (account == null) {
            System.out.println("Account not found");
            return false;
        }

        return account.withdraw(amount);
    }

    public double getBalance(String accountNumber) {
        IAccount account = accounts.get(accountNumber);
        if (account == null) {
            System.out.println("Account not found");
            return -1;
        }

        return account.getBalance();
    }

    public void displayTransactionHistory(String accountNumber, int count) {
        IAccount account = accounts.get(accountNumber);
        if (account == null) {
            System.out.println("Account not found");
            return;
        }

        account.displayTransactionHistory(count);
    }

    public IAccount getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }
}