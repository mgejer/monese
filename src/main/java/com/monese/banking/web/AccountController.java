package com.monese.banking.web;

import com.monese.banking.model.Account;
import com.monese.banking.model.Transaction;
import com.monese.banking.service.AccountService;
import com.monese.banking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AccountMapper accountMapper;

    @Value("${db.url}")
    private String dbconnection;

    @GetMapping("/{id}")
    public ResponseEntity<AccountAPI> getAccount(@PathVariable Long id) {

        //TODO obtains amount of days from request param
        return new ResponseEntity(accountMapper.map(accountService.retrieve(id), transactionService.findByAccount(id, Optional.empty())), HttpStatus.OK);
    }

    @PostMapping("/transaction")
    public ResponseEntity<Transaction> postTransaction(@RequestParam long originId, @RequestParam long destinationId, @RequestParam double amount) {
        return new ResponseEntity(accountService.transfer(originId, destinationId, amount), HttpStatus.ACCEPTED);
    }

    @GetMapping("/transaction/{id}")
    public ResponseEntity<Transaction> getTransactionStatus(@PathVariable String id) {
        return new ResponseEntity(accountService.retrieveTransaction(), HttpStatus.ACCEPTED);
    }
}
