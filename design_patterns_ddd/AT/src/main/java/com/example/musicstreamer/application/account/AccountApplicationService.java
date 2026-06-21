package com.example.musicstreamer.application.account;

import com.example.musicstreamer.api.shared.NotFoundException;
import com.example.musicstreamer.domain.account.Account;
import com.example.musicstreamer.domain.account.AccountRepository;
import com.example.musicstreamer.domain.account.CreditCard;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
@Transactional
public class AccountApplicationService implements AccountRegistrationUseCase, CardManagementUseCase {

    private final AccountRepository accountRepository;

    public AccountApplicationService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account registerAccount(String ownerName) {
        Account account = Account.create(ownerName);
        return accountRepository.save(account);
    }

    @Override
    public Account registerCard(UUID accountId, String holderName, String cardNumber, boolean active, LocalDate expiresAt) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Conta não encontrada."));

        String sanitizedCardNumber = cardNumber.replaceAll("\\s+", "");
        String lastFourDigits = sanitizedCardNumber.substring(sanitizedCardNumber.length() - 4);
        account.registerCard(new CreditCard(holderName, lastFourDigits, active, expiresAt));
        return accountRepository.save(account);
    }
}
