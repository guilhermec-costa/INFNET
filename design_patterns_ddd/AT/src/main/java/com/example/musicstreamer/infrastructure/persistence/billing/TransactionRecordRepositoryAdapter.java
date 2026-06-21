package com.example.musicstreamer.infrastructure.persistence.billing;

import com.example.musicstreamer.domain.billing.TransactionRecord;
import com.example.musicstreamer.domain.billing.TransactionRecordRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public class TransactionRecordRepositoryAdapter implements TransactionRecordRepository {

    private final SpringDataTransactionRecordRepository repository;

    public TransactionRecordRepositoryAdapter(SpringDataTransactionRecordRepository repository) {
        this.repository = repository;
    }

    @Override
    public TransactionRecord save(TransactionRecord transactionRecord) {
        return repository.save(transactionRecord);
    }

    @Override
    public List<TransactionRecord> findApprovedTransactionsInWindow(UUID accountId, OffsetDateTime start, OffsetDateTime end) {
        return repository.findByAccountIdAndApprovedTrueAndOccurredAtBetween(accountId, start, end);
    }
}
