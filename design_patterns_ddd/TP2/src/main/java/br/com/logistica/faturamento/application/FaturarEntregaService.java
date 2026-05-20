package br.com.logistica.faturamento.application;

import br.com.logistica.faturamento.domain.Fatura;
import br.com.logistica.faturamento.domain.FaturaRepository;
import br.com.logistica.faturamento.domain.events.FaturaEmitida;
import br.com.logistica.faturamento.infrastructure.NotaFiscalGateway;

public class FaturarEntregaService {

    private final FaturaRepository faturaRepository;
    private final NotaFiscalGateway notaFiscalGateway;

    public FaturarEntregaService(FaturaRepository faturaRepository, NotaFiscalGateway notaFiscalGateway) {
        this.faturaRepository = faturaRepository;
        this.notaFiscalGateway = notaFiscalGateway;
    }

    public FaturaEmitida emitir(Fatura fatura) {
        String notaFiscal = notaFiscalGateway.gerarNotaFiscal(fatura);
        FaturaEmitida evento = fatura.emitir(notaFiscal);
        faturaRepository.salvar(fatura);
        return evento;
    }
}
