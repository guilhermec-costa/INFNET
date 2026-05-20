package br.com.logistica.manutencao.domain;

import java.time.Instant;

public record OrdemManutencao(String descricao, Instant agendadaPara, boolean preventiva) {
}
