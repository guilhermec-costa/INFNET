package com.example.musicstreamer.application.account;

import com.example.musicstreamer.domain.account.Account;

public interface AccountRegistrationUseCase {

    Account registerAccount(String ownerName);
}
