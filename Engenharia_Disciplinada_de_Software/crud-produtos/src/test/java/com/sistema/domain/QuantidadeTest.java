package com.sistema.domain;

import net.jqwik.api.*;
import net.jqwik.api.constraints.IntRange;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantidadeTest {

    @Test
    void deveCriarQuantidadeValida() {
        Quantidade quantidade = Quantidade.de(10);
        assertEquals(10, quantidade.getValor());
    }

    @Test
    void deveAceitarQuantidadeZero() {
        Quantidade quantidade = Quantidade.de(0);
        assertEquals(0, quantidade.getValor());
    }

    @Test
    void deveCriarQuantidadeZeroComMetodoEstatico() {
        Quantidade quantidade = Quantidade.zero();
        assertEquals(0, quantidade.getValor());
    }

    @Test
    void deveRejeitarQuantidadeNegativa() {
        assertThrows(IllegalArgumentException.class, () -> Quantidade.de(-1));
    }

    @Test
    void deveAceitarQuantidadeMaxima() {
        Quantidade quantidade = Quantidade.de(999999);
        assertEquals(999999, quantidade.getValor());
    }

    @Test
    void deveRejeitarQuantidadeAcimaDoMaximo() {
        assertThrows(IllegalArgumentException.class, () -> Quantidade.de(1000000));
    }

    @Test
    void deveAdicionarQuantidade() {
        Quantidade quantidade = Quantidade.de(10);
        Quantidade novaQuantidade = quantidade.adicionar(5);
        assertEquals(15, novaQuantidade.getValor());
        assertEquals(10, quantidade.getValor());
    }

    @Test
    void deveSubtrairQuantidade() {
        Quantidade quantidade = Quantidade.de(10);
        Quantidade novaQuantidade = quantidade.subtrair(3);
        assertEquals(7, novaQuantidade.getValor());
        assertEquals(10, quantidade.getValor());
    }

    @Test
    void deveRejeitarSubtracaoQueResulteEmNegativo() {
        Quantidade quantidade = Quantidade.de(5);
        assertThrows(IllegalArgumentException.class, () -> quantidade.subtrair(10));
    }

    @Test
    void deveRejeitarAdicaoQueExcedaMaximo() {
        Quantidade quantidade = Quantidade.de(999990);
        assertThrows(IllegalArgumentException.class, () -> quantidade.adicionar(20));
    }

    @Test
    void quantidadesIguaisDevemSerIguais() {
        Quantidade q1 = Quantidade.de(10);
        Quantidade q2 = Quantidade.de(10);
        assertEquals(q1, q2);
        assertEquals(q1.hashCode(), q2.hashCode());
    }

    @Test
    void deveRetornarStringCorretamente() {
        Quantidade quantidade = Quantidade.de(42);
        assertEquals("42", quantidade.toString());
    }

    @Property
    void quantidadeValidaDeveSerCriada(@ForAll @IntRange(min = 0, max = 999999) int valor) {
        Quantidade quantidade = Quantidade.de(valor);
        assertNotNull(quantidade);
        assertEquals(valor, quantidade.getValor());
    }

    @Property
    void quantidadeNegativaDeveSerRejeitada(@ForAll @IntRange(min = -1000000, max = -1) int valor) {
        assertThrows(IllegalArgumentException.class, () -> Quantidade.de(valor));
    }

    @Property
    void adicaoDeveSerComutativa(@ForAll @IntRange(min = 0, max = 10000) int a, 
                                  @ForAll @IntRange(min = 0, max = 10000) int b) {
        Assume.that(a + b <= 999999);
        
        Quantidade q1 = Quantidade.de(a).adicionar(b);
        Quantidade q2 = Quantidade.de(b).adicionar(a);
        assertEquals(q1.getValor(), q2.getValor());
    }

    @Property
    void subtracaoDeveSerInversaDeAdicao(@ForAll @IntRange(min = 0, max = 10000) int inicial,
                                          @ForAll @IntRange(min = 0, max = 1000) int delta) {
        Assume.that(inicial >= delta);
        
        Quantidade quantidade = Quantidade.de(inicial);
        Quantidade resultado = quantidade.adicionar(delta).subtrair(delta);
        assertEquals(inicial, resultado.getValor());
    }

    @Property
    void quantidadesIguaisDevemSerIguaisProperty(@ForAll @IntRange(min = 0, max = 999999) int valor) {
        Quantidade q1 = Quantidade.de(valor);
        Quantidade q2 = Quantidade.de(valor);
        assertEquals(q1, q2);
    }

    @Property
    void imutabilidadeDeveSerMantida(@ForAll @IntRange(min = 0, max = 10000) int inicial,
                                      @ForAll @IntRange(min = 0, max = 1000) int adicao) {
        Assume.that(inicial + adicao <= 999999);
        
        Quantidade original = Quantidade.de(inicial);
        Quantidade nova = original.adicionar(adicao);
        
        assertEquals(inicial, original.getValor());
        assertEquals(inicial + adicao, nova.getValor());
    }
}