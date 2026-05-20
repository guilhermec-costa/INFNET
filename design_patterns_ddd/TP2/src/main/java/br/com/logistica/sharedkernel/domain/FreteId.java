package br.com.logistica.sharedkernel.domain;

import java.util.UUID;

public record FreteId(UUID valor) {

    public static FreteId novo() {
        return new FreteId(UUID.randomUUID());
    }
}
