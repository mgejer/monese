package com.monese.banking;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.monese.banking.dao.AccountRepository;
import com.monese.banking.model.Account;
import com.monese.banking.service.AccountService;
import com.monese.banking.web.mapper.AccountAPI;
import com.monese.banking.web.mapper.TransactionAPI;
import com.monese.banking.web.mapper.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.monese.banking.web.AccountController.TRANSACTION_SUCCESSFUL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@EnableJpaRepositories
class IntegrationTest {

    private static final double FIRST_TRANSACTION_AMOUNT = 100d;
    private static Account origin;
    private static Account destination1;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AccountService accountService;

    @BeforeEach
    void setupDB() {
        origin = new Account(500d);
        accountRepository.save(origin);

        destination1 = new Account(100d);
        accountRepository.save(destination1);

        accountService.transfer(origin.getId(), destination1.getId(), 100d);

        origin = accountService.retrieve(origin.getId());
        destination1 = accountService.retrieve(destination1.getId());
    }

    @Test
    void testFindOriginAccount() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/accounts/" + origin.getId())
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();
        AccountAPI accountAPI = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), AccountAPI.class);
        assertEquals(origin.getId(), accountAPI.getId());
        assertEquals(origin.getBalance(), accountAPI.getBalance());

        assertEquals(1, accountAPI.getTransactions().size());
        assertEquals(TransactionType.OUTBOUND, accountAPI.getTransactions().get(0).getType());
        assertEquals(destination1.getId(), accountAPI.getTransactions().get(0).getAccount());
        assertEquals(-FIRST_TRANSACTION_AMOUNT, accountAPI.getTransactions().get(0).getAmount());
    }

    @Test
    void testFindDestinationAccount() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/accounts/" + destination1.getId())
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();
        AccountAPI accountAPI = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), AccountAPI.class);
        assertEquals(destination1.getId(), accountAPI.getId());
        assertEquals(destination1.getBalance(), accountAPI.getBalance());

        assertEquals(1, accountAPI.getTransactions().size());
        assertEquals(TransactionType.INBOUND, accountAPI.getTransactions().get(0).getType());
        assertEquals(origin.getId(), accountAPI.getTransactions().get(0).getAccount());
        assertEquals(FIRST_TRANSACTION_AMOUNT, accountAPI.getTransactions().get(0).getAmount());
    }

    @Test
    void testSuccessfulTransfer() throws Exception {
        double transactionAmount = 200d;
        mockMvc.perform(post("/accounts/transaction?origin=" + origin.getId() + "&destination=" + destination1.getId() + "&amount=" + transactionAmount)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string(TRANSACTION_SUCCESSFUL))
                .andReturn();

        MvcResult mvcResult = mockMvc.perform(get("/accounts/" + destination1.getId())
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();
        AccountAPI accountAPI = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), AccountAPI.class);
        assertEquals(destination1.getId(), accountAPI.getId());
        assertEquals(destination1.getBalance() + 200d, accountAPI.getBalance());

        assertEquals(2, accountAPI.getTransactions().size());
        assertEquals(TransactionType.INBOUND, accountAPI.getTransactions().get(1).getType());
        assertEquals(origin.getId(), accountAPI.getTransactions().get(1).getAccount());
        assertEquals(FIRST_TRANSACTION_AMOUNT, accountAPI.getTransactions().get(1).getAmount());

        assertEquals(TransactionType.INBOUND, accountAPI.getTransactions().get(0).getType());
        assertEquals(origin.getId(), accountAPI.getTransactions().get(0).getAccount());
        assertEquals(transactionAmount, accountAPI.getTransactions().get(0).getAmount());
    }

    @Test
    void testNotEnoughFoundsTransfer() throws Exception {
        double transactionAmount = 1200d;
        mockMvc.perform(post("/accounts/transaction?origin=" + origin.getId() + "&destination=" + destination1.getId() + "&amount=" + transactionAmount)
                .contentType("application/json"))
                .andExpect(status().is(400))
                .andExpect(content().string("Origin account does not have enough funds"))
                .andReturn();
    }

}
