package br.com.logistica.manutencao.domain;

import br.com.logistica.manutencao.domain.events.VeiculoIndisponivel;
import br.com.logistica.sharedkernel.domain.ModalTransporte;
import br.com.logistica.sharedkernel.domain.VeiculoId;

import java.util.ArrayList;
import java.util.List;

public class Veiculo {

    private final VeiculoId veiculoId;
    private final String placa;
    private final ModalTransporte modal;
    private final List<OrdemManutencao> ordens;
    private IndisponibilidadeVeiculo indisponibilidade;

    public Veiculo(VeiculoId veiculoId, String placa, ModalTransporte modal) {
        this.veiculoId = veiculoId;
        this.placa = placa;
        this.modal = modal;
        this.ordens = new ArrayList<>();
    }

    public void adicionarOrdem(OrdemManutencao ordemManutencao) {
        ordens.add(ordemManutencao);
    }

    public VeiculoIndisponivel marcarIndisponivel(IndisponibilidadeVeiculo indisponibilidade) {
        this.indisponibilidade = indisponibilidade;
        return new VeiculoIndisponivel(veiculoId, placa, indisponibilidade);
    }

    public VeiculoId veiculoId() {
        return veiculoId;
    }

    public String placa() {
        return placa;
    }

    public ModalTransporte modal() {
        return modal;
    }

    public List<OrdemManutencao> ordens() {
        return List.copyOf(ordens);
    }

    public IndisponibilidadeVeiculo indisponibilidade() {
        return indisponibilidade;
    }
}
