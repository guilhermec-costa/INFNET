package br.com.logistica.sharedkernel.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

public record Dinheiro(BigDecimal valor, Currency moeda) {

    public Dinheiro {
        valor = valor.setScale(2, RoundingMode.HALF_UP);
    }

    public static Dinheiro de(BigDecimal valor) {
        return new Dinheiro(valor, Currency.getInstance("BRL"));
    }

    public Dinheiro somar(Dinheiro outro) {
        validarMesmaMoeda(outro);
        return new Dinheiro(valor.add(outro.valor), moeda);
    }

    private void validarMesmaMoeda(Dinheiro outro) {
        if (!moeda.equals(outro.moeda)) {
            throw new IllegalArgumentException("Moedas diferentes não podem ser somadas.");
        }
    }
}
