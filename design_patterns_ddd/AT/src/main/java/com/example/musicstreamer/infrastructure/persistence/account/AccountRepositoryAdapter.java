package com.example.musicstreamer.infrastructure.persistence.account;

import com.example.musicstreamer.domain.account.Account;
import com.example.musicstreamer.domain.account.AccountRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class AccountRepositoryAdapter implements AccountRepository {

    private final SpringDataAccountRepository repository;

    public AccountRepositoryAdapter(SpringDataAccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public Account save(Account account) {
        return repository.save(account);
    }

    @Override
    public Optional<Account> findById(UUID id) {
        return repository.findById(id);
    }
}
