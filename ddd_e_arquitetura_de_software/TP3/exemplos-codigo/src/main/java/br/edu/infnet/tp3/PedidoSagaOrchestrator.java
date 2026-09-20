package br.edu.infnet.tp3;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PedidoSagaOrchestrator {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PedidoSagaOrchestrator(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "pedidos.criados", groupId = "saga-pedido")
    public void iniciar(PedidoCriado evento) {
        enviar("estoque.reservas.solicitadas",
                new ReservaEstoqueSolicitada(evento.pedidoId()));
    }

    @KafkaListener(topics = "estoque.reservado", groupId = "saga-pedido")
    public void solicitarPagamento(EstoqueReservado evento) {
        enviar("pagamentos.solicitados",
                new SolicitacaoPagamento(evento.pedidoId(), evento.valor()));
    }

    @KafkaListener(topics = "pagamentos.recusados", groupId = "saga-pedido")
    public void compensar(PagamentoRecusado evento) {
        enviar("estoque.liberacoes.solicitadas",
                new LiberacaoEstoqueSolicitada(evento.pedidoId()));
        enviar("pedidos.cancelados", new PedidoCancelado(evento.pedidoId()));
    }

    private void enviar(String topico, EventoPedido evento) {
        kafkaTemplate.send(topico, evento.pedidoId().toString(), evento);
    }
}
