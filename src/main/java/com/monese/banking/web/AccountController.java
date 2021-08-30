package com.monese.banking.web;

import com.monese.banking.exceptions.OriginNotFoundException;
import com.monese.banking.model.Transaction;
import com.monese.banking.service.AccountService;
import com.monese.banking.service.TransactionService;
import com.monese.banking.web.mapper.AccountAPI;
import com.monese.banking.web.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

//TODO unit test of api
//TODO integration test of application

@RestController
@RequestMapping(path = "/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AccountMapper accountMapper;

    @GetMapping("/{id}")
    public ResponseEntity<AccountAPI> getAccount(@PathVariable Long id, @RequestParam(required = false) Integer daysInPast) {
        return new ResponseEntity(accountMapper.map(accountService.retrieve(id), transactionService.findByAccount(id, Optional.ofNullable(daysInPast))), HttpStatus.OK);
    }

    @PostMapping("/transaction")
    public ResponseEntity<Transaction> postTransaction(@RequestParam long originId, @RequestParam long destinationId, @RequestParam double amount) {
        return new ResponseEntity(accountService.transfer(originId, destinationId, amount), HttpStatus.ACCEPTED);
    }
}
