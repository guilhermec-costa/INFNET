package com.sistema.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public final class Preco {
    private static final BigDecimal VALOR_MINIMO = BigDecimal.ZERO;
    private static final BigDecimal VALOR_MAXIMO = new BigDecimal("999999.99");
    private static final int CASAS_DECIMAIS = 2;

    private final BigDecimal valor;

    private Preco(BigDecimal valor) {
        if (valor == null) {
            throw new IllegalArgumentException("Preço não pode ser nulo");
        }
        
        BigDecimal valorArredondado = valor.setScale(CASAS_DECIMAIS, RoundingMode.HALF_UP);
        
        if (valorArredondado.compareTo(VALOR_MINIMO) < 0) {
            throw new IllegalArgumentException("Preço não pode ser negativo");
        }
        
        if (valorArredondado.compareTo(VALOR_MAXIMO) > 0) {
            throw new IllegalArgumentException("Preço não pode exceder " + VALOR_MAXIMO);
        }
        
        this.valor = valorArredondado;
    }

    public static Preco de(BigDecimal valor) {
        return new Preco(valor);
    }

    public static Preco de(double valor) {
        return new Preco(BigDecimal.valueOf(valor));
    }

    public BigDecimal getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Preco preco = (Preco) o;
        return valor.compareTo(preco.valor) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public String toString() {
        return String.format("R$ %.2f", valor);
    }
}