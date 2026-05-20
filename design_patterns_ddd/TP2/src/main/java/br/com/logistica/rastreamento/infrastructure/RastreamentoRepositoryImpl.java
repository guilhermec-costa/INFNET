package br.com.logistica.rastreamento.infrastructure;

import br.com.logistica.rastreamento.domain.EventoRastreamento;
import br.com.logistica.rastreamento.domain.RastreamentoRepository;
import br.com.logistica.sharedkernel.domain.EntregaId;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class RastreamentoRepositoryImpl implements RastreamentoRepository {

    private final Map<EntregaId, EventoRastreamento> armazenamento = new ConcurrentHashMap<>();

    @Override
    public void salvar(EventoRastreamento eventoRastreamento) {
        armazenamento.put(eventoRastreamento.entregaId(), eventoRastreamento);
    }

    @Override
    public Optional<EventoRastreamento> buscarPorEntregaId(EntregaId entregaId) {
        return Optional.ofNullable(armazenamento.get(entregaId));
    }
}
