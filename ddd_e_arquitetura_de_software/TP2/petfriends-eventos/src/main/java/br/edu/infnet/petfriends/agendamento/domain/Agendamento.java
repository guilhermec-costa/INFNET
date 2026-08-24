package br.edu.infnet.petfriends.agendamento.domain;

import br.edu.infnet.petfriends.shared.domain.DomainEvent;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/** Aggregate Root do contexto de Agendamento. */
public final class Agendamento {
    private final UUID id;
    private final UUID petId;          // referência ao agregado Pet somente pelo ID
    private final UUID veterinarioId;  // referência ao agregado Veterinário somente pelo ID
    private final LocalDateTime horario;
    private Status status;
    private final List<DomainEvent> eventos = new ArrayList<>();

    public Agendamento(UUID petId, UUID veterinarioId, LocalDateTime horario) {
        this.id = UUID.randomUUID();
        this.petId = Objects.requireNonNull(petId);
        this.veterinarioId = Objects.requireNonNull(veterinarioId);
        this.horario = Objects.requireNonNull(horario);
        this.status = Status.PENDENTE;
    }

    /** Confirma a consulta somente uma vez e registra o fato ocorrido. */
    public void confirmar() {
        if (status != Status.PENDENTE) {
            throw new IllegalStateException("Somente um agendamento pendente pode ser confirmado");
        }
        status = Status.CONFIRMADO;
        eventos.add(new ConsultaAgendada(id, petId, veterinarioId, horario, Instant.now()));
    }

    public List<DomainEvent> retirarEventos() {
        List<DomainEvent> pendentes = List.copyOf(eventos);
        eventos.clear();
        return pendentes;
    }

    public UUID id() { return id; }
    public UUID petId() { return petId; }
    public UUID veterinarioId() { return veterinarioId; }
    public LocalDateTime horario() { return horario; }
    public Status status() { return status; }
    public enum Status { PENDENTE, CONFIRMADO, CANCELADO }
}
