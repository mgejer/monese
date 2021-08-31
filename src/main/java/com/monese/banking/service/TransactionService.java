package com.monese.banking.service;

import com.monese.banking.dao.TransactionRepository;
import com.monese.banking.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.util.Collection;
import java.util.Optional;

import static java.time.LocalDateTime.now;

@Component
public class TransactionService {

    @Autowired
    private TransactionRepository repository;

    @Value("${default.transactions.by.account.max.amount.days.search: 30}")
    private Integer defaultAmountOfDaysSearch;

    public Collection<Transaction> findByAccount(long accountId, Optional<Integer> amountOfDays) {
        return repository.findByAccountId(accountId, LocalDate.now().atStartOfDay().minusDays(amountOfDays.orElse(defaultAmountOfDaysSearch)));
    }

    Transaction createSuccessfulTransaction (double amount, long from, long to) {
        return repository.save(new Transaction(amount, now(), from, to));
    }
}
