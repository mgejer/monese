package com.monese.banking.service;

import com.monese.banking.dao.AccountRepository;
import com.monese.banking.exceptions.AccountNotFoundException;
import com.monese.banking.exceptions.DestinationNotFoundException;
import com.monese.banking.exceptions.OriginNotFoundException;
import com.monese.banking.exceptions.InsufficientFoundsException;
import com.monese.banking.model.Account;
import com.monese.banking.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class AccountService {

    @Autowired
    private AccountRepository repository;

    @Autowired
    private TransactionService transactionService;

    public Account retrieve(long id) {
        return repository.findById(id).orElseThrow(AccountNotFoundException::new);
    }

    @Transactional
    public Transaction transfer(long from, long to, double amount) {
        Account origin = repository.lockedFindById(from).orElseThrow(OriginNotFoundException::new);
        Account destination = repository.lockedFindById(to).orElseThrow(DestinationNotFoundException::new);

        if (origin.getBalance() < amount) {
            throw new InsufficientFoundsException();
        }
        origin.addToBalance(-amount);
        destination.addToBalance(amount);
        repository.save(origin);
        repository.save(destination);

        return transactionService.createSuccessfulTransaction(amount, from, to);
    }
}
