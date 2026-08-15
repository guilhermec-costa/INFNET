package br.com.infnet.reservas.reservation.service;

import br.com.infnet.reservas.reservation.domain.Reservation;
import br.com.infnet.reservas.reservation.dto.*;
import br.com.infnet.reservas.reservation.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ReservationApplicationService {
    private final ReservationRepository repository;
    private final NotificationClient notificationClient;

    public ReservationApplicationService(ReservationRepository repository, NotificationClient notificationClient) {
        this.repository = repository;
        this.notificationClient = notificationClient;
    }

    @Transactional
    public ReservationResponse create(CreateReservationRequest request) {
        if (!request.endsAt().isAfter(request.startsAt()))
            throw new IllegalArgumentException("endsAt deve ser posterior a startsAt");
        Reservation saved = repository.save(
                new Reservation(request.roomName(), request.requesterEmail(), request.startsAt(), request.endsAt()));
        notificationClient.sendReservationConfirmation(saved);
        return ReservationResponse.from(saved);
    }

    public List<ReservationResponse> list() {
        return repository.findAll().stream().map(ReservationResponse::from).toList();
    }
}
