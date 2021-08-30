package com.monese.banking.web.mapper;

import com.monese.banking.model.Account;
import com.monese.banking.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class AccountMapper {
    @Autowired
    private TransactionMapper transactionMapper;

    public AccountAPI map(Account account, Collection<Transaction> transactions) {

        List<TransactionAPI> mappedTransactions = transactions.stream()
                .map((Transaction transaction) -> TransactionMapper.map(account.getId(), transaction))
                .collect(toList());

        return new AccountAPI(account.getId(), account.getBalance(), mappedTransactions);
    }
}
