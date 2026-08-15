package br.com.infnet.reservas.reservation.dto;

import br.com.infnet.reservas.reservation.domain.*;
import java.time.LocalDateTime;

public record ReservationResponse(Long id, String roomName, String requesterEmail, LocalDateTime startsAt,
        LocalDateTime endsAt, ReservationStatus status) {
    public static ReservationResponse from(Reservation r) {
        return new ReservationResponse(r.getId(), r.getRoomName(), r.getRequesterEmail(), r.getStartsAt(),
                r.getEndsAt(), r.getStatus());
    }
}
