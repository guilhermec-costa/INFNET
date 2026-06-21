package com.example.musicstreamer.domain.billing;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface TransactionRecordRepository {

    TransactionRecord save(TransactionRecord transactionRecord);

    List<TransactionRecord> findApprovedTransactionsInWindow(UUID accountId, OffsetDateTime start, OffsetDateTime end);
}
