package com.monese.banking.web.mapper;

import com.monese.banking.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class TransactionMapperTest {

    private static final long ID = 4L;
    private static final long ORIGIN = 10;
    private static final long DESTINATION = 20L;
    private static final double AMOUNT = 100d;
    private static final LocalDateTime DATE = LocalDateTime.now();

    @InjectMocks
    private TransactionMapper mapper;
    @Mock
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        when(transaction.getOrigin()).thenReturn(ORIGIN);
        when(transaction.getDestination()).thenReturn(DESTINATION);
        when(transaction.getDate()).thenReturn(DATE);
        when(transaction.getId()).thenReturn(ID);
        when(transaction.getAmount()).thenReturn(AMOUNT);
    }

    @Test
    void mapOutboundTransaction() {
        TransactionAPI transactionAPI = mapper.map(ORIGIN, transaction);
        assertEquals(DESTINATION, transactionAPI.getAccount());
        assertEquals(-AMOUNT, transactionAPI.getAmount());
        assertEquals(TransactionType.OUTBOUND, transactionAPI.getType());
        assertEquals(DATE.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), transactionAPI.getDate());
    }

    @Test
    void mapInboundTransaction() {
        TransactionAPI transactionAPI = mapper.map(DESTINATION, transaction);
        assertEquals(ORIGIN, transactionAPI.getAccount());
        assertEquals(AMOUNT, transactionAPI.getAmount());
        assertEquals(TransactionType.INBOUND, transactionAPI.getType());
        assertEquals(DATE.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), transactionAPI.getDate());
    }
}