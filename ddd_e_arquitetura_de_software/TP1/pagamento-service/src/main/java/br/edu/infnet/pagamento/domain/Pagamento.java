package br.edu.infnet.pagamento.domain;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/** Aggregate Root: concentra as regras e o ciclo de vida do pagamento. */
public final class Pagamento {
    private final UUID id;
    private final UUID pedidoId;
    private final Dinheiro valor;
    private StatusPagamento status;

    public Pagamento(UUID pedidoId, Dinheiro valor) {
        this.id = UUID.randomUUID();
        this.pedidoId = Objects.requireNonNull(pedidoId);
        this.valor = Objects.requireNonNull(valor);
        this.status = StatusPagamento.PENDENTE;
    }

    public void aprovar() {
        if (status != StatusPagamento.PENDENTE) throw new IllegalStateException("Pagamento já processado");
        status = StatusPagamento.APROVADO;
    }

    public UUID id() { return id; }
    public UUID pedidoId() { return pedidoId; }
    public Dinheiro valor() { return valor; }
    public StatusPagamento status() { return status; }

    public record Dinheiro(BigDecimal valor, String moeda) {
        public Dinheiro {
            if (valor == null || valor.signum() <= 0) throw new IllegalArgumentException("Valor deve ser positivo");
            if (moeda == null || moeda.isBlank()) throw new IllegalArgumentException("Moeda obrigatória");
        }
    }

    public enum StatusPagamento { PENDENTE, APROVADO, RECUSADO }
}
