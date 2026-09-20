package br.edu.infnet.tp3;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OutboxPublisher {
    private final OutboxRepository outbox;
    private final KafkaTemplate<String, EventoOutbox> kafkaTemplate;

    public OutboxPublisher(OutboxRepository outbox, KafkaTemplate<String, EventoOutbox> kafkaTemplate) {
        this.outbox = outbox;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    public void publicarPendentes() {
        for (EventoOutbox evento : outbox.buscarPendentes()) {
            kafkaTemplate.send(evento.topico(), evento.chave(), evento);
            evento.marcarComoPublicado();
        }
    }
}
