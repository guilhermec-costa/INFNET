package br.com.logistica.sharedkernel.domain;

import java.time.Instant;

public record Periodo(Instant inicio, Instant fim) {

    public Periodo {
        if (fim.isBefore(inicio)) {
            throw new IllegalArgumentException("O fim do período não pode ser anterior ao início.");
        }
    }
}
