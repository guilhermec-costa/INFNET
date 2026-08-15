package br.com.infnet.reservas.notification.repository;

import br.com.infnet.reservas.notification.domain.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification, String> {
}
