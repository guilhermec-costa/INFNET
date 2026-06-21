package com.example.musicstreamer.application.billing;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public interface TransactionAuthorizationUseCase {

    AuthorizationResult authorize(UUID accountId, String merchant, BigDecimal amount, OffsetDateTime occurredAt);
}
