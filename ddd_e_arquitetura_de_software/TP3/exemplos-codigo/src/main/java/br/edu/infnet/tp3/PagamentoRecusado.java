package br.edu.infnet.tp3;

import java.util.UUID;

public record PagamentoRecusado(UUID pedidoId) implements EventoPedido {
}
