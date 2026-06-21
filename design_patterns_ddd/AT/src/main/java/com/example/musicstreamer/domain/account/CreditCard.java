package com.example.musicstreamer.domain.account;

import jakarta.persistence.Embeddable;

import java.time.LocalDate;

@Embeddable
public class CreditCard {

    private String holderName;
    private String lastFourDigits;
    private boolean active;
    private LocalDate expiresAt;

    protected CreditCard() {
    }

    public CreditCard(String holderName, String lastFourDigits, boolean active, LocalDate expiresAt) {
        this.holderName = holderName;
        this.lastFourDigits = lastFourDigits;
        this.active = active;
        this.expiresAt = expiresAt;
    }

    public String holderName() {
        return holderName;
    }

    public String lastFourDigits() {
        return lastFourDigits;
    }

    public boolean isActive() {
        return active;
    }

    public LocalDate expiresAt() {
        return expiresAt;
    }

    public boolean isValidAt(LocalDate referenceDate) {
        return expiresAt != null && !expiresAt.isBefore(referenceDate);
    }
}
