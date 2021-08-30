package com.monese.banking.service;

import com.monese.banking.dao.AccountRepository;
import com.monese.banking.model.Account;
import com.monese.banking.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class AccountService {

    @Autowired
    private AccountRepository repository;

    public Account retrieve(long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException());
    }

    @Transactional
    public Transaction transfer(long from, long to, double amount) {
        Account origin = repository.lockedFindById(from).orElseThrow(() -> new RuntimeException());
        Account destination = repository.lockedFindById(to).orElseThrow(() -> new RuntimeException());

        if (origin.getBalance() < amount) {
            throw new RuntimeException();
        }
        origin.addToBalance(-amount);
        destination.addToBalance(amount);
        repository.save(origin);
        repository.save(destination);

        return null;
    }

    public Transaction retrieveTransaction() {
        return null;
    }
}
