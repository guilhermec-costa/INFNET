package br.com.logistica.faturamento.domain;

import br.com.logistica.faturamento.domain.events.FaturaEmitida;
import br.com.logistica.sharedkernel.domain.Dinheiro;
import br.com.logistica.sharedkernel.domain.EntregaId;

import java.util.List;

public class Fatura {

    private final EntregaId entregaId;
    private final List<ItemFatura> itens;
    private String notaFiscal;

    public Fatura(EntregaId entregaId, List<ItemFatura> itens) {
        this.entregaId = entregaId;
        this.itens = List.copyOf(itens);
    }

    public FaturaEmitida emitir(String notaFiscal) {
        this.notaFiscal = notaFiscal;
        return new FaturaEmitida(entregaId, notaFiscal, valorTotal());
    }

    public Dinheiro valorTotal() {
        return itens.stream()
                .map(ItemFatura::valor)
                .reduce(Dinheiro.de(java.math.BigDecimal.ZERO), Dinheiro::somar);
    }

    public EntregaId entregaId() {
        return entregaId;
    }

    public List<ItemFatura> itens() {
        return itens;
    }

    public String notaFiscal() {
        return notaFiscal;
    }
}
