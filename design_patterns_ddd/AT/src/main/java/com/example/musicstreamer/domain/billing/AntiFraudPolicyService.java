package com.example.musicstreamer.domain.billing;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class AntiFraudPolicyService {

    public List<FraudViolation> evaluate(String merchant, Money money, OffsetDateTime occurredAt, List<TransactionRecord> recentApprovedTransactions) {
        List<FraudViolation> violations = new ArrayList<>();
        if (recentApprovedTransactions.size() >= 3) {
            violations.add(FraudViolation.HIGH_FREQUENCY_SMALL_INTERVAL);
        }

        long duplicatedCount = recentApprovedTransactions.stream()
                .filter(transaction -> transaction.merchant().equalsIgnoreCase(merchant))
                .filter(transaction -> transaction.money().amount().compareTo(money.amount()) == 0)
                .count();

        if (duplicatedCount >= 2) {
            violations.add(FraudViolation.DUPLICATED_TRANSACTION);
        }

        return violations;
    }
}
