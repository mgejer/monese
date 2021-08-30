package com.monese.banking.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
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

}
