package br.com.logistica.fretes.application;

import br.com.logistica.fretes.domain.Frete;
import br.com.logistica.fretes.domain.FreteRepository;
import br.com.logistica.fretes.domain.events.EntregaIniciada;
import br.com.logistica.fretes.infrastructure.EntregaEventPublisher;
import br.com.logistica.sharedkernel.domain.FreteId;

public class IniciarEntregaService {

    private final FreteRepository freteRepository;
    private final EntregaEventPublisher eventPublisher;

    public IniciarEntregaService(FreteRepository freteRepository, EntregaEventPublisher eventPublisher) {
        this.freteRepository = freteRepository;
        this.eventPublisher = eventPublisher;
    }

    public EntregaIniciada executar(FreteId freteId) {
        Frete frete = freteRepository.buscarPorId(freteId)
                .orElseThrow(() -> new IllegalArgumentException("Frete não encontrado."));

        EntregaIniciada evento = frete.iniciarEntrega();
        freteRepository.salvar(frete);
        eventPublisher.publicar(evento);
        return evento;
    }
}
