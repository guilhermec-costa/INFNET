package br.com.logistica.faturamento.infrastructure;

import br.com.logistica.faturamento.domain.Fatura;
import br.com.logistica.faturamento.domain.FaturaRepository;
import br.com.logistica.sharedkernel.domain.EntregaId;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class FaturaRepositoryImpl implements FaturaRepository {

    private final Map<EntregaId, Fatura> armazenamento = new ConcurrentHashMap<>();

    @Override
    public void salvar(Fatura fatura) {
        armazenamento.put(fatura.entregaId(), fatura);
    }

    @Override
    public Optional<Fatura> buscarPorEntregaId(EntregaId entregaId) {
        return Optional.ofNullable(armazenamento.get(entregaId));
    }
}
