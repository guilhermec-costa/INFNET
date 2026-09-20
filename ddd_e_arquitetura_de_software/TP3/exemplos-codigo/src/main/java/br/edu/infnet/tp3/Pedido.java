package br.edu.infnet.tp3;

import java.math.BigDecimal;
import java.util.UUID;

public record Pedido(UUID id, UUID clienteId, BigDecimal valor) {
}
