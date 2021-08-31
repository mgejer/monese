package com.monese.banking.web.mapper;

import com.monese.banking.model.Account;
import com.monese.banking.model.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collection;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AccountMapperTest {

    private static final long ACCOUNT_ID = 100;
    private static final double BALANCE = 300d;
    @InjectMocks
    private AccountMapper mapper;
    @Mock
    private TransactionMapper transactionMapper;
    @Mock
    private Account account;
    @Mock
    private Transaction transaction1, transaction2;
    @Mock
    private TransactionAPI transactionAPI1, transactionAPI2;

    @Test
    void map() {
        when(account.getId()).thenReturn(ACCOUNT_ID);
        when(account.getBalance()).thenReturn(BALANCE);
        when(transactionMapper.map(ACCOUNT_ID, transaction1)).thenReturn(transactionAPI1);
        when(transactionMapper.map(ACCOUNT_ID, transaction2)).thenReturn(transactionAPI2);
        Collection<Transaction> transactions = asList(transaction1, transaction2);
        AccountAPI accountAPI = mapper.map(account, transactions);
        assertEquals(ACCOUNT_ID, accountAPI.getId());
        assertEquals(BALANCE, accountAPI.getBalance());
        assertEquals(asList(transactionAPI1, transactionAPI2), accountAPI.getTransactions());
    }
}