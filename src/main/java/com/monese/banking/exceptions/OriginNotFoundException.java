package com.monese.banking.exceptions;

public class OriginNotFoundException extends NotFoundException {
    public OriginNotFoundException() {
        super("Origin account not found");
    }
}
