// Main.java
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Main extends Application {
    private Bank bank;
    private TextField userNameField;
    private TextField accountNumberField;
    private TextField amountField;
    private TextArea resultArea;
    private ComboBox<String> accountTypeComboBox;
    private TextField maturityDateField;
    private Label statusLabel;

    @Override
    public void start(Stage primaryStage) {
        bank = new Bank();

        // Create main container
        BorderPane mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(20));
        mainLayout.setStyle("-fx-background-color: #f5f8fa;");

        // Create header
        HBox header = createHeader();
        mainLayout.setTop(header);

        // Create content area with form and result panels
        SplitPane contentSplitPane = new SplitPane();
        contentSplitPane.setDividerPositions(0.5);
        contentSplitPane.setPadding(new Insets(15, 0, 15, 0));

        // Form panel (left side)
        VBox formPanel = createFormPanel();

        // Result panel (right side)
        VBox resultPanel = createResultPanel();

        contentSplitPane.getItems().addAll(formPanel, resultPanel);
        mainLayout.setCenter(contentSplitPane);

        // Create status bar
        HBox statusBar = createStatusBar();
        mainLayout.setBottom(statusBar);

        // Scene setup
        Scene scene = new Scene(mainLayout, 900, 600);
        primaryStage.setTitle("Abadasigana Bank");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private HBox createHeader() {
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.setSpacing(15);
        header.setPadding(new Insets(0, 0, 15, 0));
        header.setStyle("-fx-border-color: transparent transparent #3498db transparent; -fx-border-width: 0 0 2 0;");

        // Logo
        StackPane logo = new StackPane();
        logo.setPrefSize(50, 50);
        logo.setStyle("-fx-background-color: #3498db; -fx-background-radius: 8;");

        Text bankIcon = new Text("AB");
        bankIcon.setFill(Color.WHITE);
        bankIcon.setFont(Font.font("System", FontWeight.BOLD, 20));
        logo.getChildren().add(bankIcon);

        // App title
        VBox titleBox = new VBox(2);
        Text title = new Text("Abadasigana Bank");
        title.setFont(Font.font("System", FontWeight.BOLD, 22));
        title.setFill(Color.web("#2c3e50"));

        Text subtitle = new Text("Account Management System");
        subtitle.setFont(Font.font("System", FontWeight.NORMAL, 14));
        subtitle.setFill(Color.web("#7f8c8d"));

        titleBox.getChildren().addAll(title, subtitle);

        header.getChildren().addAll(logo, titleBox);
        return header;
    }

    private VBox createFormPanel() {
        VBox formPanel = new VBox(15);
        formPanel.setPadding(new Insets(10));
        formPanel.setStyle("-fx-background-color: white; -fx-background-radius: 8; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);");

        // Panel Title
        Text formTitle = new Text("Account Operations");
        formTitle.setFont(Font.font("System", FontWeight.BOLD, 16));

        // Field Container
        VBox fieldsContainer = new VBox(12);
        fieldsContainer.setPadding(new Insets(10, 0, 10, 0));

        // Account Type Selection
        VBox typeBox = new VBox(5);
        Label typeLabel = new Label("Account Type:");
        typeLabel.setStyle("-fx-font-weight: bold;");
        accountTypeComboBox = new ComboBox<>();
        accountTypeComboBox.getItems().addAll("Savings", "Current", "Fixed Deposit");
        accountTypeComboBox.setPromptText("Select Account Type");
        accountTypeComboBox.setPrefWidth(Double.MAX_VALUE);
        accountTypeComboBox.setStyle("-fx-background-radius: 4;");
        typeBox.getChildren().addAll(typeLabel, accountTypeComboBox);

        // Name Field
        VBox nameBox = new VBox(5);
        Label nameLabel = new Label("Your Name:");
        nameLabel.setStyle("-fx-font-weight: bold;");
        userNameField = new TextField();
        userNameField.setPromptText("Enter your full name");
        userNameField.setStyle("-fx-background-radius: 4;");
        nameBox.getChildren().addAll(nameLabel, userNameField);

        // Account Number
        VBox accountBox = new VBox(5);
        Label accNumLabel = new Label("Account Number:");
        accNumLabel.setStyle("-fx-font-weight: bold;");
        accountNumberField = new TextField();
        accountNumberField.setPromptText("Enter account number");
        accountNumberField.setStyle("-fx-background-radius: 4; -fx-border-color: #3498db; -fx-border-width: 1; -fx-border-radius: 4;");
        accountBox.getChildren().addAll(accNumLabel, accountNumberField);

        // Amount
        VBox amountBox = new VBox(5);
        Label amountLabel = new Label("Amount:");
        amountLabel.setStyle("-fx-font-weight: bold;");
        amountField = new TextField();
        amountField.setPromptText("Enter amount");
        amountField.setStyle("-fx-background-radius: 4;");
        amountBox.getChildren().addAll(amountLabel, amountField);

        // Maturity Date
        VBox maturityBox = new VBox(5);
        Label maturityDateLabel = new Label("Maturity Date (yyyy-MM-dd):");
        maturityDateLabel.setStyle("-fx-font-weight: bold;");
        maturityDateField = new TextField();
        maturityDateField.setPromptText("YYYY-MM-DD");
        maturityDateField.setStyle("-fx-background-radius: 4;");
        maturityBox.getChildren().addAll(maturityDateLabel, maturityDateField);

        // Initially hide maturity date fields
        maturityBox.setVisible(false);
        maturityBox.setManaged(false);

        // Show/hide maturity date field based on account type selection
        accountTypeComboBox.setOnAction(e -> {
            boolean isFixedDeposit = "Fixed Deposit".equals(accountTypeComboBox.getValue());
            maturityBox.setVisible(isFixedDeposit);
            maturityBox.setManaged(isFixedDeposit);
        });

        fieldsContainer.getChildren().addAll(typeBox, nameBox, accountBox, amountBox, maturityBox);

        // Buttons in grid layout
        GridPane buttonGrid = new GridPane();
        buttonGrid.setHgap(10);
        buttonGrid.setVgap(10);
        buttonGrid.setPrefWidth(Double.MAX_VALUE); // Make grid take full width

        // Create buttons with improved styling
        Button createAccountBtn = createStyledButton("Create Account", "#2196F3");
        Button depositBtn = createStyledButton("Deposit", "#4CAF50");
        Button withdrawBtn = createStyledButton("Withdraw", "#F44336");
        Button checkBalanceBtn = createStyledButton("Check Balance", "#FFC107");
        Button transactionHistoryBtn = createStyledButton("Transaction History", "#673AB7");

        // Make buttons take full width in their cells
        createAccountBtn.setMaxWidth(Double.MAX_VALUE);
        depositBtn.setMaxWidth(Double.MAX_VALUE);
        withdrawBtn.setMaxWidth(Double.MAX_VALUE);
        checkBalanceBtn.setMaxWidth(Double.MAX_VALUE);
        transactionHistoryBtn.setMaxWidth(Double.MAX_VALUE);

        // Add buttons to grid
        buttonGrid.add(createAccountBtn, 0, 0, 2, 1); // span 2 columns
        buttonGrid.add(depositBtn, 0, 1);
        buttonGrid.add(withdrawBtn, 1, 1);
        buttonGrid.add(checkBalanceBtn, 0, 2);
        buttonGrid.add(transactionHistoryBtn, 1, 2);

        // Set column constraints for even button sizing
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        buttonGrid.getColumnConstraints().addAll(col1, col2);

        // Event Handlers
        createAccountBtn.setOnAction(e -> createAccount());
        depositBtn.setOnAction(e -> makeDeposit());
        withdrawBtn.setOnAction(e -> makeWithdrawal());
        checkBalanceBtn.setOnAction(e -> checkBalance());
        transactionHistoryBtn.setOnAction(e -> showTransactionHistory());

        formPanel.getChildren().addAll(formTitle, fieldsContainer, buttonGrid);
        return formPanel;
    }

    private Button createStyledButton(String text, String baseColor) {
        Button button = new Button(text);
        button.setStyle(
                "-fx-background-color: " + baseColor + ";" +
                        "-fx-text-fill: " + (baseColor.equals("#FFC107") ? "black" : "white") + ";" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 10 15;" +
                        "-fx-background-radius: 4;"
        );

        // Add hover effect
        button.setOnMouseEntered(e ->
                button.setStyle(
                        "-fx-background-color: derive(" + baseColor + ", -10%);" +
                                "-fx-text-fill: " + (baseColor.equals("#FFC107") ? "black" : "white") + ";" +
                                "-fx-font-weight: bold;" +
                                "-fx-padding: 10 15;" +
                                "-fx-background-radius: 4;"
                )
        );

        button.setOnMouseExited(e ->
                button.setStyle(
                        "-fx-background-color: " + baseColor + ";" +
                                "-fx-text-fill: " + (baseColor.equals("#FFC107") ? "black" : "white") + ";" +
                                "-fx-font-weight: bold;" +
                                "-fx-padding: 10 15;" +
                                "-fx-background-radius: 4;"
                )
        );

        return button;
    }

    private VBox createResultPanel() {
        VBox resultPanel = new VBox(15);
        resultPanel.setPadding(new Insets(10));
        resultPanel.setStyle("-fx-background-color: white; -fx-background-radius: 8; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);");

        // Panel Title
        Text resultTitle = new Text("Account Information & Transaction History");
        resultTitle.setFont(Font.font("System", FontWeight.BOLD, 16));

        // Result Area with styled header
        VBox resultBox = new VBox(5);

        HBox resultHeader = new HBox();
        resultHeader.setAlignment(Pos.CENTER_LEFT);
        resultHeader.setPadding(new Insets(5, 10, 5, 10));
        resultHeader.setStyle("-fx-background-color: #f8f9fa; -fx-background-radius: 4 4 0 0;");

        Label resultHeaderLabel = new Label("Results & Transaction History");
        resultHeaderLabel.setFont(Font.font("System", FontWeight.BOLD, 12));
        resultHeader.getChildren().add(resultHeaderLabel);

        resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setPrefHeight(350);
        resultArea.setWrapText(true);
        resultArea.setStyle("-fx-font-family: 'Consolas', 'Monaco', monospace; -fx-font-size: 13px;");

        resultBox.getChildren().addAll(resultHeader, resultArea);

        resultPanel.getChildren().addAll(resultTitle, resultBox);
        return resultPanel;
    }

    private HBox createStatusBar() {
        HBox statusBar = new HBox();
        statusBar.setPadding(new Insets(10, 0, 0, 0));
        statusBar.setStyle("-fx-border-color: #e0e0e0 transparent transparent transparent; -fx-border-width: 1 0 0 0;");

        statusLabel = new Label("Ready");
        statusLabel.setTextFill(Color.web("#7f8c8d"));

        statusBar.getChildren().add(statusLabel);
        return statusBar;
    }

    private void createAccount() {
        try {
            String accountNumber = accountNumberField.getText();
            String accountType = accountTypeComboBox.getValue();
            String userName = userNameField.getText();
            double initialAmount = Double.parseDouble(amountField.getText());

            // Input validation
            if (accountNumber.isEmpty() || userName.isEmpty()) {
                updateStatus("Please enter both account number and name", true);
                return;
            }

            // Validate amount is positive
            if (initialAmount <= 0) {
                updateStatus("Initial amount must be greater than zero", true);
                return;
            }

            boolean success = false;

            if (accountType == null) {
                updateStatus("Please select an account type", true);
                return;
            }

            if (accountType.equals("Savings")) {
                success = bank.createSavingsAccount(accountNumber, initialAmount, 100);
            } else if (accountType.equals("Current")) {
                success = bank.createCurrentAccount(accountNumber, initialAmount, 500);
            } else if (accountType.equals("Fixed Deposit")) {
                // Parse and validate the maturity date
                try {
                    LocalDate maturityDate = LocalDate.parse(maturityDateField.getText(),
                            DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                    // Validate maturity date is in the future
                    if (maturityDate.isBefore(LocalDate.now()) || maturityDate.isEqual(LocalDate.now())) {
                        updateStatus("Maturity date must be in the future", true);
                        return;
                    }

                    success = bank.createFixedDepositAccount(accountNumber, initialAmount, maturityDate, 5.0);
                } catch (DateTimeParseException ex) {
                    updateStatus("Invalid date format. Please use YYYY-MM-DD format.", true);
                    return;
                }
            }

            if (success) {
                updateStatus("Account created successfully", false);
                resultArea.setText("✅ Successfully created " + accountType + " account #" + accountNumber + "\n" +
                        "Account Holder: " + userName + "\n" +
                        "Initial Balance: $" + String.format("%.2f", initialAmount));
            } else {
                updateStatus("Failed to create account", true);
                resultArea.setText("❌ Failed to create account. Account number may already exist.");
            }
        } catch (NumberFormatException e) {
            updateStatus("Please enter valid amount", true);
        }
    }

    private void makeDeposit() {
        try {
            String accountNumber = accountNumberField.getText();
            double amount = Double.parseDouble(amountField.getText());

            // Validate amount
            if (amount <= 0) {
                updateStatus("Deposit amount must be greater than zero", true);
                return;
            }

            // Get the account to check its type
            IAccount account = bank.getAccount(accountNumber);

            if (account == null) {
                updateStatus("Account not found", true);
                resultArea.setText("❌ Account not found. Please check account number.");
                return;
            }

            // Check if it's a Fixed Deposit account and if maturity date has passed
            if (account.getAccountType().equals("Fixed Deposit")) {
                FixedDepositAccount fdAccount = (FixedDepositAccount) account;
                if (LocalDate.now().isBefore(fdAccount.getMaturityDate())) {
                    updateStatus("Cannot deposit before maturity date", true);
                    resultArea.setText("❌ Cannot deposit to Fixed Deposit account before maturity date: " +
                            fdAccount.getMaturityDate());
                    return;
                }
            }

            boolean success = bank.deposit(accountNumber, amount);

            if (success) {
                double newBalance = bank.getBalance(accountNumber);
                updateStatus("Deposit successful", false);
                resultArea.setText(String.format("✅ Successfully deposited $%.2f\n\n" +
                                "Account: #%s (%s)\n" +
                                "New balance: $%.2f",
                        amount, accountNumber, account.getAccountType(), newBalance));
            } else {
                updateStatus("Deposit failed", true);
                resultArea.setText("❌ Deposit failed. Please check account number and amount.");
            }
        } catch (NumberFormatException e) {
            updateStatus("Please enter valid amount", true);
        }
    }

    private void makeWithdrawal() {

        try {
            String accountNumber = accountNumberField.getText();
            double amount = Double.parseDouble(amountField.getText());
            IAccount account = bank.getAccount(accountNumber);




            if (account == null) {
                updateStatus("Account not found", true);
                resultArea.setText("❌ Account not found. Please check account number.");
                return;
            }

            // Check if it's a Fixed Deposit account and if maturity date has passed
            if (account.getAccountType().equals("Fixed Deposit")) {
                FixedDepositAccount fdAccount = (FixedDepositAccount) account;
                if (LocalDate.now().isBefore(fdAccount.getMaturityDate())) {
                    updateStatus("Cannot withdraw before maturity date", true);
                    resultArea.setText("❌ Cannot withdraw from Fixed Deposit account before maturity date: " +
                            fdAccount.getMaturityDate());
                    return;
                }


                // Validate amount
                if (amount <= 0) {
                    updateStatus("Withdrawal amount must be greater than zero", true);
                    return;
                }

            }

            // Check if withdrawal amount is less than or equal to balance
            double currentBalance = bank.getBalance(accountNumber);
            if (amount > currentBalance && account.getAccountType().equals("Savings") ) {
                updateStatus("Insufficient funds for withdrawal", true);
                resultArea.setText(String.format("❌ Insufficient funds for withdrawal.\n" +
                                "Current balance: $%.2f\n" +
                                "Requested withdrawal: $%.2f",
                        currentBalance, amount));
                return;
            }

            boolean success = bank.withdraw(accountNumber, amount);

            if (success) {
                double newBalance = bank.getBalance(accountNumber);
                updateStatus("Withdrawal successful", false);
                resultArea.setText(String.format("✅ Successfully withdrew $%.2f\n\n" +
                                "Account: #%s (%s)\n" +
                                "New balance: $%.2f",
                        amount, accountNumber, account.getAccountType(), newBalance));
            } else {
                updateStatus("Withdrawal failed", true);
                resultArea.setText("❌ Withdrawal failed. Please check account number, amount, and account rules.");
            }
        } catch (NumberFormatException e) {
            updateStatus("Please enter valid amount", true);
        }
    }

    private void checkBalance() {
        String accountNumber = accountNumberField.getText();
        double balance = bank.getBalance(accountNumber);

        if (balance >= -500) {
            IAccount account = bank.getAccount(accountNumber);
            String additionalInfo = "";

            if (account.getAccountType().equals("Fixed Deposit")) {
                FixedDepositAccount fdAccount = (FixedDepositAccount) account;
                additionalInfo = "\nMaturity Date: " + fdAccount.getMaturityDate().toString();
            }

            updateStatus("Balance retrieved successfully", false);
            resultArea.setText(String.format("📊 Account Information\n\n" +
                            "Account Number: #%s\n" +
                            "Type: %s\n" +
                            "Current Balance: $%.2f%s",
                    accountNumber, account.getAccountType(), balance, additionalInfo));
        } else {
            updateStatus("Failed to retrieve balance", true);
            resultArea.setText("❌ Failed to retrieve balance. Please check account number.");
        }
    }




    private void showTransactionHistory() {
        String accountNumber = accountNumberField.getText();
        IAccount account = bank.getAccount(accountNumber);

        if (account != null) {
            updateStatus("Transaction history loaded", false);



            StringBuilder historyText = new StringBuilder();
            historyText.append("🧾 Transaction History\n\n");
            historyText.append("Account: #").append(accountNumber)
                    .append(" (").append(account.getAccountType()).append(")\n");
            historyText.append("Current Balance: $").append(String.format("%.2f", bank.getBalance(accountNumber))).append("\n");

            // Add maturity date info for Fixed Deposit accounts
            if (account.getAccountType().equals("Fixed Deposit")) {
                FixedDepositAccount fdAccount = (FixedDepositAccount) account;
                historyText.append("Maturity Date: ").append(fdAccount.getMaturityDate()).append("\n");
            }

            historyText.append("\n----- Recent Transactions -----\n\n");

            TransactionHistory history = ((Account)account).getTransactionHistory();
            for (Transaction transaction : history.getRecentTransactions(10)) {
                historyText.append(transaction.toString()).append("\n");
            }

            resultArea.setText(historyText.toString());
        } else {
            updateStatus("Account not found", true);
            resultArea.setText("❌ Account not found. Please check account number.");
        }
    }

    private void updateStatus(String message, boolean isError) {
        statusLabel.setText(message);
        statusLabel.setTextFill(isError ? Color.RED : Color.web("#2ecc71"));
    }

    public static void main(String[] args) {
        launch(args);
    }
}