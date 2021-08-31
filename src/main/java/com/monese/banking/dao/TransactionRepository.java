package com.monese.banking.dao;

import com.monese.banking.model.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    @Query(value = "FROM Transaction t WHERE (t.origin = :accountId or t.destination = :accountId) and t.date > :date order by t.date desc")
    Collection<Transaction> findByAccountId(long accountId, LocalDateTime date);
}
