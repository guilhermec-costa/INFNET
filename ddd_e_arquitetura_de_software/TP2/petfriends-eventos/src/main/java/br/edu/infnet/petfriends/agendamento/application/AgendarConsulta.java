package br.edu.infnet.petfriends.agendamento.application;

import br.edu.infnet.petfriends.agendamento.domain.Agendamento;
import java.time.LocalDateTime;
import java.util.UUID;

public final class AgendarConsulta {
    private final PublicadorEventos publicador;
    public AgendarConsulta(PublicadorEventos publicador) { this.publicador = publicador; }

    public Agendamento executar(UUID petId, UUID veterinarioId, LocalDateTime horario) {
        Agendamento agendamento = new Agendamento(petId, veterinarioId, horario);
        agendamento.confirmar();
        agendamento.retirarEventos().forEach(publicador::publicar);
        return agendamento;
    }
}
