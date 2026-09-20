package br.edu.infnet.tp3;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PedidoController {
    private final PagamentoClient pagamentoClient;

    public PedidoController(PagamentoClient pagamentoClient) {
        this.pagamentoClient = pagamentoClient;
    }

    @PostMapping("/pedidos")
    public ResponseEntity<AutorizacaoPagamento> criar(@RequestBody Pedido pedido) {
        // O pedido só recebe resposta após o serviço de pagamento responder.
        return ResponseEntity.ok(pagamentoClient.autorizar(pedido));
    }
}
