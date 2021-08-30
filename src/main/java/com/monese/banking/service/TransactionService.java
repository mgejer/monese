package com.monese.banking.service;

import com.monese.banking.dao.TransactionRepository;
import com.monese.banking.model.Status;
import com.monese.banking.model.Transaction;

import java.time.LocalDateTime;

public class TransactionService {

    private TransactionRepository repository;

    public Transaction createSuccessfulTransaction (double amount, long from, long to) {
        return repository.save(new Transaction(amount, LocalDateTime.now(), from, to, Status.SUCCESSFUL));
    }
}
