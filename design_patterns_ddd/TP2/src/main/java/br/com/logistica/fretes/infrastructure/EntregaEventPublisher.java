package br.com.logistica.fretes.infrastructure;

import br.com.logistica.fretes.domain.events.EntregaIniciada;

public class EntregaEventPublisher {

    public void publicar(EntregaIniciada evento) {
        System.out.printf("Evento publicado: entrega %s iniciada no modal %s.%n",
                evento.entregaId().valor(), evento.modalTransporte());
    }
}
