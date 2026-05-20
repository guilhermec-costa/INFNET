package br.com.logistica.manutencao.infrastructure;

import br.com.logistica.manutencao.domain.ManutencaoRepository;
import br.com.logistica.manutencao.domain.Veiculo;
import br.com.logistica.sharedkernel.domain.VeiculoId;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ManutencaoRepositoryImpl implements ManutencaoRepository {

    private final Map<VeiculoId, Veiculo> armazenamento = new ConcurrentHashMap<>();

    @Override
    public void salvar(Veiculo veiculo) {
        armazenamento.put(veiculo.veiculoId(), veiculo);
    }

    @Override
    public Optional<Veiculo> buscarPorId(VeiculoId veiculoId) {
        return Optional.ofNullable(armazenamento.get(veiculoId));
    }
}
