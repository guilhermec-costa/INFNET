package br.edu.infnet.petfriends.agendamento.domain;

import br.edu.infnet.petfriends.shared.domain.DomainEvent;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

/** Evento publicado quando uma consulta é confirmada no contexto de Agendamento. */
public record ConsultaAgendada(UUID agendamentoId, UUID petId, UUID veterinarioId,
                               LocalDateTime horario, Instant occurredOn, UUID eventId)
        implements DomainEvent {
    public ConsultaAgendada(UUID agendamentoId, UUID petId, UUID veterinarioId,
                            LocalDateTime horario, Instant occurredOn) {
        this(agendamentoId, petId, veterinarioId, horario, occurredOn, UUID.randomUUID());
    }
}
