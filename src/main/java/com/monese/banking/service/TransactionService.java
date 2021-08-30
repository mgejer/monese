package com.monese.banking.service;

import com.monese.banking.dao.TransactionRepository;
import com.monese.banking.model.Status;
import com.monese.banking.model.Transaction;

import java.util.Date;

public class TransactionService {

    private TransactionRepository repository;

    public Transaction createSuccessfulTransaction (double amount, long from, long to) {
        return repository.save(new Transaction(amount, new Date(), from, to, Status.SUCCESSFUL));
    }
}
