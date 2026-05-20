package br.com.logistica.rastreamento.domain;

import br.com.logistica.sharedkernel.domain.EntregaId;

import java.util.Optional;

public interface RastreamentoRepository {

    void salvar(EventoRastreamento eventoRastreamento);

    Optional<EventoRastreamento> buscarPorEntregaId(EntregaId entregaId);
}
