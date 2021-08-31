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

    public Account(Double balance) {
        this.balance = balance;
    }

    public Account() {
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


    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", balance=" + balance +
                '}';
    }
}
