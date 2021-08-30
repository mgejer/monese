package com.monese.banking.service;

import com.monese.banking.dao.AccountRepository;
import com.monese.banking.exceptions.AccountNotFoundException;
import com.monese.banking.exceptions.DestinationNotFoundException;
import com.monese.banking.exceptions.OriginNotFoundException;
import com.monese.banking.exceptions.InsufficientFoundsException;
import com.monese.banking.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class AccountServiceTest {

    private static final long ORIGIN = 100;
    private static final long DESTINATION = 200;

    @InjectMocks
    private AccountService accountService;
    @Mock
    private AccountRepository repository;
    @Mock
    private TransactionService transactionService;
    @Mock
    private Account originAccount, destinationAccount;

    @BeforeEach
    void setup() {
        when(originAccount.getBalance()).thenReturn(200d);
        when(repository.lockedFindById(ORIGIN)).thenReturn(Optional.of(originAccount));
        when(repository.lockedFindById(DESTINATION)).thenReturn(Optional.of(destinationAccount));
    }

    @Test
    void transfer() {
        accountService.transfer(ORIGIN, DESTINATION, 100);
        verify(originAccount).addToBalance(-100);
        verify(destinationAccount).addToBalance(100);
        verify(repository).save(originAccount);
        verify(repository).save(destinationAccount);
        verify(transactionService).createSuccessfulTransaction(100, ORIGIN, DESTINATION);
    }

    @Test
    void transferFromNonExistentAccountThrowsException() {
        assertThrows(OriginNotFoundException.class, () -> accountService.transfer(300, DESTINATION, 100));
    }

    @Test
    void transferToNonExistentAccountThrowsException() {
        assertThrows(DestinationNotFoundException.class, () -> accountService.transfer(ORIGIN, 300, 100));
    }

    @Test
    void transferInsufficientAmountThrowsException() {
        assertThrows(InsufficientFoundsException.class, () -> accountService.transfer(ORIGIN, DESTINATION, 400));
    }

    @Test
    void testRetrieve() {
        Account account = mock(Account.class);
        when(repository.findById(100L)).thenReturn(Optional.of(account));
        assertEquals(account, accountService.retrieve(100));
    }

    @Test
    void testRetrieveNotFoundThrowsException() {
        when(repository.findById(100L)).thenReturn(Optional.empty());
        assertThrows(AccountNotFoundException.class, () -> accountService.retrieve(100));
    }
}