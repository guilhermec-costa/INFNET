package br.com.infnet.reservas.reservation.service;

import br.com.infnet.reservas.reservation.domain.Reservation;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Component
public class NotificationClient {
    private final RestTemplate restTemplate;
    private final String baseUrl;

    public NotificationClient(RestTemplate restTemplate,
            @Value("${notification.base-url:http://notification-service}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    @CircuitBreaker(name = "notificationService", fallbackMethod = "notificationUnavailable")
    public void sendReservationConfirmation(Reservation reservation) {
        restTemplate.postForEntity(baseUrl + "/api/notifications",
                Map.of("recipient", reservation.getRequesterEmail(), "subject", "Reserva confirmada", "message",
                        "Sua reserva da sala " + reservation.getRoomName() + " foi confirmada."),
                Void.class);
    }

    void notificationUnavailable(Reservation reservation, Throwable error) {
        // A reserva permanece confirmada; a indisponibilidade é registrada em log para reprocessamento futuro.
        System.err.println(
                "Notification service unavailable for reservation " + reservation.getId() + ": " + error.getMessage());
    }
}
