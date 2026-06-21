package com.example.musicstreamer.domain.account;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String ownerName;

    @Embedded
    private CreditCard creditCard;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "account_favorite_tracks", joinColumns = @JoinColumn(name = "account_id"))
    @Column(name = "track_id", nullable = false)
    private Set<UUID> favoriteTrackIds = new HashSet<>();

    protected Account() {
    }

    private Account(UUID id, String ownerName) {
        this.id = id;
        this.ownerName = ownerName;
    }

    public static Account create(String ownerName) {
        return new Account(UUID.randomUUID(), ownerName);
    }

    public UUID id() {
        return id;
    }

    public String ownerName() {
        return ownerName;
    }

    public CreditCard creditCard() {
        return creditCard;
    }

    public Set<UUID> favoriteTrackIds() {
        return Set.copyOf(favoriteTrackIds);
    }

    public void registerCard(CreditCard card) {
        this.creditCard = card;
    }

    public boolean hasValidCard(LocalDate referenceDate) {
        return creditCard != null && creditCard.isValidAt(referenceDate);
    }

    public boolean hasActiveCard(LocalDate referenceDate) {
        return creditCard != null && creditCard.isActive() && creditCard.isValidAt(referenceDate);
    }

    public void addFavoriteTrack(UUID trackId) {
        favoriteTrackIds.add(trackId);
    }
}
