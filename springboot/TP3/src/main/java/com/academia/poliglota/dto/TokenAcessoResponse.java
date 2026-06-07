package com.academia.poliglota.dto;

public record TokenAcessoResponse(
        Long alunoId,
        String token,
        long validadeEmSegundos
) {
}
