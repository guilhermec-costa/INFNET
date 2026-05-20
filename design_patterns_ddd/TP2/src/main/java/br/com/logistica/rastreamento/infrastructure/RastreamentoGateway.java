package br.com.logistica.rastreamento.infrastructure;

import br.com.logistica.rastreamento.domain.EventoRastreamento;

public class RastreamentoGateway {

    public void notificarAtualizacao(EventoRastreamento evento) {
        System.out.printf("Rastreamento atualizado para a entrega %s com status %s.%n",
                evento.entregaId().valor(), evento.statusEntrega());
    }
}
