package com.example.musicstreamer.infrastructure.persistence.billing;

import com.example.musicstreamer.domain.billing.Subscription;
import com.example.musicstreamer.domain.billing.SubscriptionRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class SubscriptionRepositoryAdapter implements SubscriptionRepository {

    private final SpringDataSubscriptionRepository repository;

    public SubscriptionRepositoryAdapter(SpringDataSubscriptionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Subscription save(Subscription subscription) {
        return repository.save(subscription);
    }

    @Override
    public Optional<Subscription> findActiveByAccountId(UUID accountId) {
        return repository.findByAccountIdAndActiveTrue(accountId);
    }
}
