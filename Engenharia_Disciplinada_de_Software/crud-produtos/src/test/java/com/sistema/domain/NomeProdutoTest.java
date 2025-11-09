package com.sistema.domain;

import net.jqwik.api.*;
import net.jqwik.api.constraints.StringLength;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NomeProdutoTest {

    @Test
    void deveCriarNomeProdutoValido() {
        NomeProduto nome = NomeProduto.de("Notebook");
        assertEquals("Notebook", nome.getValor());
    }

    @Test
    void deveRemoverEspacosEmBranco() {
        NomeProduto nome = NomeProduto.de("  Notebook  ");
        assertEquals("Notebook", nome.getValor());
    }

    @Test
    void deveRejeitarNomeNulo() {
        assertThrows(IllegalArgumentException.class, () -> NomeProduto.de(null));
    }

    @Test
    void deveRejeitarNomeVazio() {
        assertThrows(IllegalArgumentException.class, () -> NomeProduto.de(""));
    }

    @Test
    void deveRejeitarNomeApenasEspacos() {
        assertThrows(IllegalArgumentException.class, () -> NomeProduto.de("   "));
    }

    @Test
    void deveRejeitarNomeMuitoCurto() {
        assertThrows(IllegalArgumentException.class, () -> NomeProduto.de("Ab"));
    }

    @Test
    void deveAceitarNomeComTamanhoMinimo() {
        NomeProduto nome = NomeProduto.de("ABC");
        assertEquals("ABC", nome.getValor());
    }

    @Test
    void deveRejeitarNomeMuitoLongo() {
        String nomeLongo = "A".repeat(101);
        assertThrows(IllegalArgumentException.class, () -> NomeProduto.de(nomeLongo));
    }

    @Test
    void deveAceitarNomeComTamanhoMaximo() {
        String nomeMaximo = "A".repeat(100);
        NomeProduto nome = NomeProduto.de(nomeMaximo);
        assertEquals(nomeMaximo, nome.getValor());
    }

    @Test
    void nomesIguaisDevemSerIguais() {
        NomeProduto nome1 = NomeProduto.de("Notebook");
        NomeProduto nome2 = NomeProduto.de("Notebook");
        assertEquals(nome1, nome2);
        assertEquals(nome1.hashCode(), nome2.hashCode());
    }

    @Test
    void deveRetornarStringCorretamente() {
        NomeProduto nome = NomeProduto.de("Notebook");
        assertEquals("Notebook", nome.toString());
    }

    @Property
    void nomeValidoDeveSerCriado(@ForAll @StringLength(min = 3, max = 100) String valor) {
        String valorTrimado = valor.trim();
        if (valorTrimado.length() >= 3 && valorTrimado.length() <= 100) {
            NomeProduto nome = NomeProduto.de(valor);
            assertNotNull(nome);
            assertEquals(valorTrimado, nome.getValor());
        }
    }

    @Property
    void nomeDeveRemoverEspacosProperty(@ForAll @StringLength(min = 3, max = 100) String valor) {
        String valorComEspacos = "  " + valor + "  ";
        String valorTrimado = valor.trim();
        
        if (valorTrimado.length() >= 3 && valorTrimado.length() <= 100) {
            NomeProduto nome = NomeProduto.de(valorComEspacos);
            assertEquals(valorTrimado, nome.getValor());
        }
    }

    @Property
    void nomeMuitoCurtoDeveSerRejeitado(@ForAll @StringLength(min = 1, max = 2) String valor) {
        assertThrows(IllegalArgumentException.class, () -> NomeProduto.de(valor));
    }

    @Property
    void nomesIguaisDevemSerIguaisProperty(@ForAll @StringLength(min = 3, max = 100) String valor) {
        String valorTrimado = valor.trim();
        if (valorTrimado.length() >= 3 && valorTrimado.length() <= 100) {
            NomeProduto nome1 = NomeProduto.de(valor);
            NomeProduto nome2 = NomeProduto.de(valor);
            assertEquals(nome1, nome2);
        }
    }
}