package br.com.infnet.reservas.reservation.repository;

import br.com.infnet.reservas.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
