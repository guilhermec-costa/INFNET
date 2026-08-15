package br.com.infnet.reservas.reservation.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public record CreateReservationRequest(@NotBlank String roomName, @Email @NotBlank String requesterEmail,
        @NotNull LocalDateTime startsAt, @NotNull LocalDateTime endsAt) {
}
