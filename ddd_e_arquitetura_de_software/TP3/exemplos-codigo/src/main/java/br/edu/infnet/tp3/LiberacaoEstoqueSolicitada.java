package br.edu.infnet.tp3;

import java.util.UUID;

public record LiberacaoEstoqueSolicitada(UUID pedidoId) implements EventoPedido {
}
