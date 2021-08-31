package com.monese.banking.web;

import com.monese.banking.service.AccountService;
import com.monese.banking.service.TransactionService;
import com.monese.banking.web.mapper.AccountAPI;
import com.monese.banking.web.mapper.AccountMapper;
import com.monese.banking.web.mapper.TransactionAPI;
import com.monese.banking.web.mapper.TransactionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping(path = "/accounts")
public class AccountController {

    public static final String TRANSACTION_SUCCESSFUL = "Transaction successful";

    private Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private TransactionMapper transactionMapper;

    @GetMapping("/{id}")
    public ResponseEntity<AccountAPI> getAccount(@PathVariable Long id, @RequestParam(required = false) Integer daysInPast) {
        logger.debug("Received request to retrieve account {} with transactions up to {} days", id, daysInPast);
        return new ResponseEntity(accountMapper.map(accountService.retrieve(id), transactionService.findByAccount(id, Optional.ofNullable(daysInPast))), HttpStatus.OK);
    }

    @PostMapping("/transaction")
    public String postTransaction(@RequestParam long origin, @RequestParam long destination, @RequestParam double amount) {
        logger.debug("Received request to transfer from account " + origin + " to destination " + destination + ": " + amount);
        accountService.transfer(origin, destination, amount);
        return TRANSACTION_SUCCESSFUL;
    }

    @PostMapping("")
    public ResponseEntity<AccountAPI> getAccount(@RequestParam Double balance) {
        return new ResponseEntity(accountMapper.map(accountService.create(balance), Collections.emptyList()), HttpStatus.OK);
    }
}
