package br.com.infnet.reservas.reservation.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String roomName;
    @Column(nullable = false)
    private String requesterEmail;
    @Column(nullable = false)
    private LocalDateTime startsAt;
    @Column(nullable = false)
    private LocalDateTime endsAt;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status = ReservationStatus.CONFIRMED;

    protected Reservation() {
    }

    public Reservation(String roomName, String requesterEmail, LocalDateTime startsAt, LocalDateTime endsAt) {
        this.roomName = roomName;
        this.requesterEmail = requesterEmail;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
    }

    public Long getId() {
        return id;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getRequesterEmail() {
        return requesterEmail;
    }

    public LocalDateTime getStartsAt() {
        return startsAt;
    }

    public LocalDateTime getEndsAt() {
        return endsAt;
    }

    public ReservationStatus getStatus() {
        return status;
    }
}
