package br.edu.infnet.tp3;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class Pagamento {
    @Id
    private UUID id;
    private UUID pedidoId;
    private BigDecimal valor;
    private String status;

    protected Pagamento() {
    }

    private Pagamento(UUID pedidoId, BigDecimal valor) {
        this.id = UUID.randomUUID();
        this.pedidoId = pedidoId;
        this.valor = valor;
        this.status = "AUTORIZADO";
    }

    public static Pagamento autorizar(UUID pedidoId, BigDecimal valor) {
        return new Pagamento(pedidoId, valor);
    }
}
