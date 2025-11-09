package com.sistema.domain;

import net.jqwik.api.*;
import net.jqwik.api.constraints.BigRange;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PrecoTest {

    @Test
    void deveCriarPrecoValido() {
        Preco preco = Preco.de(new BigDecimal("10.50"));
        assertEquals(new BigDecimal("10.50"), preco.getValor());
    }

    @Test
    void deveCriarPrecoComDouble() {
        Preco preco = Preco.de(10.50);
        assertEquals(new BigDecimal("10.50"), preco.getValor());
    }

    @Test
    void deveArredondarPrecoParaDuasCasasDecimais() {
        Preco preco = Preco.de(new BigDecimal("10.556"));
        assertEquals(new BigDecimal("10.56"), preco.getValor());
    }

    @Test
    void deveRejeitarPrecoNulo() {
        assertThrows(IllegalArgumentException.class, () -> Preco.de((BigDecimal) null));
    }

    @Test
    void deveRejeitarPrecoNegativo() {
        assertThrows(IllegalArgumentException.class, () -> Preco.de(new BigDecimal("-0.01")));
    }

    @Test
    void deveAceitarPrecoZero() {
        Preco preco = Preco.de(BigDecimal.ZERO);
        assertEquals(BigDecimal.ZERO.setScale(2), preco.getValor());
    }

    @Test
    void deveRejeitarPrecoAcimaDoMaximo() {
        assertThrows(IllegalArgumentException.class, () -> Preco.de(new BigDecimal("1000000.00")));
    }

    @Test
    void deveAceitarPrecoMaximo() {
        Preco preco = Preco.de(new BigDecimal("999999.99"));
        assertEquals(new BigDecimal("999999.99"), preco.getValor());
    }

    @Test
    void precosComMesmoValorDevemSerIguais() {
        Preco preco1 = Preco.de(new BigDecimal("10.50"));
        Preco preco2 = Preco.de(new BigDecimal("10.50"));
        assertEquals(preco1, preco2);
        assertEquals(preco1.hashCode(), preco2.hashCode());
    }

    @Test
    void deveFormatarPrecoCorretamente() {
        Preco preco = Preco.de(new BigDecimal("1234.56"));
        assertEquals("R$ 1234.56", preco.toString());
    }

    @Property
    void precoValidoDeveSerCriado(@ForAll @BigRange(min = "0.00", max = "999999.99") BigDecimal valor) {
        Preco preco = Preco.de(valor);
        assertNotNull(preco);
        assertTrue(preco.getValor().compareTo(BigDecimal.ZERO) >= 0);
        assertTrue(preco.getValor().compareTo(new BigDecimal("999999.99")) <= 0);
    }

    @Property
    void precoNegativoDeveSerRejeitado(@ForAll @BigRange(min = "-1000000", max = "-0.01") BigDecimal valor) {
        assertThrows(IllegalArgumentException.class, () -> Preco.de(valor));
    }

    @Property
    void precoDeveManterDuasCasasDecimais(@ForAll @BigRange(min = "0.00", max = "999999.99") BigDecimal valor) {
        Preco preco = Preco.de(valor);
        assertEquals(2, preco.getValor().scale());
    }

    @Property
    void precosComMesmoValorDevemSerIguaisProperty(@ForAll @BigRange(min = "0.00", max = "999999.99") BigDecimal valor) {
        Preco preco1 = Preco.de(valor);
        Preco preco2 = Preco.de(valor);
        assertEquals(preco1, preco2);
    }
}