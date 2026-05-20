package br.com.logistica.manutencao.domain;

import br.com.logistica.sharedkernel.domain.Periodo;

public record IndisponibilidadeVeiculo(String motivo, Periodo periodo) {
}
