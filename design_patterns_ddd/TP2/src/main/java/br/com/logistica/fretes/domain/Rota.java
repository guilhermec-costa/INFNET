package br.com.logistica.fretes.domain;

import br.com.logistica.sharedkernel.domain.Endereco;
import br.com.logistica.sharedkernel.domain.ModalTransporte;

import java.util.List;

public record Rota(Endereco origem, Endereco destino, List<String> trechos, ModalTransporte modal) {
}
