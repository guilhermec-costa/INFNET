package br.com.infnet.tp1.dto;

import java.math.BigDecimal;

public record OperationResponse(
        String operation,
        BigDecimal a,
        BigDecimal b,
        BigDecimal result
) {
}

