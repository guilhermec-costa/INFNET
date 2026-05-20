package br.com.logistica.rastreamento.domain;

import br.com.logistica.sharedkernel.domain.EntregaId;
import br.com.logistica.sharedkernel.domain.StatusEntrega;

public class EventoRastreamento {

    private final EntregaId entregaId;
    private final Checkpoint checkpoint;
    private final PrevisaoEntrega previsaoEntrega;
    private StatusEntrega statusEntrega;

    public EventoRastreamento(EntregaId entregaId,
                              Checkpoint checkpoint,
                              PrevisaoEntrega previsaoEntrega,
                              StatusEntrega statusEntrega) {
        this.entregaId = entregaId;
        this.checkpoint = checkpoint;
        this.previsaoEntrega = previsaoEntrega;
        this.statusEntrega = statusEntrega;
    }

    public void atualizarStatus(StatusEntrega novoStatus) {
        this.statusEntrega = novoStatus;
    }

    public EntregaId entregaId() {
        return entregaId;
    }

    public Checkpoint checkpoint() {
        return checkpoint;
    }

    public PrevisaoEntrega previsaoEntrega() {
        return previsaoEntrega;
    }

    public StatusEntrega statusEntrega() {
        return statusEntrega;
    }
}
