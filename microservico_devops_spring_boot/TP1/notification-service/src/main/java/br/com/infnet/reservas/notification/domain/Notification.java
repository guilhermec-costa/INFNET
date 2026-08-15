package br.com.infnet.reservas.notification.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;

@Document("notifications")
public class Notification {
    @Id
    private String id;
    private String recipient;
    private String subject;
    private String message;
    private Instant createdAt;

    protected Notification() {
    }

    public Notification(String recipient, String subject, String message) {
        this.recipient = recipient;
        this.subject = subject;
        this.message = message;
        this.createdAt = Instant.now();
    }

    public String getId() {
        return id;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
