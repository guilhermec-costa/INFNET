package br.edu.infnet.tp3;

import java.math.BigDecimal;
import java.util.UUID;

public record EstoqueReservado(UUID pedidoId, BigDecimal valor) implements EventoPedido {
}
