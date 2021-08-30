package com.monese.banking.dao;

import com.monese.banking.model.Account;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository repository;

    @Test
    void findById() {
        double balance = 156d;
        Account account = repository.save(new Account(balance));
        assertNotNull(account.getId());
        Optional<Account> byId = repository.findById(account.getId());
        assertThat(byId).isPresent();
        assertThat(byId.get().getBalance()).isEqualTo(balance);
    }
}