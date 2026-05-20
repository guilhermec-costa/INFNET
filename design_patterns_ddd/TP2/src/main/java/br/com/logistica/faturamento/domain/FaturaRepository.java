package br.com.logistica.faturamento.domain;

import br.com.logistica.sharedkernel.domain.EntregaId;

import java.util.Optional;

public interface FaturaRepository {

    void salvar(Fatura fatura);

    Optional<Fatura> buscarPorEntregaId(EntregaId entregaId);
}
