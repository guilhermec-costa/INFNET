package br.com.logistica.fretes.domain.events;

import br.com.logistica.fretes.domain.Rota;
import br.com.logistica.sharedkernel.domain.EntregaId;
import br.com.logistica.sharedkernel.domain.ModalTransporte;

public record EntregaIniciada(EntregaId entregaId, Rota rota, ModalTransporte modalTransporte) {
}
