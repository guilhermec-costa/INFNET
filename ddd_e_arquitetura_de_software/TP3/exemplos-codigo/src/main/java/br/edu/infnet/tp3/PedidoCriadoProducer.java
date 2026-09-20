package br.edu.infnet.tp3;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PedidoCriadoProducer {
    private final KafkaTemplate<String, PedidoCriado> kafkaTemplate;

    public PedidoCriadoProducer(KafkaTemplate<String, PedidoCriado> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publicar(Pedido pedido) {
        PedidoCriado evento = new PedidoCriado(pedido.id(), pedido.clienteId(), pedido.valor());
        // A chave mantém eventos do mesmo pedido na mesma partição.
        kafkaTemplate.send("pedidos.criados", pedido.id().toString(), evento);
    }
}
