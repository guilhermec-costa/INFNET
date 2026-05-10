package br.com.infnet.tp1.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record OperationRequest(
        @NotNull(message = "O valor de 'a' é obrigatório.")
        BigDecimal a,
        @NotNull(message = "O valor de 'b' é obrigatório.")
        BigDecimal b
) {
}

