package br.com.logistica.sharedkernel.domain;

import java.util.UUID;

public record VeiculoId(UUID valor) {

    public static VeiculoId novo() {
        return new VeiculoId(UUID.randomUUID());
    }
}
