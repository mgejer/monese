package com.monese.banking.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Origin account does not have enough funds")
public class InsufficientFoundsException extends RuntimeException {
}
