package br.com.logistica.fretes.domain;

import br.com.logistica.sharedkernel.domain.Dinheiro;

import java.time.Duration;

public record Cotacao(Dinheiro preco, Duration prazoEstimado) {
}
