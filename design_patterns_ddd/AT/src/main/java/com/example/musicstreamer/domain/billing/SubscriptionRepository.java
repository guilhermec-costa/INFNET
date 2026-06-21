package com.example.musicstreamer.domain.billing;

import java.util.Optional;
import java.util.UUID;

public interface SubscriptionRepository {

    Subscription save(Subscription subscription);

    Optional<Subscription> findActiveByAccountId(UUID accountId);
}
