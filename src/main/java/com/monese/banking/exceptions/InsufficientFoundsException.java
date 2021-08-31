package com.monese.banking.exceptions;

public class InsufficientFoundsException extends BadRequestException {

    public InsufficientFoundsException() {
        super("Origin account does not have enough funds");
    }
}
