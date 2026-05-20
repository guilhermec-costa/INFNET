package br.com.logistica.faturamento.domain.events;

import br.com.logistica.sharedkernel.domain.Dinheiro;
import br.com.logistica.sharedkernel.domain.EntregaId;

public record FaturaEmitida(EntregaId entregaId, String notaFiscal, Dinheiro valorTotal) {
}
