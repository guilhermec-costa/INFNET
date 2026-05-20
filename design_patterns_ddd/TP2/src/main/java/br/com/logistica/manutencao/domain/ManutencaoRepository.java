package br.com.logistica.manutencao.domain;

import br.com.logistica.sharedkernel.domain.VeiculoId;

import java.util.Optional;

public interface ManutencaoRepository {

    void salvar(Veiculo veiculo);

    Optional<Veiculo> buscarPorId(VeiculoId veiculoId);
}
