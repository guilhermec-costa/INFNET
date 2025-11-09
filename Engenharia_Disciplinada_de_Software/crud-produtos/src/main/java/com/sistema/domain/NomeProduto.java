package com.sistema.domain;

import java.util.Objects;

public final class NomeProduto {
    private static final int TAMANHO_MINIMO = 3;
    private static final int TAMANHO_MAXIMO = 100;

    private final String valor;

    private NomeProduto(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("Nome do produto não pode ser nulo ou vazio");
        }
        
        String valorTrimado = valor.trim();
        
        if (valorTrimado.length() < TAMANHO_MINIMO) {
            throw new IllegalArgumentException(
                "Nome do produto deve ter no mínimo " + TAMANHO_MINIMO + " caracteres"
            );
        }
        
        if (valorTrimado.length() > TAMANHO_MAXIMO) {
            throw new IllegalArgumentException(
                "Nome do produto deve ter no máximo " + TAMANHO_MAXIMO + " caracteres"
            );
        }
        
        this.valor = valorTrimado;
    }

    public static NomeProduto de(String valor) {
        return new NomeProduto(valor);
    }

    public String getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NomeProduto that = (NomeProduto) o;
        return Objects.equals(valor, that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public String toString() {
        return valor;
    }
}