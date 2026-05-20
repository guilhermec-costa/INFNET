package br.com.logistica.faturamento.domain;

import br.com.logistica.sharedkernel.domain.Dinheiro;

public record ItemFatura(String descricao, Dinheiro valor) {
}
