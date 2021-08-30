package com.monese.banking.model;

public class Transaction {

    private final TransactionType transactionType;
    private final Double amount;
    private final String otherAccount;
    private final Status status;

    public Transaction(TransactionType transactionType, Double amount, String otherAccount, Status status) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.otherAccount = otherAccount;
        this.status = status;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public Double getAmount() {
        return amount;
    }

    public String getOtherAccount() {
        return otherAccount;
    }

    public Status getStatus() {
        return status;
    }
}
