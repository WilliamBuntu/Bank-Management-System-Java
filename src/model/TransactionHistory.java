package model;

import java.util.ArrayList;
import java.util.List;

public class TransactionHistory {
    private TransactionNode head;
    private int size;

    public TransactionHistory() {
        this.head = null;
        this.size = 0;
    }

    public void addTransaction(Transaction transaction) {
        TransactionNode newNode = new TransactionNode(transaction);

        if (head == null) {
            head = newNode;
        } else {
            newNode.setNext(head);
            head = newNode;
        }

        size++;
    }

    public int getSize() {
        return size;
    }

    public List<Transaction> getRecentTransactions(int count) {
        List<Transaction> recentTransactions = new ArrayList<>();
        TransactionNode current = head;
        int counter = 0;

        while (current != null && counter < count) {
            recentTransactions.add(current.getTransaction());
            current = current.getNext();
            counter++;
        }

        return recentTransactions;
    }

    public void displayTransactions(int count) {
        TransactionNode current = head;
        int counter = 0;

        System.out.println("Recent Transactions:");
        while (current != null && counter < count) {
            System.out.println(current.getTransaction());
            current = current.getNext();
            counter++;
        }

        if (counter == 0) {
            System.out.println("No transactions found.");
        }
    }
}