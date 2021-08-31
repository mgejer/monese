package com.monese.banking.exceptions;

public class AccountNotFoundException extends NotFoundException {
    public AccountNotFoundException() {
        super("Account not found");
    }
}
