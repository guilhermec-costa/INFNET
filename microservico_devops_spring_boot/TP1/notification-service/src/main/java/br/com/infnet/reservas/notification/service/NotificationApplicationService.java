package br.com.infnet.reservas.notification.service;

import br.com.infnet.reservas.notification.domain.Notification;
import br.com.infnet.reservas.notification.dto.*;
import br.com.infnet.reservas.notification.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotificationApplicationService {
    private final NotificationRepository repository;

    public NotificationApplicationService(NotificationRepository repository) {
        this.repository = repository;
    }

    public NotificationResponse create(CreateNotificationRequest request) {
        return NotificationResponse
                .from(repository.save(new Notification(request.recipient(), request.subject(), request.message())));
    }

    public List<NotificationResponse> list() {
        return repository.findAll().stream().map(NotificationResponse::from).toList();
    }
}
