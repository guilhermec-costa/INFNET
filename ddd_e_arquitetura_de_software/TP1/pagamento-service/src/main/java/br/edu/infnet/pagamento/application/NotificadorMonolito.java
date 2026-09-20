package br.edu.infnet.pagamento.application;

import br.edu.infnet.pagamento.domain.Pagamento;
import java.util.UUID;

/** Porta de saída: o domínio não conhece HTTP, banco ou classes do monólito. */
public interface NotificadorMonolito {
    void pagamentoProcessado(UUID pedidoId, UUID pagamentoId, Pagamento.StatusPagamento status);
}
