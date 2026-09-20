package br.edu.infnet.tp3;

import java.util.UUID;

public record PedidoCancelado(UUID pedidoId) implements EventoPedido {
}
