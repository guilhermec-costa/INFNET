package com.example.musicstreamer.infrastructure.persistence.billing;

import com.example.musicstreamer.domain.billing.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataSubscriptionRepository extends JpaRepository<Subscription, UUID> {

    Optional<Subscription> findByAccountIdAndActiveTrue(UUID accountId);
}
