package br.com.logistica;

import br.com.logistica.fretes.application.CotacaoFreteService;
import br.com.logistica.fretes.domain.Cotacao;
import br.com.logistica.fretes.domain.Rota;
import br.com.logistica.sharedkernel.domain.Endereco;
import br.com.logistica.sharedkernel.domain.ModalTransporte;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArquiteturaProjetoTest {

    @Test
    void deveCotarFretePorModal() {
        Rota rota = new Rota(
                new Endereco("Rua A", "São Paulo", "SP", "01000-000"),
                new Endereco("Rua B", "Campinas", "SP", "13000-000"),
                List.of("Trecho 1"),
                ModalTransporte.CAMINHAO
        );

        Cotacao cotacao = new CotacaoFreteService().cotar(rota);

        assertEquals("180.00", cotacao.preco().valor().toPlainString());
    }
}
