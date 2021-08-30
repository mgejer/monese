package com.monese.banking.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Origin account not found")
public class OriginNotFoundException extends RuntimeException {
}
