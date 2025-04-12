package model;

import java.time.LocalDate;

public class FixedDepositAccount extends Account {
    private final LocalDate maturityDate;
    private final double interestRate;

    public FixedDepositAccount(String accountNumber, double initialDeposit,
                               LocalDate maturityDate, double interestRate) {
        super(accountNumber, initialDeposit);
        this.maturityDate = maturityDate;
        this.interestRate = interestRate;
        this.accountType = "Fixed Deposit";
    }

    // This constructor is kept for backward compatibility
    public FixedDepositAccount(String accountNumber, double initialDeposit,
                               int lockInPeriodMonths, double interestRate) {
        super(accountNumber, initialDeposit);
        this.maturityDate = LocalDate.now().plusMonths(lockInPeriodMonths);
        this.interestRate = interestRate;
        this.accountType = "Fixed Deposit";
    }

    @Override
    protected boolean canWithdraw(double amount) {
        if (LocalDate.now().isBefore(maturityDate)) {
            System.out.println("Cannot withdraw: Account is locked until " + maturityDate);
            return false;
        }
        return true;
    }

    @Override
    public boolean deposit(double amount) {
        if (LocalDate.now().isBefore(maturityDate)) {
            System.out.println("Cannot deposit: Account is locked until " + maturityDate);
            return false;
        }
        return super.deposit(amount);
    }

    public LocalDate getMaturityDate() {
        return maturityDate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    // Calculate interest amount based on time held
    public double calculateInterest() {
        return balance * (interestRate / 100);
    }
}