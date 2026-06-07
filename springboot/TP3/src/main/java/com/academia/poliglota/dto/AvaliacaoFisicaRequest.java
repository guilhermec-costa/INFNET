package com.academia.poliglota.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record AvaliacaoFisicaRequest(
        @NotNull Long alunoId,
        @NotNull BigDecimal peso,
        @NotNull BigDecimal altura,
        @NotNull BigDecimal percentualGordura,
        String anotacoesMedicas
) {
}
