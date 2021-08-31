package com.monese.banking.service;

import com.monese.banking.dao.AccountRepository;
import com.monese.banking.exceptions.AccountNotFoundException;
import com.monese.banking.exceptions.DestinationNotFoundException;
import com.monese.banking.exceptions.OriginNotFoundException;
import com.monese.banking.exceptions.InsufficientFoundsException;
import com.monese.banking.model.Account;
import com.monese.banking.model.Transaction;
import com.monese.banking.web.AccountController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class AccountService {

    private Logger logger = LoggerFactory.getLogger(AccountService.class);

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
            logger.error("Insufficient funds to transfer {} from account {}", amount, from);
            throw new InsufficientFoundsException();
        }
        origin.addToBalance(-amount);
        destination.addToBalance(amount);
        repository.save(origin);
        repository.save(destination);

        return transactionService.createSuccessfulTransaction(amount, from, to);
    }

    public Account create(Double balance) {
        return repository.save(new Account(balance));
    }
}
