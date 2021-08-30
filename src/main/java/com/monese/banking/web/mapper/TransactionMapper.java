package com.monese.banking.web.mapper;

import com.monese.banking.model.Transaction;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
class TransactionMapper {
    static TransactionAPI map(long accountId, Transaction transaction) {

        TransactionType type = transaction.getOrigin() == accountId ? TransactionType.OUTBOUND : TransactionType.INBOUND;
        long otherAccount = transaction.getOrigin() == accountId ? transaction.getDestination() : transaction.getOrigin();
        double amount = transaction.getOrigin() == accountId ? -transaction.getAmount() : transaction.getAmount();

        return new TransactionAPI(transaction.getId(), type, transaction.getDate().format(DateTimeFormatter.ISO_INSTANT), otherAccount, amount, transaction.getStatus().name());
    }
}
