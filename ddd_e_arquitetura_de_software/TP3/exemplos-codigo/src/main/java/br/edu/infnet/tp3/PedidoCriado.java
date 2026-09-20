package br.edu.infnet.tp3;

import java.math.BigDecimal;
import java.util.UUID;

public record PedidoCriado(UUID pedidoId, UUID clienteId, BigDecimal valor) {
}
