package com.monese.banking.model;

import java.util.List;

public class Account {

    private final String accountId;
    private final Double balance;
    private final List<Transaction> transactions;

    public Account(String accountId, Double balance, List<Transaction> transactions) {
        this.accountId = accountId;
        this.balance = balance;
        this.transactions = transactions;
    }

    public String getAccountId() {
        return accountId;
    }

    public Double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
