package com.monese.banking.dao;

import com.monese.banking.model.Status;
import com.monese.banking.model.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository repository;

    @Test
    void findByAccountId() {
        Transaction transactionWithAccountAsOrigin = repository.save(new Transaction(100d, LocalDateTime.now(), 100, 200, Status.SUCCESSFUL));
        Transaction transactionWithAccountAsDestination = repository.save(new Transaction(100d, LocalDateTime.now(), 300, 100, Status.SUCCESSFUL));
        repository.save(new Transaction(100d, LocalDateTime.now(), 300, 200, Status.SUCCESSFUL));
        repository.save(new Transaction(100d, LocalDateTime.now(), 200, 300, Status.SUCCESSFUL));

        assertEquals(Arrays.asList(transactionWithAccountAsDestination, transactionWithAccountAsOrigin), repository.findByAccountId(100, LocalDateTime.now().minusDays(1)));
    }

    @Test
    void findByAccountIdAfterDate() {
        LocalDateTime now = LocalDateTime.now();

        repository.save(new Transaction(100d, now.minusDays(5), 100, 200, Status.SUCCESSFUL));
        repository.save(new Transaction(100d, now.minusDays(4), 300, 100, Status.SUCCESSFUL));
        repository.save(new Transaction(100d, now.minusDays(3), 100, 200, Status.SUCCESSFUL));
        Transaction twoDaysAgoTransaction = repository.save(new Transaction(100d, now.minusDays(2), 300, 100, Status.SUCCESSFUL));
        Transaction yesterdayTransaction = repository.save(new Transaction(100d, now.minusDays(1), 300, 100, Status.SUCCESSFUL));
        Transaction todayTransaction = repository.save(new Transaction(100d, now, 300, 100, Status.SUCCESSFUL));

        Collection<Transaction> byAccountId = repository.findByAccountId(100, now.withHour(0).minusDays(2));
        assertEquals(Arrays.asList(todayTransaction, yesterdayTransaction, twoDaysAgoTransaction), byAccountId);
    }

}