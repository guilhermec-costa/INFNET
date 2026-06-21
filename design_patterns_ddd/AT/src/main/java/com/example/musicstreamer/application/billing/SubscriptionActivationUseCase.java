package com.example.musicstreamer.application.billing;

import com.example.musicstreamer.domain.billing.PlanType;
import com.example.musicstreamer.domain.billing.Subscription;

import java.util.UUID;

public interface SubscriptionActivationUseCase {

    Subscription activateSubscription(UUID accountId, PlanType planType);
}
