package com.monese.banking.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monese.banking.exceptions.AccountNotFoundException;
import com.monese.banking.exceptions.DestinationNotFoundException;
import com.monese.banking.exceptions.InsufficientFoundsException;
import com.monese.banking.exceptions.OriginNotFoundException;
import com.monese.banking.model.Account;
import com.monese.banking.model.Transaction;
import com.monese.banking.service.AccountService;
import com.monese.banking.service.TransactionService;
import com.monese.banking.web.mapper.*;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AccountService service;
    @MockBean
    private TransactionService transactionService;
    @MockBean
    private AccountMapper accountMapper;
    @MockBean
    private TransactionMapper transactionMapper;
    @Mock
    private Account account;
    private AccountAPI accountAPI;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        TransactionAPI transaction1 = new TransactionAPI(1, TransactionType.INBOUND, LocalDateTime.now().minusDays(4).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), 105, 300);
        TransactionAPI transaction2 = new TransactionAPI(2, TransactionType.OUTBOUND, LocalDateTime.now().minusDays(3).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), 105, 300);
        List<TransactionAPI> transactions = Arrays.asList(transaction1, transaction2);
        accountAPI = new AccountAPI(100L, 300d, transactions);
    }

    @Test
    void testGetAccount() throws Exception {
        when(service.retrieve(100)).thenReturn(account);
        when(transactionService.findByAccount(100, Optional.empty())).thenReturn(Arrays.asList());
        when(accountMapper.map(eq(account), anyCollection())).thenReturn(accountAPI);
        MvcResult mvcResult = mockMvc.perform(get("/accounts/100")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(objectMapper.writeValueAsString(accountAPI), mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testGetNonExistentAccount() throws Exception {
        when(service.retrieve(100)).thenThrow(new AccountNotFoundException());
        mockMvc.perform(get("/accounts/100")
                .contentType("application/json"))
                .andExpect(status().is(404))
                .andExpect(status().reason("Account not found"))
                .andReturn();
    }

    @Test
    void testTransfer() throws Exception {
        Transaction transaction = mock(Transaction.class);
        TransactionAPI transactionAPI = new TransactionAPI(1, TransactionType.OUTBOUND, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), 2, 300);
        when(transactionMapper.map(1, transaction)).thenReturn(transactionAPI);
        when(service.transfer(1, 2, 300)).thenReturn(transaction);

        MvcResult mvcResult = mockMvc.perform(post("/accounts/transaction?origin=1&destination=2&amount=300").contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(objectMapper.writeValueAsString(transactionAPI), mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testTransferOriginNotFoundReturnsError() throws Exception {
        when(service.transfer(1, 2, 300)).thenThrow(new OriginNotFoundException());

        mockMvc.perform(post("/accounts/transaction?origin=1&destination=2&amount=300").contentType("application/json"))
                .andExpect(status().is(400))
                .andExpect(status().reason("Origin account not found"))
                .andReturn();
    }

    @Test
    void testTransferDestinationNotFoundReturnsError() throws Exception {
        when(service.transfer(1, 2, 300)).thenThrow(new DestinationNotFoundException());

        mockMvc.perform(post("/accounts/transaction?origin=1&destination=2&amount=300").contentType("application/json"))
                .andExpect(status().is(400))
                .andExpect(status().reason("Destination account not found"))
                .andReturn();
    }

    @Test
    void testTransferInsufficientFoundsReturnsError() throws Exception {
        when(service.transfer(1, 2, 300)).thenThrow(new InsufficientFoundsException());

        mockMvc.perform(post("/accounts/transaction?origin=1&destination=2&amount=300").contentType("application/json"))
                .andExpect(status().is(400))
                .andExpect(status().reason("Origin account does not have enough funds"))
                .andReturn();
    }
}
