package com.example.musicstreamer.application.account;

import com.example.musicstreamer.domain.account.Account;

import java.time.LocalDate;
import java.util.UUID;

public interface CardManagementUseCase {

    Account registerCard(UUID accountId, String holderName, String cardNumber, boolean active, LocalDate expiresAt);
}
