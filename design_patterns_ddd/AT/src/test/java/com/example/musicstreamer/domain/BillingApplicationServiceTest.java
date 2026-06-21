package com.example.musicstreamer.domain;

import com.example.musicstreamer.application.billing.AuthorizationResult;
import com.example.musicstreamer.application.billing.BillingApplicationService;
import com.example.musicstreamer.domain.account.Account;
import com.example.musicstreamer.domain.account.AccountRepository;
import com.example.musicstreamer.domain.account.CreditCard;
import com.example.musicstreamer.domain.billing.PlanType;
import com.example.musicstreamer.domain.billing.Subscription;
import com.example.musicstreamer.domain.billing.SubscriptionRepository;
import com.example.musicstreamer.domain.billing.TransactionRecord;
import com.example.musicstreamer.domain.billing.TransactionRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BillingApplicationServiceTest {

    private BillingApplicationService billingApplicationService;
    private InMemoryAccountRepository accountRepository;
    private InMemorySubscriptionRepository subscriptionRepository;
    private InMemoryTransactionRecordRepository transactionRecordRepository;

    @BeforeEach
    void setUp() {
        accountRepository = new InMemoryAccountRepository();
        subscriptionRepository = new InMemorySubscriptionRepository();
        transactionRecordRepository = new InMemoryTransactionRecordRepository();

        Clock clock = Clock.fixed(Instant.parse("2026-06-21T15:00:00Z"), ZoneOffset.UTC);
        billingApplicationService = new BillingApplicationService(
                accountRepository,
                subscriptionRepository,
                transactionRecordRepository,
                clock
        );
    }

    @Test
    void shouldNotActivateSubscriptionWithoutValidCard() {
        Account account = Account.create("Maria");
        accountRepository.save(account);

        assertThatThrownBy(() -> billingApplicationService.activateSubscription(account.id(), PlanType.PREMIUM))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("cartão de crédito inválido");
    }

    @Test
    void shouldNotActivateSecondActivePlan() {
        Account account = Account.create("Carlos");
        account.registerCard(new CreditCard("Carlos", "1111", true, LocalDate.of(2027, 1, 1)));
        accountRepository.save(account);
        subscriptionRepository.save(Subscription.activate(account.id(), PlanType.INDIVIDUAL, OffsetDateTime.parse("2026-06-21T15:00:00Z")));

        assertThatThrownBy(() -> billingApplicationService.activateSubscription(account.id(), PlanType.FAMILY))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("usuário já possui um plano ativo");
    }

    @Test
    void shouldRejectTransactionWhenCardIsInactive() {
        Account account = Account.create("João");
        account.registerCard(new CreditCard("João", "2222", false, LocalDate.of(2027, 1, 1)));
        accountRepository.save(account);

        AuthorizationResult result = billingApplicationService.authorize(
                account.id(),
                "Spotify",
                new BigDecimal("39.90"),
                OffsetDateTime.parse("2026-06-21T15:00:00Z")
        );

        assertThat(result.approved()).isFalse();
        assertThat(result.violations()).containsExactly("cartão não ativo");
    }

    @Test
    void shouldRejectHighFrequencyTransactions() {
        Account account = Account.create("Laura");
        account.registerCard(new CreditCard("Laura", "3333", true, LocalDate.of(2027, 1, 1)));
        accountRepository.save(account);

        OffsetDateTime baseTime = OffsetDateTime.parse("2026-06-21T15:00:00Z");
        transactionRecordRepository.saveApproved(account.id(), "Spotify", "10.00", baseTime.minusSeconds(90));
        transactionRecordRepository.saveApproved(account.id(), "Spotify", "11.00", baseTime.minusSeconds(70));
        transactionRecordRepository.saveApproved(account.id(), "Spotify", "12.00", baseTime.minusSeconds(50));

        AuthorizationResult result = billingApplicationService.authorize(
                account.id(),
                "Spotify",
                new BigDecimal("13.00"),
                baseTime
        );

        assertThat(result.approved()).isFalse();
        assertThat(result.violations()).contains("alta-frequencia-pequeno-intervalo");
    }

    @Test
    void shouldRejectDuplicatedTransaction() {
        Account account = Account.create("Paula");
        account.registerCard(new CreditCard("Paula", "4444", true, LocalDate.of(2027, 1, 1)));
        accountRepository.save(account);

        OffsetDateTime baseTime = OffsetDateTime.parse("2026-06-21T15:00:00Z");
        transactionRecordRepository.saveApproved(account.id(), "Spotify", "29.90", baseTime.minusSeconds(90));
        transactionRecordRepository.saveApproved(account.id(), "Spotify", "29.90", baseTime.minusSeconds(20));

        AuthorizationResult result = billingApplicationService.authorize(
                account.id(),
                "Spotify",
                new BigDecimal("29.90"),
                baseTime
        );

        assertThat(result.approved()).isFalse();
        assertThat(result.violations()).contains("transacao-duplicada");
    }

    private static class InMemoryAccountRepository implements AccountRepository {
        private final Map<UUID, Account> storage = new HashMap<>();

        @Override
        public Account save(Account account) {
            storage.put(account.id(), account);
            return account;
        }

        @Override
        public Optional<Account> findById(UUID id) {
            return Optional.ofNullable(storage.get(id));
        }
    }

    private static class InMemorySubscriptionRepository implements SubscriptionRepository {
        private final List<Subscription> storage = new ArrayList<>();

        @Override
        public Subscription save(Subscription subscription) {
            storage.add(subscription);
            return subscription;
        }

        @Override
        public Optional<Subscription> findActiveByAccountId(UUID accountId) {
            return storage.stream().filter(subscription -> subscription.accountId().equals(accountId) && subscription.isActive()).findFirst();
        }
    }

    private static class InMemoryTransactionRecordRepository implements TransactionRecordRepository {
        private final List<TransactionRecord> storage = new ArrayList<>();

        @Override
        public TransactionRecord save(TransactionRecord transactionRecord) {
            storage.add(transactionRecord);
            return transactionRecord;
        }

        @Override
        public List<TransactionRecord> findApprovedTransactionsInWindow(UUID accountId, OffsetDateTime start, OffsetDateTime end) {
            return storage.stream()
                    .filter(TransactionRecord::approved)
                    .filter(transaction -> transaction.accountId().equals(accountId))
                    .filter(transaction -> !transaction.occurredAt().isBefore(start) && !transaction.occurredAt().isAfter(end))
                    .toList();
        }

        void saveApproved(UUID accountId, String merchant, String amount, OffsetDateTime occurredAt) {
            save(TransactionRecord.create(
                    accountId,
                    merchant,
                    com.example.musicstreamer.domain.billing.Money.of(new BigDecimal(amount)),
                    occurredAt,
                    true
            ));
        }
    }
}
