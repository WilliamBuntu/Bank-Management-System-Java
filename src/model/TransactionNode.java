package model;

public class TransactionNode {
    private final Transaction transaction;
    private TransactionNode next;

    public TransactionNode(Transaction transaction) {
        this.transaction = transaction;
        this.next = null;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public TransactionNode getNext() {
        return next;
    }

    public void setNext(TransactionNode next) {
        this.next = next;
    }
}