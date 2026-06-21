package com.example.musicstreamer.api.billing;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.musicstreamer.application.billing.AuthorizationResult;
import com.example.musicstreamer.application.billing.SubscriptionActivationUseCase;
import com.example.musicstreamer.application.billing.TransactionAuthorizationUseCase;
import com.example.musicstreamer.domain.billing.PlanType;
import com.example.musicstreamer.domain.billing.Subscription;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@RestController
public class BillingController {

    private final SubscriptionActivationUseCase subscriptionActivationUseCase;
    private final TransactionAuthorizationUseCase transactionAuthorizationUseCase;

    public BillingController(
            SubscriptionActivationUseCase subscriptionActivationUseCase,
            TransactionAuthorizationUseCase transactionAuthorizationUseCase
    ) {
        this.subscriptionActivationUseCase = subscriptionActivationUseCase;
        this.transactionAuthorizationUseCase = transactionAuthorizationUseCase;
    }

    @PostMapping("/accounts/{accountId}/subscriptions")
    @ResponseStatus(HttpStatus.CREATED)
    public SubscriptionResponse activateSubscription(@PathVariable UUID accountId, @Valid @RequestBody ActivateSubscriptionRequest request) {
        return SubscriptionResponse.from(subscriptionActivationUseCase.activateSubscription(accountId, request.planType()));
    }

    @PostMapping("/transactions/authorizations")
    public AuthorizationResponse authorize(@Valid @RequestBody AuthorizeTransactionRequest request) {
        return AuthorizationResponse.from(transactionAuthorizationUseCase.authorize(
                request.accountId(),
                request.merchant(),
                request.amount(),
                request.occurredAt()
        ));
    }

    public record ActivateSubscriptionRequest(
            @NotNull(message = "O tipo de plano é obrigatório.")
            PlanType planType
    ) {
    }

    public record AuthorizeTransactionRequest(
            @NotNull(message = "A conta é obrigatória.")
            UUID accountId,
            @NotBlank(message = "O comerciante é obrigatório.")
            String merchant,
            @NotNull(message = "O valor é obrigatório.")
            @DecimalMin(value = "0.01", message = "O valor da transação deve ser maior que zero.")
            BigDecimal amount,
            @NotNull(message = "A data e hora da transação são obrigatórias.")
            OffsetDateTime occurredAt
    ) {
    }

    public record SubscriptionResponse(UUID id, UUID accountId, PlanType planType, boolean active, OffsetDateTime activatedAt) {
        static SubscriptionResponse from(Subscription subscription) {
            return new SubscriptionResponse(
                    subscription.id(),
                    subscription.accountId(),
                    subscription.planType(),
                    subscription.isActive(),
                    subscription.activatedAt()
            );
        }
    }

    public record AuthorizationResponse(
            UUID accountId,
            String ownerName,
            String merchant,
            boolean approved,
            AccountStatusResponse account,
            List<String> violations
    ) {
        static AuthorizationResponse from(AuthorizationResult result) {
            return new AuthorizationResponse(
                    result.accountId(),
                    result.ownerName(),
                    result.merchant(),
                    result.approved(),
                    new AccountStatusResponse(result.cardRegistered(), result.cardActive(), result.subscriptionActive()),
                    result.violations()
            );
        }
    }

    public record AccountStatusResponse(boolean cardRegistered, boolean cardActive, boolean subscriptionActive) {
    }
}
