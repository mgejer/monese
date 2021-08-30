package com.monese.banking.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private Double balance;
    //@OneToMany
    //private final List<Transaction> transactions;

    public Account(Double balance) {
        this.balance = balance;
      //  this.transactions = transactions;
    }

    public Long getId() {
        return id;
    }

    public Double getBalance() {
        return balance;
    }

    public void addToBalance(double amount) {
        balance += amount;
    }

    /*public List<Transaction> getTransactions() {
        return transactions;
    }*/

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", balance=" + balance +
                '}';
    }
}
