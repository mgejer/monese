package com.monese.banking.model;

public class Status {

    private final StatusType type;
    private final String description;

    public Status(StatusType type, String description) {
        this.type = type;
        this.description = description;
    }

    public StatusType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}
