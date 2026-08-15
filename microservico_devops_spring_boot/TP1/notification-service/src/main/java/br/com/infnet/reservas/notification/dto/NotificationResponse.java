package br.com.infnet.reservas.notification.dto;

import br.com.infnet.reservas.notification.domain.Notification;
import java.time.Instant;

public record NotificationResponse(String id, String recipient, String subject, String message, Instant createdAt) {
    public static NotificationResponse from(Notification n) {
        return new NotificationResponse(n.getId(), n.getRecipient(), n.getSubject(), n.getMessage(), n.getCreatedAt());
    }
}
