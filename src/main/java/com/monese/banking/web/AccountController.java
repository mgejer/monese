package com.monese.banking.web;

import com.monese.banking.exceptions.OriginNotFoundException;
import com.monese.banking.model.Transaction;
import com.monese.banking.service.AccountService;
import com.monese.banking.service.TransactionService;
import com.monese.banking.web.mapper.AccountAPI;
import com.monese.banking.web.mapper.AccountMapper;
import com.monese.banking.web.mapper.TransactionAPI;
import com.monese.banking.web.mapper.TransactionMapper;
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
    @Autowired
    private TransactionMapper transactionMapper;

    @GetMapping("/{id}")
    public ResponseEntity<AccountAPI> getAccount(@PathVariable Long id, @RequestParam(required = false) Integer daysInPast) {
        return new ResponseEntity(accountMapper.map(accountService.retrieve(id), transactionService.findByAccount(id, Optional.ofNullable(daysInPast))), HttpStatus.OK);
    }

    @PostMapping("/transaction")
    public ResponseEntity<TransactionAPI> postTransaction(@RequestParam long origin, @RequestParam long destination, @RequestParam double amount) {
        return new ResponseEntity(transactionMapper.map(origin, accountService.transfer(origin, destination, amount)), HttpStatus.OK);
    }
}
