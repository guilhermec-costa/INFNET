package com.sistema.domain;

import net.jqwik.api.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoriaTest {

    @Test
    void deveObterDescricaoCorreta() {
        assertEquals("Eletrônicos", Categoria.ELETRONICOS.getDescricao());
        assertEquals("Alimentos", Categoria.ALIMENTOS.getDescricao());
        assertEquals("Vestuário", Categoria.VESTUARIO.getDescricao());
        assertEquals("Móveis", Categoria.MOVEIS.getDescricao());
        assertEquals("Livros", Categoria.LIVROS.getDescricao());
        assertEquals("Brinquedos", Categoria.BRINQUEDOS.getDescricao());
        assertEquals("Esportes", Categoria.ESPORTES.getDescricao());
        assertEquals("Beleza", Categoria.BELEZA.getDescricao());
        assertEquals("Automotivo", Categoria.AUTOMOTIVO.getDescricao());
        assertEquals("Outros", Categoria.OUTROS.getDescricao());
    }

    @Test
    void deveEncontrarCategoriaPorDescricao() {
        assertEquals(Categoria.ELETRONICOS, Categoria.fromDescricao("Eletrônicos"));
        assertEquals(Categoria.ALIMENTOS, Categoria.fromDescricao("Alimentos"));
    }

    @Test
    void deveEncontrarCategoriaIgnorandoCaso() {
        assertEquals(Categoria.ELETRONICOS, Categoria.fromDescricao("eletrônicos"));
        assertEquals(Categoria.ALIMENTOS, Categoria.fromDescricao("ALIMENTOS"));
    }

    @Test
    void deveEncontrarCategoriaComEspacos() {
        assertEquals(Categoria.ELETRONICOS, Categoria.fromDescricao("  Eletrônicos  "));
    }

    @Test
    void deveRejeitarDescricaoNula() {
        assertThrows(IllegalArgumentException.class, () -> Categoria.fromDescricao(null));
    }

    @Test
    void deveRejeitarDescricaoVazia() {
        assertThrows(IllegalArgumentException.class, () -> Categoria.fromDescricao(""));
    }

    @Test
    void deveRejeitarDescricaoApenasEspacos() {
        assertThrows(IllegalArgumentException.class, () -> Categoria.fromDescricao("   "));
    }

    @Test
    void deveRejeitarDescricaoInvalida() {
        assertThrows(IllegalArgumentException.class, () -> Categoria.fromDescricao("Categoria Inexistente"));
    }

    @Test
    void deveRetornarDescricaoNoToString() {
        assertEquals("Eletrônicos", Categoria.ELETRONICOS.toString());
        assertEquals("Alimentos", Categoria.ALIMENTOS.toString());
    }

    @Test
    void deveConterTodasAsCategorias() {
        Categoria[] categorias = Categoria.values();
        assertEquals(10, categorias.length);
    }

    @Property
    void todasCategoriasDevemTerDescricao(@ForAll("categorias") Categoria categoria) {
        assertNotNull(categoria.getDescricao());
        assertFalse(categoria.getDescricao().isEmpty());
    }

    @Property
    void deveEncontrarCategoriaValidaPorDescricao(@ForAll("categorias") Categoria categoria) {
        Categoria encontrada = Categoria.fromDescricao(categoria.getDescricao());
        assertEquals(categoria, encontrada);
    }

    @Property
    void descricaoDeveSerCaseInsensitive(@ForAll("categorias") Categoria categoria) {
        String descricaoLower = categoria.getDescricao().toLowerCase();
        String descricaoUpper = categoria.getDescricao().toUpperCase();
        
        assertEquals(categoria, Categoria.fromDescricao(descricaoLower));
        assertEquals(categoria, Categoria.fromDescricao(descricaoUpper));
    }

    @Provide
    Arbitrary<Categoria> categorias() {
        return Arbitraries.of(Categoria.class);
    }
}