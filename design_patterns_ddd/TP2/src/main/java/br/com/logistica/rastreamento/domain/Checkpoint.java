package br.com.logistica.rastreamento.domain;

import br.com.logistica.sharedkernel.domain.Localizacao;

import java.time.Instant;

public record Checkpoint(String descricao, Localizacao localizacao, Instant ocorridoEm) {
}
