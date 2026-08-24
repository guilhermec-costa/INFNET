package br.edu.infnet.petfriends.agendamento.application;

import br.edu.infnet.petfriends.shared.domain.DomainEvent;

public interface PublicadorEventos {
    void publicar(DomainEvent evento);
}
