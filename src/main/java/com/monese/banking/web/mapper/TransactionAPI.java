package com.monese.banking.web.mapper;

public class TransactionAPI {
    private final long id;
    private final TransactionType type;
    private final String date;
    private final long account;
    private final double amount;

    public TransactionAPI(long id, TransactionType type, String date, long account, double amount) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.account = account;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public long getAccount() {
        return account;
    }

    public double getAmount() {
        return amount;
    }
}
