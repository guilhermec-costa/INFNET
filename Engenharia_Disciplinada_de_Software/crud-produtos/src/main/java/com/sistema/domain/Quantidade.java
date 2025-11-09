package com.sistema.domain;

import java.util.Objects;

public final class Quantidade {
    private static final int QUANTIDADE_MINIMA = 0;
    private static final int QUANTIDADE_MAXIMA = 999999;

    private final int valor;

    private Quantidade(int valor) {
        if (valor < QUANTIDADE_MINIMA) {
            throw new IllegalArgumentException(
                "Quantidade não pode ser menor que " + QUANTIDADE_MINIMA
            );
        }
        
        if (valor > QUANTIDADE_MAXIMA) {
            throw new IllegalArgumentException(
                "Quantidade não pode ser maior que " + QUANTIDADE_MAXIMA
            );
        }
        
        this.valor = valor;
    }

    public static Quantidade de(int valor) {
        return new Quantidade(valor);
    }

    public static Quantidade zero() {
        return new Quantidade(QUANTIDADE_MINIMA);
    }

    public int getValor() {
        return valor;
    }

    public Quantidade adicionar(int quantidade) {
        return new Quantidade(this.valor + quantidade);
    }

    public Quantidade subtrair(int quantidade) {
        return new Quantidade(this.valor - quantidade);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quantidade that = (Quantidade) o;
        return valor == that.valor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public String toString() {
        return String.valueOf(valor);
    }
}