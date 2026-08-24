package br.edu.infnet.petfriends.shared.domain;

import java.time.Instant;
import java.util.UUID;

/** Contrato comum para fatos relevantes ocorridos no domínio. */
public interface DomainEvent {
    UUID eventId();
    Instant occurredOn();
}
