package com.monese.banking.web;

import com.monese.banking.service.AccountService;
import com.monese.banking.service.TransactionService;
import com.monese.banking.web.mapper.AccountAPI;
import com.monese.banking.web.mapper.AccountMapper;
import com.monese.banking.web.mapper.TransactionAPI;
import com.monese.banking.web.mapper.TransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/accounts")
public class AccountController {

    public static final String TRANSACTION_SUCCESSFUL = "Transaction successful";

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
    public String postTransaction(@RequestParam long origin, @RequestParam long destination, @RequestParam double amount) {
        accountService.transfer(origin, destination, amount);
        return TRANSACTION_SUCCESSFUL;
    }
}
