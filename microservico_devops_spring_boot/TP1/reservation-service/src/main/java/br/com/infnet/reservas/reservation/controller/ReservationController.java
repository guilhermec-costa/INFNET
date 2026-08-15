package br.com.infnet.reservas.reservation.controller;

import br.com.infnet.reservas.reservation.dto.*;
import br.com.infnet.reservas.reservation.service.ReservationApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    private final ReservationApplicationService service;

    public ReservationController(ReservationApplicationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> create(@Valid @RequestBody CreateReservationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @GetMapping
    public List<ReservationResponse> list() {
        return service.list();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<String> invalid(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
