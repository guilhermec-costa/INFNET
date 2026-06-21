package com.example.musicstreamer.infrastructure.persistence.billing;

import com.example.musicstreamer.domain.billing.TransactionRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface SpringDataTransactionRecordRepository extends JpaRepository<TransactionRecord, UUID> {

    List<TransactionRecord> findByAccountIdAndApprovedTrueAndOccurredAtBetween(UUID accountId, OffsetDateTime start, OffsetDateTime end);
}
