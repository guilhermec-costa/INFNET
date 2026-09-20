package br.edu.infnet.tp3;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class PagamentoClient {
    private final RestClient restClient = RestClient.create("http://pagamento-service");

    public AutorizacaoPagamento autorizar(Pedido pedido) {
        return restClient.post()
                .uri("/pagamentos/autorizacoes")
                .body(new SolicitacaoPagamento(pedido.id(), pedido.valor()))
                .retrieve()
                .body(AutorizacaoPagamento.class);
    }
}
