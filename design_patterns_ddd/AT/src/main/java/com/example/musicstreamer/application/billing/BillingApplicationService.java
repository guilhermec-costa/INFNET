package com.example.musicstreamer.application.billing;

import com.example.musicstreamer.api.shared.NotFoundException;
import com.example.musicstreamer.domain.account.Account;
import com.example.musicstreamer.domain.account.AccountRepository;
import com.example.musicstreamer.domain.billing.AntiFraudPolicyService;
import com.example.musicstreamer.domain.billing.FraudViolation;
import com.example.musicstreamer.domain.billing.Money;
import com.example.musicstreamer.domain.billing.PlanType;
import com.example.musicstreamer.domain.billing.Subscription;
import com.example.musicstreamer.domain.billing.SubscriptionRepository;
import com.example.musicstreamer.domain.billing.TransactionRecord;
import com.example.musicstreamer.domain.billing.TransactionRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class BillingApplicationService implements SubscriptionActivationUseCase, TransactionAuthorizationUseCase {

    private final AccountRepository accountRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final TransactionRecordRepository transactionRecordRepository;
    private final AntiFraudPolicyService antiFraudPolicyService;
    private final Clock clock;

    public BillingApplicationService(
            AccountRepository accountRepository,
            SubscriptionRepository subscriptionRepository,
            TransactionRecordRepository transactionRecordRepository,
            Clock clock
    ) {
        this.accountRepository = accountRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.transactionRecordRepository = transactionRecordRepository;
        this.antiFraudPolicyService = new AntiFraudPolicyService();
        this.clock = clock;
    }

    @Override
    public Subscription activateSubscription(UUID accountId, PlanType planType) {
        Account account = loadAccount(accountId);
        LocalDate today = LocalDate.now(clock);

        if (!account.hasValidCard(today)) {
            throw new IllegalStateException(FraudViolation.INVALID_CREDIT_CARD.message());
        }

        if (subscriptionRepository.findActiveByAccountId(accountId).isPresent()) {
            throw new IllegalStateException(FraudViolation.USER_ALREADY_HAS_ACTIVE_PLAN.message());
        }

        return subscriptionRepository.save(Subscription.activate(accountId, planType, OffsetDateTime.now(clock)));
    }

    @Override
    public AuthorizationResult authorize(UUID accountId, String merchant, BigDecimal amount, OffsetDateTime occurredAt) {
        Account account = loadAccount(accountId);
        boolean subscriptionActive = subscriptionRepository.findActiveByAccountId(accountId).isPresent();
        List<FraudViolation> violations = new ArrayList<>();
        LocalDate transactionDate = occurredAt.toLocalDate();

        if (account.creditCard() == null || !account.creditCard().isValidAt(transactionDate)) {
            violations.add(FraudViolation.INVALID_CREDIT_CARD);
        } else if (!account.creditCard().isActive()) {
            violations.add(FraudViolation.CARD_NOT_ACTIVE);
        }

        OffsetDateTime windowStart = occurredAt.minusMinutes(2);
        List<TransactionRecord> recentTransactions = transactionRecordRepository
                .findApprovedTransactionsInWindow(accountId, windowStart, occurredAt);

        violations.addAll(antiFraudPolicyService.evaluate(merchant, Money.of(amount), occurredAt, recentTransactions));

        boolean approved = violations.isEmpty();
        transactionRecordRepository.save(TransactionRecord.create(accountId, merchant, Money.of(amount), occurredAt, approved));

        return new AuthorizationResult(
                accountId,
                account.ownerName(),
                merchant,
                approved,
                account.creditCard() != null,
                account.creditCard() != null && account.creditCard().isActive(),
                subscriptionActive,
                violations.stream().map(FraudViolation::message).toList()
        );
    }

    private Account loadAccount(UUID accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Conta não encontrada."));
    }
}
