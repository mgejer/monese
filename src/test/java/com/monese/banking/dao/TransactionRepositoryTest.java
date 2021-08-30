package com.monese.banking.dao;

import com.monese.banking.model.Status;
import com.monese.banking.model.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository repository;

    @Test
    void findByAccountId() {
        Transaction transactionWithAccountAsOrigin = repository.save(new Transaction(100d, new Date(), 100, 200, Status.SUCCESSFUL));
        Transaction transactionWithAccountAsDestination = repository.save(new Transaction(100d, new Date(), 300, 100, Status.SUCCESSFUL));
        repository.save(new Transaction(100d, new Date(), 300, 200, Status.SUCCESSFUL));
        repository.save(new Transaction(100d, new Date(), 200, 300, Status.SUCCESSFUL));

        assertEquals(Arrays.asList(transactionWithAccountAsOrigin, transactionWithAccountAsDestination), repository.findByAccountId(100));
    }
}