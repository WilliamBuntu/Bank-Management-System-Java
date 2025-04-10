// Main.java
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Main extends Application {
    private Bank bank;
    private TextField accountNumberField;
    private TextField amountField;
    private TextArea resultArea;
    private ComboBox<String> accountTypeComboBox;
    private TextField maturityDateField;
    private Label maturityDateLabel;

    @Override
    public void start(Stage primaryStage) {
        bank = new Bank();

        // Create UI components
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        // Account Type Selection
        accountTypeComboBox = new ComboBox<>();
        accountTypeComboBox.getItems().addAll("Savings", "Current", "Fixed Deposit");
        accountTypeComboBox.setPromptText("Select Account Type");
        grid.add(new Label("Account Type:"), 0, 0);
        grid.add(accountTypeComboBox, 1, 0);

        // Account Number
        accountNumberField = new TextField();
        grid.add(new Label("Account Number:"), 0, 1);
        grid.add(accountNumberField, 1, 1);

        // Amount
        amountField = new TextField();
        grid.add(new Label("Amount:"), 0, 2);
        grid.add(amountField, 1, 2);

        // Maturity Date for Fixed Deposit
        maturityDateLabel = new Label("Maturity Date (yyyy-MM-dd):");
        maturityDateField = new TextField();
        maturityDateField.setPromptText("YYYY-MM-DD");
        grid.add(maturityDateLabel, 0, 3);
        grid.add(maturityDateField, 1, 3);

        // Initially hide maturity date fields
        maturityDateLabel.setVisible(false);
        maturityDateField.setVisible(false);

        // Show/hide maturity date field based on account type selection
        accountTypeComboBox.setOnAction(e -> {
            boolean isFixedDeposit = "Fixed Deposit".equals(accountTypeComboBox.getValue());
            maturityDateLabel.setVisible(isFixedDeposit);
            maturityDateField.setVisible(isFixedDeposit);
        });

        // Buttons for operations
        Button createAccountBtn = new Button("Create Account");
        Button depositBtn = new Button("Deposit");
        Button withdrawBtn = new Button("Withdraw");
        Button checkBalanceBtn = new Button("Check Balance");
        Button transactionHistoryBtn = new Button("Transaction History");

        grid.add(createAccountBtn, 0, 4);
        grid.add(depositBtn, 1, 4);
        grid.add(withdrawBtn, 0, 5);
        grid.add(checkBalanceBtn, 1, 5);
        grid.add(transactionHistoryBtn, 0, 6, 2, 1);

        // Result Area
        resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setPrefHeight(200);

        // Event Handlers
        createAccountBtn.setOnAction(e -> createAccount());
        depositBtn.setOnAction(e -> makeDeposit());
        withdrawBtn.setOnAction(e -> makeWithdrawal());
        checkBalanceBtn.setOnAction(e -> checkBalance());
        transactionHistoryBtn.setOnAction(e -> showTransactionHistory());

        // Main Layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.getChildren().addAll(grid, resultArea);

        // Scene setup
        Scene scene = new Scene(layout, 400, 500);
        primaryStage.setTitle("Bank Account Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createAccount() {
        try {
            String accountNumber = accountNumberField.getText();
            String accountType = accountTypeComboBox.getValue();
            double initialAmount = Double.parseDouble(amountField.getText());

            boolean success = false;

            if (accountType == null) {
                resultArea.setText("Please select an account type");
                return;
            }

            if (accountType.equals("Savings")) {
                success = bank.createSavingsAccount(accountNumber, initialAmount, 100);
            } else if (accountType.equals("Current")) {
                success = bank.createCurrentAccount(accountNumber, initialAmount, 500);
            } else if (accountType.equals("Fixed Deposit")) {
                // Parse the maturity date
                try {
                    LocalDate maturityDate = LocalDate.parse(maturityDateField.getText(),
                            DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                    success = bank.createFixedDepositAccount(accountNumber, initialAmount, maturityDate, 5.0);
                } catch (DateTimeParseException ex) {
                    resultArea.setText("Invalid date format. Please use YYYY-MM-DD format.");
                    return;
                }
            }

            if (success) {
                resultArea.setText("Successfully created " + accountType + " account #" + accountNumber);
            } else {
                resultArea.setText("Failed to create account. Account number may already exist.");
            }
        } catch (NumberFormatException e) {
            resultArea.setText("Please enter valid amount");
        }
    }

    private void makeDeposit() {
        try {
            String accountNumber = accountNumberField.getText();
            double amount = Double.parseDouble(amountField.getText());

            // Get the account to check its type
            IAccount account = bank.getAccount(accountNumber);


            if (account == null) {
                resultArea.setText("Account not found. Please check account number.");
                return;
            }

            // Check if it's a Fixed Deposit account and if maturity date has passed
            if (account.getAccountType().equals("Fixed Deposit")) {
                FixedDepositAccount fdAccount = (FixedDepositAccount) account;
                if (LocalDate.now().isBefore(fdAccount.getMaturityDate())) {
                    resultArea.setText("Cannot deposit to Fixed Deposit account before maturity date: " +
                            fdAccount.getMaturityDate());
                    return;
                }
            }

            boolean success = bank.deposit(accountNumber, amount);

            if (success) {
                double newBalance = bank.getBalance(accountNumber);
                resultArea.setText(String.format("Successfully deposited $%.2f\nNew balance: $%.2f",
                        amount, newBalance));
            } else {
                resultArea.setText("Deposit failed. Please check account number and amount.");
            }
        } catch (NumberFormatException e) {
            resultArea.setText("Please enter valid amount");
        }
    }

    private void makeWithdrawal() {
        try {
            String accountNumber = accountNumberField.getText();
            double amount = Double.parseDouble(amountField.getText());

            // Get the account to check its type
            IAccount account = bank.getAccount(accountNumber);

            if (account == null) {
                resultArea.setText("Account not found. Please check account number.");
                return;
            }

            // This check is technically redundant since canWithdraw() will check maturity date,
            // but including it here gives us a more specific error message
            if (account.getAccountType().equals("Fixed Deposit")) {
                FixedDepositAccount fdAccount = (FixedDepositAccount) account;
                if (LocalDate.now().isBefore(fdAccount.getMaturityDate())) {
                    resultArea.setText("Cannot withdraw from Fixed Deposit account before maturity date: " +
                            fdAccount.getMaturityDate());
                    return;
                }
            }

            boolean success = bank.withdraw(accountNumber, amount);

            if (success) {
                double newBalance = bank.getBalance(accountNumber);
                resultArea.setText(String.format("Successfully withdrew $%.2f\nNew balance: $%.2f",
                        amount, newBalance));
            } else {
                resultArea.setText("Withdrawal failed. Please check account number, amount, and account rules.");
            }
        } catch (NumberFormatException e) {
            resultArea.setText("Please enter valid amount");
        }
    }

    private void checkBalance() {
        String accountNumber = accountNumberField.getText();
        double balance = bank.getBalance(accountNumber);

        if (balance >= 0) {
            IAccount account = bank.getAccount(accountNumber);
            String additionalInfo = "";

            if (account.getAccountType().equals("Fixed Deposit")) {
                FixedDepositAccount fdAccount = (FixedDepositAccount) account;
                additionalInfo = "\nMaturity Date: " + fdAccount.getMaturityDate().toString();
            }

            resultArea.setText(String.format("Current balance for account #%s: $%.2f%s",
                    accountNumber, balance, additionalInfo));
        } else {
            resultArea.setText("Failed to retrieve balance. Please check account number.");
        }
    }

    private void showTransactionHistory() {
        String accountNumber = accountNumberField.getText();
        IAccount account = bank.getAccount(accountNumber);

        if (account != null) {
            StringBuilder historyText = new StringBuilder();
            historyText.append("Transaction History for Account #").append(accountNumber)
                    .append(" (").append(account.getAccountType()).append(")").append(":\n\n");

            // Add maturity date info for Fixed Deposit accounts
            if (account.getAccountType().equals("Fixed Deposit")) {
                FixedDepositAccount fdAccount = (FixedDepositAccount) account;
                historyText.append("Maturity Date: ").append(fdAccount.getMaturityDate()).append("\n\n");
            }

            TransactionHistory history = ((Account)account).getTransactionHistory();
            for (Transaction transaction : history.getRecentTransactions(10)) {
                historyText.append(transaction.toString()).append("\n");
            }

            resultArea.setText(historyText.toString());
        } else {
            resultArea.setText("Account not found. Please check account number.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}