package com.monese.banking.dao;

import com.monese.banking.model.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository repository;

    @Test
    void findByAccountId() {
        Transaction transactionWithAccountAsOrigin = repository.save(new Transaction(100d, LocalDateTime.now(), 100, 200));
        Transaction transactionWithAccountAsDestination = repository.save(new Transaction(100d, LocalDateTime.now(), 300, 100));
        repository.save(new Transaction(100d, LocalDateTime.now(), 300, 200));
        repository.save(new Transaction(100d, LocalDateTime.now(), 200, 300));

        assertEquals(Arrays.asList(transactionWithAccountAsDestination, transactionWithAccountAsOrigin), repository.findByAccountId(100, LocalDateTime.now().minusDays(1)));
    }

    @Test
    void findByAccountIdAfterDate() {
        LocalDateTime now = LocalDateTime.now();

        repository.save(new Transaction(100d, now.minusDays(5), 100, 200));
        repository.save(new Transaction(100d, now.minusDays(4), 300, 100));
        repository.save(new Transaction(100d, now.minusDays(3), 100, 200));
        Transaction twoDaysAgoTransaction = repository.save(new Transaction(100d, now.minusDays(2), 300, 100));
        Transaction yesterdayTransaction = repository.save(new Transaction(100d, now.minusDays(1), 300, 100));
        Transaction todayTransaction = repository.save(new Transaction(100d, now, 300, 100));

        Collection<Transaction> byAccountId = repository.findByAccountId(100, now.withHour(0).minusDays(2));
        assertEquals(Arrays.asList(todayTransaction, yesterdayTransaction, twoDaysAgoTransaction), byAccountId);
    }

}