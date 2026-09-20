package br.edu.infnet.tp3;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProcessadorPagamento {
    private final MensagemProcessadaRepository mensagens;
    private final PagamentoRepository pagamentos;

    public ProcessadorPagamento(MensagemProcessadaRepository mensagens,
                                PagamentoRepository pagamentos) {
        this.mensagens = mensagens;
        this.pagamentos = pagamentos;
    }

    @Transactional
    @KafkaListener(topics = "pagamentos.solicitados", groupId = "pagamento")
    public void processar(SolicitacaoPagamento mensagem) {
        if (mensagens.existsById(mensagem.id())) {
            return; // Reentrega: não repete o efeito de negócio.
        }
        pagamentos.save(Pagamento.autorizar(mensagem.pedidoId(), mensagem.valor()));
        mensagens.save(new MensagemProcessada(mensagem.id()));
    }
}
