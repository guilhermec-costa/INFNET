package com.example.biblioteca.integration;

public record NotificacaoRequest(
        Long leitorId,
        String leitorNome,
        String tipo,
        String titulo,
        String mensagem
) {
}
