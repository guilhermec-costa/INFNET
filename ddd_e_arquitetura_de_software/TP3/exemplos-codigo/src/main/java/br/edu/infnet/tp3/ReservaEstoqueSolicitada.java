package br.edu.infnet.tp3;

import java.util.UUID;

public record ReservaEstoqueSolicitada(UUID pedidoId) implements EventoPedido {
}
