package com.monese.banking.exceptions;

public class DestinationNotFoundException extends NotFoundException {
    public DestinationNotFoundException() {
        super("Destination account not found");
    }
}
