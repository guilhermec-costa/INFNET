package br.edu.infnet.pagamento.infrastructure;

import br.edu.infnet.pagamento.application.NotificadorMonolito;
import br.edu.infnet.pagamento.domain.Pagamento;
import java.util.UUID;

/** Adaptador substituível: na migração, chamaria a API/fachada do monólito. */
public final class ClienteMonolitoHttp implements NotificadorMonolito {
    @Override
    public void pagamentoProcessado(UUID pedidoId, UUID pagamentoId, Pagamento.StatusPagamento status) {
        // POST /integracoes/pedidos/{pedidoId}/pagamento com os dados necessários.
        // Nenhuma tabela nem classe interna do monólito é acessada diretamente.
        System.out.printf("Notificando pedido %s: pagamento %s = %s%n", pedidoId, pagamentoId, status);
    }
}
