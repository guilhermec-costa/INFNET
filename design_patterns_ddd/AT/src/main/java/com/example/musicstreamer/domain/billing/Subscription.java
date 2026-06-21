package com.example.musicstreamer.domain.billing;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID accountId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlanType planType;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private OffsetDateTime activatedAt;

    protected Subscription() {
    }

    private Subscription(UUID id, UUID accountId, PlanType planType, OffsetDateTime activatedAt) {
        this.id = id;
        this.accountId = accountId;
        this.planType = planType;
        this.active = true;
        this.activatedAt = activatedAt;
    }

    public static Subscription activate(UUID accountId, PlanType planType, OffsetDateTime activatedAt) {
        return new Subscription(UUID.randomUUID(), accountId, planType, activatedAt);
    }

    public UUID id() {
        return id;
    }

    public UUID accountId() {
        return accountId;
    }

    public PlanType planType() {
        return planType;
    }

    public boolean isActive() {
        return active;
    }

    public OffsetDateTime activatedAt() {
        return activatedAt;
    }
}
