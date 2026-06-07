package com.academia.poliglota.dto;

public record TokenValidacaoResponse(
        String token,
        boolean ativo
) {
}
