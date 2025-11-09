package com.sistema.domain;

import net.jqwik.api.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoTest {

    @Test
    void deveCriarProdutoValido() {
        Produto produto = Produto.criar(
                NomeProduto.de("Notebook"),
                Preco.de(new BigDecimal("2500.00")),
                Quantidade.de(10),
                Categoria.ELETRONICOS
        );

        assertNotNull(produto.getId());
        assertEquals("Notebook", produto.getNome().getValor());
        assertEquals(new BigDecimal("2500.00"), produto.getPreco().getValor());
        assertEquals(10, produto.getQuantidade().getValor());
        assertEquals(Categoria.ELETRONICOS, produto.getCategoria());
    }

    @Test
    void deveReconstituirProduto() {
        ProdutoId id = ProdutoId.gerar();
        Produto produto = Produto.reconstituir(
                id,
                NomeProduto.de("Mouse"),
                Preco.de(new BigDecimal("50.00")),
                Quantidade.de(20),
                Categoria.ELETRONICOS
        );

        assertEquals(id, produto.getId());
        assertEquals("Mouse", produto.getNome().getValor());
    }

    @Test
    void deveRejeitarIdNulo() {
        assertThrows(IllegalArgumentException.class, () ->
                Produto.reconstituir(null, NomeProduto.de("Teste"), Preco.de(BigDecimal.TEN),
                        Quantidade.de(1), Categoria.OUTROS)
        );
    }

    @Test
    void deveRejeitarNomeNulo() {
        assertThrows(IllegalArgumentException.class, () ->
                Produto.reconstituir(ProdutoId.gerar(), null, Preco.de(BigDecimal.TEN),
                        Quantidade.de(1), Categoria.OUTROS)
        );
    }

    @Test
    void deveRejeitarPrecoNulo() {
        assertThrows(IllegalArgumentException.class, () ->
                Produto.reconstituir(ProdutoId.gerar(), NomeProduto.de("Teste"), null,
                        Quantidade.de(1), Categoria.OUTROS)
        );
    }

    @Test
    void deveRejeitarQuantidadeNula() {
        assertThrows(IllegalArgumentException.class, () ->
                Produto.reconstituir(ProdutoId.gerar(), NomeProduto.de("Teste"), Preco.de(BigDecimal.TEN),
                        null, Categoria.OUTROS)
        );
    }

    @Test
    void deveRejeitarCategoriaNula() {
        assertThrows(IllegalArgumentException.class, () ->
                Produto.reconstituir(ProdutoId.gerar(), NomeProduto.de("Teste"), Preco.de(BigDecimal.TEN),
                        Quantidade.de(1), null)
        );
    }

    @Test
    void deveAtualizarNome() {
        Produto produto = Produto.criar(
                NomeProduto.de("Notebook"),
                Preco.de(new BigDecimal("2500.00")),
                Quantidade.de(10),
                Categoria.ELETRONICOS
        );

        Produto atualizado = produto.atualizarNome(NomeProduto.de("Laptop"));

        assertEquals("Laptop", atualizado.getNome().getValor());
        assertEquals(produto.getId(), atualizado.getId());
        assertEquals("Notebook", produto.getNome().getValor());
    }

    @Test
    void deveAtualizarPreco() {
        Produto produto = Produto.criar(
                NomeProduto.de("Mouse"),
                Preco.de(new BigDecimal("50.00")),
                Quantidade.de(20),
                Categoria.ELETRONICOS
        );

        Produto atualizado = produto.atualizarPreco(Preco.de(new BigDecimal("45.00")));

        assertEquals(new BigDecimal("45.00"), atualizado.getPreco().getValor());
        assertEquals(new BigDecimal("50.00"), produto.getPreco().getValor());
    }

    @Test
    void deveAtualizarQuantidade() {
        Produto produto = Produto.criar(
                NomeProduto.de("Teclado"),
                Preco.de(new BigDecimal("150.00")),
                Quantidade.de(15),
                Categoria.ELETRONICOS
        );

        Produto atualizado = produto.atualizarQuantidade(Quantidade.de(30));

        assertEquals(30, atualizado.getQuantidade().getValor());
        assertEquals(15, produto.getQuantidade().getValor());
    }

    @Test
    void deveAtualizarCategoria() {
        Produto produto = Produto.criar(
                NomeProduto.de("Livro Java"),
                Preco.de(new BigDecimal("80.00")),
                Quantidade.de(5),
                Categoria.LIVROS
        );

        Produto atualizado = produto.atualizarCategoria(Categoria.OUTROS);

        assertEquals(Categoria.OUTROS, atualizado.getCategoria());
        assertEquals(Categoria.LIVROS, produto.getCategoria());
    }

    @Test
    void produtosComMesmoIdDevemSerIguais() {
        ProdutoId id = ProdutoId.gerar();
        Produto p1 = Produto.reconstituir(id, NomeProduto.de("Produto"), Preco.de(BigDecimal.TEN),
                Quantidade.de(1), Categoria.OUTROS);
        Produto p2 = Produto.reconstituir(id, NomeProduto.de("Outro Nome"), Preco.de(new BigDecimal("20")),
                Quantidade.de(2), Categoria.ALIMENTOS);

        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void produtosComIdsDigerentesNaoDevemSerIguais() {
        Produto p1 = Produto.criar(NomeProduto.de("Produto 1"), Preco.de(BigDecimal.TEN),
                Quantidade.de(1), Categoria.OUTROS);
        Produto p2 = Produto.criar(NomeProduto.de("Produto 1"), Preco.de(BigDecimal.TEN),
                Quantidade.de(1), Categoria.OUTROS);

        assertNotEquals(p1, p2);
    }

    @Property
    void produtoCriadoDeveSerValido(@ForAll("nomesProduto") String nome,
                                     @ForAll("precos") BigDecimal preco,
                                     @ForAll("quantidades") int quantidade,
                                     @ForAll("categorias") Categoria categoria) {
        Produto produto = Produto.criar(
                NomeProduto.de(nome),
                Preco.de(preco),
                Quantidade.de(quantidade),
                categoria
        );

        assertNotNull(produto);
        assertNotNull(produto.getId());
        assertEquals(nome.trim(), produto.getNome().getValor());
        assertEquals(preco.setScale(2, java.math.RoundingMode.HALF_UP), produto.getPreco().getValor());
        assertEquals(quantidade, produto.getQuantidade().getValor());
        assertEquals(categoria, produto.getCategoria());
    }

    @Property
    void atualizacaoDeveManterImutabilidade(@ForAll("produtos") Produto original) {
        NomeProduto novoNome = NomeProduto.de("Nome Atualizado");
        Produto atualizado = original.atualizarNome(novoNome);

        assertEquals(original.getId(), atualizado.getId());
        assertNotEquals(original.getNome(), atualizado.getNome());
        assertEquals(novoNome, atualizado.getNome());
    }

    @Provide
    Arbitrary<String> nomesProduto() {
        return Arbitraries.strings().alpha().ofMinLength(3).ofMaxLength(100);
    }

    @Provide
    Arbitrary<BigDecimal> precos() {
        return Arbitraries.bigDecimals()
                .between(BigDecimal.ZERO, new BigDecimal("999999.99"))
                .ofScale(2);
    }

    @Provide
    Arbitrary<Integer> quantidades() {
        return Arbitraries.integers().between(0, 999999);
    }

    @Provide
    Arbitrary<Categoria> categorias() {
        return Arbitraries.of(Categoria.class);
    }

    @Provide
    Arbitrary<Produto> produtos() {
        return Combinators.combine(
                nomesProduto(),
                precos(),
                quantidades(),
                categorias()
        ).as((nome, preco, quantidade, categoria) ->
                Produto.criar(NomeProduto.de(nome), Preco.de(preco),
                        Quantidade.de(quantidade), categoria)
        );
    }
}