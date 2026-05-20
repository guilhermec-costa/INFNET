package br.com.logistica.sharedkernel.domain;

import java.util.UUID;

public record EntregaId(UUID valor) {

    public static EntregaId nova() {
        return new EntregaId(UUID.randomUUID());
    }
}
