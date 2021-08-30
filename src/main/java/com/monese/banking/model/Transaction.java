package com.monese.banking.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private Double amount;
    private LocalDateTime date;
    private long origin;
    private long destination;
    @Enumerated(EnumType.STRING)
    private Status status;

    public Transaction(Double amount, LocalDateTime date, long origin, long destination, Status status) {
        this.amount = amount;
        this.date = date;
        this.origin = origin;
        this.destination = destination;
        this.status = status;
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

    public Status getStatus() {
        return status;
    }
}
