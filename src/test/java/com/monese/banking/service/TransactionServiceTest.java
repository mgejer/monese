package com.monese.banking.service;

import com.monese.banking.dao.TransactionRepository;
import com.monese.banking.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.*;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class TransactionServiceTest {

    private static final int MINUS_DAYS = 60;
    private static final int ACCOUNT_ID = 100;
    @InjectMocks
    private TransactionService service;

    @Mock
    private TransactionRepository repository;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(service, "defaultAmountOfDaysSearch", 60);
    }

    @Test
    void findByAccount() {
        Transaction transaction = mock(Transaction.class);
        List<Transaction> transactions = singletonList(transaction);
        when(repository.findByAccountId(ACCOUNT_ID, LocalDate.now().atStartOfDay().minusDays(MINUS_DAYS))).thenReturn(transactions);
        assertEquals(transactions, service.findByAccount(ACCOUNT_ID, Optional.empty()));
    }

    @Test
    void findByAccountUseGivenAmountOfDays() {
        Transaction transaction = mock(Transaction.class);
        List<Transaction> transactions = singletonList(transaction);
        int days = 50;
        when(repository.findByAccountId(ACCOUNT_ID, LocalDate.now().atStartOfDay().minusDays(days))).thenReturn(transactions);
        assertEquals(transactions, service.findByAccount(ACCOUNT_ID, Optional.of(50)));
    }
}