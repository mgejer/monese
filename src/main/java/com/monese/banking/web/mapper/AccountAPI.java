package com.monese.banking.web.mapper;

import java.util.List;

public class AccountAPI {
    private Long id;
    private double balance;
    private List<TransactionAPI> transactions;

    public AccountAPI(Long id, double balance, List<TransactionAPI> transactions) {
        this.id = id;
        this.balance = balance;
        this.transactions = transactions;
    }

    public Long getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public List<TransactionAPI> getTransactions() {
        return transactions;
    }

}
