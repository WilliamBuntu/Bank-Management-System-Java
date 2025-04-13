# Bank Account Management System

## Overview
The Bank Account Management System is a comprehensive JavaFX application designed to improve the digital banking experience by allowing customers to efficiently manage their accounts. This system supports multiple account types, processes transactions securely, and maintains a clear history of all customer transactions.

## Features

### Multiple Account Types
- **Savings Account**: Allows deposits and withdrawals with a minimum balance requirement. Does not permit overdrafts.
- **Current Account**: Designed for frequent transactions with overdraft capabilities up to a predefined limit.
- **Fixed Deposit Account**: Enables customers to deposit a fixed amount that cannot be withdrawn until a maturity date, offering higher interest rates.

### Core Banking Operations
- Deposit funds
- Withdraw funds (with account-specific rules)
- Check account balance
- View transaction history

### Transaction Management
- Complete transaction history stored in an efficient linked list structure
- Ability to view recent transactions
- Detailed transaction records for tracking spending

### User Interface
- Modern JavaFX interface for intuitive user experience
- Interactive account management
- Real-time transaction processing
- Visual representation of transaction history

## Technical Implementation

### Object-Oriented Design
The system demonstrates key OOP concepts:
- **Inheritance**: Account type hierarchy with shared base functionality
- **Polymorphism**: Different behavior implementation across account types
- **Abstract Classes**: Blueprint structure ensuring consistency across account types
- **Interfaces**: Common contract for banking operations

### Data Structures
- **Linked Lists**: Efficient storage and retrieval of transaction history
- Support for adding, removing, and accessing transaction data

### Account Features
- Interest calculation for applicable accounts
- Overdraft management for current accounts
- Maturity date enforcement for fixed deposits
- Minimum balance requirements for savings accounts

## Installation Requirements

### Prerequisites
- Java Development Kit (JDK) 24 or higher
- JavaFX SDK
- IDE supporting Java and JavaFX ( IntelliJ IDEA, )

### Setup Instructions
1. Clone the repository
2. Import the project into your IDE
3. Ensure JavaFX is configured correctly
4. Build and run the application

## Usage Guide

### Creating a New Account
1. Launch the application
2. Select "Create New Account" option
3. Choose the account type
4. Fill in required information
5. Complete initial deposit (if applicable)

### Making Transactions
1. Select an existing account
2. Choose transaction type (deposit or withdrawal)
3. Enter amount
4. Confirm transaction details
5. Review updated balance

### Viewing Transaction History
1. Select an account
2. Navigate to "Transaction History"
3. View list of past transactions
4. Filter or sort as needed

## Development Information
- **Date**: April 10, 2025
- **Project Type**: Educational Banking System
- **Framework**: JavaFX with OOP principles

## Contributors
- Buntu William
  **Video link**: https://screenrec.com/share/yzm2rlajq7

