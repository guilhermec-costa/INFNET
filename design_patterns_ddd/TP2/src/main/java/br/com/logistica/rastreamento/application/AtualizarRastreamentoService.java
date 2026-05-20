package br.com.logistica.rastreamento.application;

import br.com.logistica.rastreamento.domain.EventoRastreamento;
import br.com.logistica.rastreamento.domain.RastreamentoRepository;
import br.com.logistica.rastreamento.infrastructure.RastreamentoGateway;

public class AtualizarRastreamentoService {

    private final RastreamentoRepository rastreamentoRepository;
    private final RastreamentoGateway rastreamentoGateway;

    public AtualizarRastreamentoService(RastreamentoRepository rastreamentoRepository,
                                        RastreamentoGateway rastreamentoGateway) {
        this.rastreamentoRepository = rastreamentoRepository;
        this.rastreamentoGateway = rastreamentoGateway;
    }

    public EventoRastreamento registrar(EventoRastreamento evento) {
        rastreamentoRepository.salvar(evento);
        rastreamentoGateway.notificarAtualizacao(evento);
        return evento;
    }
}
