package com.example.musicstreamer.domain.billing;

public enum FraudViolation {
    USER_ALREADY_HAS_ACTIVE_PLAN("usuário já possui um plano ativo"),
    INVALID_CREDIT_CARD("cartão de crédito inválido"),
    CARD_NOT_ACTIVE("cartão não ativo"),
    HIGH_FREQUENCY_SMALL_INTERVAL("alta-frequencia-pequeno-intervalo"),
    DUPLICATED_TRANSACTION("transacao-duplicada");

    private final String message;

    FraudViolation(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
