package br.com.infnet.reservas.notification.controller;

import br.com.infnet.reservas.notification.dto.*;
import br.com.infnet.reservas.notification.service.NotificationApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationApplicationService service;

    public NotificationController(NotificationApplicationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<NotificationResponse> create(@Valid @RequestBody CreateNotificationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @GetMapping
    public List<NotificationResponse> list() {
        return service.list();
    }
}
