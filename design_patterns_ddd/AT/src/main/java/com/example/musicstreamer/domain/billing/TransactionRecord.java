package com.example.musicstreamer.domain.billing;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "transaction_records")
public class TransactionRecord {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID accountId;

    @Column(nullable = false)
    private String merchant;

    @Embedded
    private Money money;

    @Column(nullable = false)
    private OffsetDateTime occurredAt;

    @Column(nullable = false)
    private boolean approved;

    protected TransactionRecord() {
    }

    private TransactionRecord(UUID id, UUID accountId, String merchant, Money money, OffsetDateTime occurredAt, boolean approved) {
        this.id = id;
        this.accountId = accountId;
        this.merchant = merchant;
        this.money = money;
        this.occurredAt = occurredAt;
        this.approved = approved;
    }

    public static TransactionRecord create(UUID accountId, String merchant, Money money, OffsetDateTime occurredAt, boolean approved) {
        return new TransactionRecord(UUID.randomUUID(), accountId, merchant, money, occurredAt, approved);
    }

    public UUID id() {
        return id;
    }

    public UUID accountId() {
        return accountId;
    }

    public String merchant() {
        return merchant;
    }

    public Money money() {
        return money;
    }

    public OffsetDateTime occurredAt() {
        return occurredAt;
    }

    public boolean approved() {
        return approved;
    }
}
