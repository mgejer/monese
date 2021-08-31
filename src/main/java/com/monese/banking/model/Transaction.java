package com.monese.banking.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator = "transaction_seq")
    private Long id;
    private Double amount;
    private LocalDateTime date;
    private long origin;
    private long destination;

    public Transaction(Double amount, LocalDateTime date, long origin, long destination) {
        this.amount = amount;
        this.date = date;
        this.origin = origin;
        this.destination = destination;
    }

    public Transaction() {
    }

    public Long getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public long getOrigin() {
        return origin;
    }

    public long getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", date=" + date +
                ", origin=" + origin +
                ", destination=" + destination +
                '}';
    }
}
