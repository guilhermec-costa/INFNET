package br.edu.infnet.tp3;

import java.math.BigDecimal;
import java.util.UUID;

public record SolicitacaoPagamento(UUID id, UUID pedidoId, BigDecimal valor) implements EventoPedido {
    public SolicitacaoPagamento(UUID pedidoId, BigDecimal valor) {
        this(UUID.randomUUID(), pedidoId, valor);
    }
}
