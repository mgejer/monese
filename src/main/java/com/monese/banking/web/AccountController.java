package com.monese.banking.web;

import com.monese.banking.service.AccountService;
import com.monese.banking.model.Account;
import com.monese.banking.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/{id}")
    public ResponseEntity<Account> getStatus(@PathVariable String id) {
        return new ResponseEntity(accountService.retrieve(id), HttpStatus.OK);
    }

    @PostMapping("/transaction")
    public ResponseEntity<Transaction> postTransaction() {
        return new ResponseEntity(accountService.transfer(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/transaction/{id}")
    public ResponseEntity<Transaction> getTransactionStatus(@PathVariable String id) {
        return new ResponseEntity(accountService.retrieveTransaction(), HttpStatus.ACCEPTED);
    }
}
