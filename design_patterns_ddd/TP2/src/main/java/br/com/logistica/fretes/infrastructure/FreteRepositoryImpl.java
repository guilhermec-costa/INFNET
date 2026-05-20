package br.com.logistica.fretes.infrastructure;

import br.com.logistica.fretes.domain.Frete;
import br.com.logistica.fretes.domain.FreteRepository;
import br.com.logistica.sharedkernel.domain.FreteId;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class FreteRepositoryImpl implements FreteRepository {

    private final Map<FreteId, Frete> armazenamento = new ConcurrentHashMap<>();

    @Override
    public void salvar(Frete frete) {
        armazenamento.put(frete.freteId(), frete);
    }

    @Override
    public Optional<Frete> buscarPorId(FreteId freteId) {
        return Optional.ofNullable(armazenamento.get(freteId));
    }
}
