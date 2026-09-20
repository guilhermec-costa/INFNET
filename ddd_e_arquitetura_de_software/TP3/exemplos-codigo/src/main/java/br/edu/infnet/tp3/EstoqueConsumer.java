package br.edu.infnet.tp3;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EstoqueConsumer {
    private final EstoqueService estoqueService;

    public EstoqueConsumer(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @KafkaListener(topics = "pedidos.criados", groupId = "estoque")
    public void reservar(PedidoCriado evento) {
        estoqueService.reservar(evento.pedidoId());
    }
}
