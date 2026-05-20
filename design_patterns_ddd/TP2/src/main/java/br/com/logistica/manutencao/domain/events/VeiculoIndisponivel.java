package br.com.logistica.manutencao.domain.events;

import br.com.logistica.manutencao.domain.IndisponibilidadeVeiculo;
import br.com.logistica.sharedkernel.domain.VeiculoId;

public record VeiculoIndisponivel(VeiculoId veiculoId, String placa, IndisponibilidadeVeiculo indisponibilidade) {
}
