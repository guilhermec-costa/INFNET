package br.com.infnet.reservas.notification.dto;

import jakarta.validation.constraints.*;

public record CreateNotificationRequest(@Email @NotBlank String recipient, @NotBlank String subject,
        @NotBlank String message) {
}
