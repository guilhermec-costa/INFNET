package br.com.logistica.faturamento.infrastructure;

import br.com.logistica.faturamento.domain.Fatura;

import java.util.UUID;

public class NotaFiscalGateway {

    public String gerarNotaFiscal(Fatura fatura) {
        return "NF-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
