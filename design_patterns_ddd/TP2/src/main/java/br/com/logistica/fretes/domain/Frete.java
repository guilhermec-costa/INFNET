package br.com.logistica.fretes.domain;

import br.com.logistica.fretes.domain.events.EntregaIniciada;
import br.com.logistica.sharedkernel.domain.EntregaId;
import br.com.logistica.sharedkernel.domain.FreteId;
import br.com.logistica.sharedkernel.domain.StatusEntrega;

public class Frete {

    private final FreteId freteId;
    private final EntregaId entregaId;
    private final Rota rota;
    private final Cotacao cotacao;
    private StatusEntrega statusEntrega;

    public Frete(FreteId freteId, EntregaId entregaId, Rota rota, Cotacao cotacao) {
        this.freteId = freteId;
        this.entregaId = entregaId;
        this.rota = rota;
        this.cotacao = cotacao;
        this.statusEntrega = StatusEntrega.PLANEJADA;
    }

    public EntregaIniciada iniciarEntrega() {
        this.statusEntrega = StatusEntrega.EM_TRANSITO;
        return new EntregaIniciada(entregaId, rota, rota.modal());
    }

    public FreteId freteId() {
        return freteId;
    }

    public EntregaId entregaId() {
        return entregaId;
    }

    public Rota rota() {
        return rota;
    }

    public Cotacao cotacao() {
        return cotacao;
    }

    public StatusEntrega statusEntrega() {
        return statusEntrega;
    }
}
