package com.monese.banking.service;

import com.monese.banking.dao.AccountRepository;
import com.monese.banking.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class AccountServiceTest {

    public static final long ORIGIN = 100;
    public static final long DESTINATION = 200;

    @InjectMocks
    private AccountService accountService;
    @Mock
    private AccountRepository repository;
    @Mock
    private Account originAccount, destinationAccount;

    @BeforeEach
    public void setup() {
        when(originAccount.getBalance()).thenReturn(200d);
        when(repository.lockedFindById(ORIGIN)).thenReturn(Optional.of(originAccount));
        when(repository.lockedFindById(DESTINATION)).thenReturn(Optional.of(destinationAccount));
    }

    @Test
    public void transfer() {
        accountService.transfer(ORIGIN, DESTINATION, 100);
        verify(originAccount).addToBalance(-100);
        verify(destinationAccount).addToBalance(100);
        verify(repository).save(originAccount);
        verify(repository).save(destinationAccount);
    }

    @Test
    public void transferFromNonExistentAccountThrowsException() {
        assertThrows(RuntimeException.class, ()-> accountService.transfer(300, DESTINATION, 100));
    }

    @Test
    public void transferToNonExistentAccountThrowsException() {
        assertThrows(RuntimeException.class, ()-> accountService.transfer(ORIGIN, 300, 100));
    }

    @Test
    public void transferInsufficientAmountThrowsException() {
        assertThrows(RuntimeException.class, ()-> accountService.transfer(ORIGIN, DESTINATION, 400));
    }
}