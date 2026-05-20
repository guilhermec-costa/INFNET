package br.com.logistica.manutencao.application;

import br.com.logistica.manutencao.domain.IndisponibilidadeVeiculo;
import br.com.logistica.manutencao.domain.ManutencaoRepository;
import br.com.logistica.manutencao.domain.OrdemManutencao;
import br.com.logistica.manutencao.domain.Veiculo;
import br.com.logistica.manutencao.domain.events.VeiculoIndisponivel;
import br.com.logistica.sharedkernel.domain.VeiculoId;

public class PlanejarManutencaoService {

    private final ManutencaoRepository manutencaoRepository;

    public PlanejarManutencaoService(ManutencaoRepository manutencaoRepository) {
        this.manutencaoRepository = manutencaoRepository;
    }

    public Veiculo planejarOrdem(VeiculoId veiculoId, OrdemManutencao ordemManutencao) {
        Veiculo veiculo = manutencaoRepository.buscarPorId(veiculoId)
                .orElseThrow(() -> new IllegalArgumentException("Veículo não encontrado."));
        veiculo.adicionarOrdem(ordemManutencao);
        manutencaoRepository.salvar(veiculo);
        return veiculo;
    }

    public VeiculoIndisponivel marcarIndisponibilidade(VeiculoId veiculoId, IndisponibilidadeVeiculo indisponibilidade) {
        Veiculo veiculo = manutencaoRepository.buscarPorId(veiculoId)
                .orElseThrow(() -> new IllegalArgumentException("Veículo não encontrado."));
        VeiculoIndisponivel evento = veiculo.marcarIndisponivel(indisponibilidade);
        manutencaoRepository.salvar(veiculo);
        return evento;
    }
}
