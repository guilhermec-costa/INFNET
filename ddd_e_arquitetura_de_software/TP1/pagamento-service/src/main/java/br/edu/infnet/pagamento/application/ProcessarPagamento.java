package br.edu.infnet.pagamento.application;

import br.edu.infnet.pagamento.domain.Pagamento;

public final class ProcessarPagamento {
    private final NotificadorMonolito notificador;

    public ProcessarPagamento(NotificadorMonolito notificador) {
        this.notificador = notificador;
    }

    public Pagamento executar(Pagamento pagamento) {
        pagamento.aprovar(); // integração com gateway ficaria em outro adaptador
        notificador.pagamentoProcessado(pagamento.pedidoId(), pagamento.id(), pagamento.status());
        return pagamento;
    }
}
