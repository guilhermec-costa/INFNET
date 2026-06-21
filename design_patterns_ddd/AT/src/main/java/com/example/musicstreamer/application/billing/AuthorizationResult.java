package com.example.musicstreamer.application.billing;

import java.util.List;
import java.util.UUID;

public record AuthorizationResult(
        UUID accountId,
        String ownerName,
        String merchant,
        boolean approved,
        boolean cardRegistered,
        boolean cardActive,
        boolean subscriptionActive,
        List<String> violations
) {
}
