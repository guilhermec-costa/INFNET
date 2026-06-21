package com.example.musicstreamer.api.account;

import com.example.musicstreamer.application.account.AccountRegistrationUseCase;
import com.example.musicstreamer.application.account.CardManagementUseCase;
import com.example.musicstreamer.domain.account.Account;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountRegistrationUseCase accountRegistrationUseCase;
    private final CardManagementUseCase cardManagementUseCase;

    public AccountController(AccountRegistrationUseCase accountRegistrationUseCase, CardManagementUseCase cardManagementUseCase) {
        this.accountRegistrationUseCase = accountRegistrationUseCase;
        this.cardManagementUseCase = cardManagementUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse createAccount(@Valid @RequestBody CreateAccountRequest request) {
        return AccountResponse.from(accountRegistrationUseCase.registerAccount(request.ownerName()));
    }

    @PostMapping("/{accountId}/cards")
    public AccountResponse registerCard(@PathVariable UUID accountId, @Valid @RequestBody RegisterCardRequest request) {
        Account account = cardManagementUseCase.registerCard(
                accountId,
                request.holderName(),
                request.cardNumber(),
                request.active(),
                request.expiresAt()
        );
        return AccountResponse.from(account);
    }

    public record CreateAccountRequest(
            @NotBlank(message = "O nome do titular é obrigatório.")
            String ownerName
    ) {
    }

    public record RegisterCardRequest(
            @NotBlank(message = "O nome impresso no cartão é obrigatório.")
            String holderName,
            @Pattern(regexp = "\\d{13,19}", message = "O número do cartão deve ter entre 13 e 19 dígitos.")
            String cardNumber,
            boolean active,
            @FutureOrPresent(message = "A data de expiração deve ser hoje ou uma data futura.")
            LocalDate expiresAt
    ) {
    }

    public record AccountResponse(
            UUID id,
            String ownerName,
            CardResponse card
    ) {
        static AccountResponse from(Account account) {
            CardResponse cardResponse = account.creditCard() == null
                    ? null
                    : new CardResponse(
                    account.creditCard().holderName(),
                    account.creditCard().lastFourDigits(),
                    account.creditCard().isActive(),
                    account.creditCard().expiresAt()
            );

            return new AccountResponse(account.id(), account.ownerName(), cardResponse);
        }
    }

    public record CardResponse(
            String holderName,
            String lastFourDigits,
            boolean active,
            LocalDate expiresAt
    ) {
    }
}
