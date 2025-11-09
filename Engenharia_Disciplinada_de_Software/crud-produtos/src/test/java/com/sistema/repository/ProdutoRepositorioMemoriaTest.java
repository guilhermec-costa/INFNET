package com.sistema.repository;

import com.sistema.domain.*;
import net.jqwik.api.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoRepositorioMemoriaTest {

    private ProdutoRepositorioMemoria repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new ProdutoRepositorioMemoria();
    }

    @Test
    void deveSalvarProduto() {
        Produto produto = criarProdutoTeste();
        repositorio.salvar(produto);

        assertTrue(repositorio.existe(produto.getId()));
    }

    @Test
    void deveRejeitarSalvarProdutoNulo() {
        assertThrows(IllegalArgumentException.class, () -> repositorio.salvar(null));
    }

    @Test
    void deveBuscarProdutoPorId() {
        Produto produto = criarProdutoTeste();
        repositorio.salvar(produto);

        Optional<Produto> encontrado = repositorio.buscarPorId(produto.getId());

        assertTrue(encontrado.isPresent());
        assertEquals(produto.getId(), encontrado.get().getId());
    }

    @Test
    void deveRetornarVazioQuandoProdutoNaoExiste() {
        ProdutoId idInexistente = ProdutoId.gerar();
        Optional<Produto> resultado = repositorio.buscarPorId(idInexistente);

        assertFalse(resultado.isPresent());
    }

    @Test
    void deveRejeitarBuscaComIdNulo() {
        assertThrows(IllegalArgumentException.class, () -> repositorio.buscarPorId(null));
    }

    @Test
    void deveBuscarTodosProdutos() {
        Produto p1 = criarProdutoTeste();
        Produto p2 = Produto.criar(NomeProduto.de("Produto 2"), Preco.de(new BigDecimal("20")),
                Quantidade.de(2), Categoria.ALIMENTOS);

        repositorio.salvar(p1);
        repositorio.salvar(p2);

        List<Produto> produtos = repositorio.buscarTodos();

        assertEquals(2, produtos.size());
        assertTrue(produtos.contains(p1));
        assertTrue(produtos.contains(p2));
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHaProdutos() {
        List<Produto> produtos = repositorio.buscarTodos();
        assertTrue(produtos.isEmpty());
    }

    @Test
    void deveAtualizarProduto() {
        Produto produto = criarProdutoTeste();
        repositorio.salvar(produto);

        Produto atualizado = produto.atualizarNome(NomeProduto.de("Nome Atualizado"));
        repositorio.atualizar(atualizado);

        Optional<Produto> encontrado = repositorio.buscarPorId(produto.getId());
        assertTrue(encontrado.isPresent());
        assertEquals("Nome Atualizado", encontrado.get().getNome().getValor());
    }

    @Test
    void deveRejeitarAtualizarProdutoNulo() {
        assertThrows(IllegalArgumentException.class, () -> repositorio.atualizar(null));
    }

    @Test
    void deveLancarExcecaoAoAtualizarProdutoInexistente() {
        Produto produto = criarProdutoTeste();
        assertThrows(IllegalStateException.class, () -> repositorio.atualizar(produto));
    }

    @Test
    void deveDeletarProduto() {
        Produto produto = criarProdutoTeste();
        repositorio.salvar(produto);

        assertTrue(repositorio.existe(produto.getId()));

        repositorio.deletar(produto.getId());

        assertFalse(repositorio.existe(produto.getId()));
    }

    @Test
    void deveRejeitarDeletarComIdNulo() {
        assertThrows(IllegalArgumentException.class, () -> repositorio.deletar(null));
    }

    @Test
    void deveDeletarSemErroQuandoProdutoNaoExiste() {
        ProdutoId idInexistente = ProdutoId.gerar();
        assertDoesNotThrow(() -> repositorio.deletar(idInexistente));
    }

    @Test
    void deveVerificarExistencia() {
        Produto produto = criarProdutoTeste();
        assertFalse(repositorio.existe(produto.getId()));

        repositorio.salvar(produto);
        assertTrue(repositorio.existe(produto.getId()));

        repositorio.deletar(produto.getId());
        assertFalse(repositorio.existe(produto.getId()));
    }

    @Test
    void deveRejeitarVerificarExistenciaComIdNulo() {
        assertThrows(IllegalArgumentException.class, () -> repositorio.existe(null));
    }

    @Test
    void deveContarProdutos() {
        assertEquals(0, repositorio.contar());

        repositorio.salvar(criarProdutoTeste());
        assertEquals(1, repositorio.contar());

        repositorio.salvar(Produto.criar(NomeProduto.de("Produto 2"),
                Preco.de(BigDecimal.TEN), Quantidade.de(1), Categoria.OUTROS));
        assertEquals(2, repositorio.contar());
    }

    @Property
    void produtoSalvoDeveSerRecuperavel(@ForAll("produtos") Produto produto) {
        ProdutoRepositorioMemoria repo = new ProdutoRepositorioMemoria();
        repo.salvar(produto);

        Optional<Produto> recuperado = repo.buscarPorId(produto.getId());

        assertTrue(recuperado.isPresent());
        assertEquals(produto.getId(), recuperado.get().getId());
    }

    @Property
    void contagemDeveCorresponderAoTamanho(@ForAll("listaProdutos") List<Produto> produtos) {
        ProdutoRepositorioMemoria repo = new ProdutoRepositorioMemoria();

        produtos.forEach(repo::salvar);

        assertEquals(produtos.size(), repo.contar());
        assertEquals(produtos.size(), repo.buscarTodos().size());
    }

    @Property
    void produtoDeletadoNaoDeveExistir(@ForAll("produtos") Produto produto) {
        ProdutoRepositorioMemoria repo = new ProdutoRepositorioMemoria();
        repo.salvar(produto);

        assertTrue(repo.existe(produto.getId()));

        repo.deletar(produto.getId());

        assertFalse(repo.existe(produto.getId()));
        assertFalse(repo.buscarPorId(produto.getId()).isPresent());
    }

    private Produto criarProdutoTeste() {
        return Produto.criar(
                NomeProduto.de("Produto Teste"),
                Preco.de(new BigDecimal("100.00")),
                Quantidade.de(10),
                Categoria.OUTROS
        );
    }

    @Provide
    Arbitrary<Produto> produtos() {
        return Combinators.combine(
                Arbitraries.strings().alpha().ofMinLength(3).ofMaxLength(100),
                Arbitraries.bigDecimals().between(BigDecimal.ZERO, new BigDecimal("999999.99")).ofScale(2),
                Arbitraries.integers().between(0, 999999),
                Arbitraries.of(Categoria.class)
        ).as((nome, preco, quantidade, categoria) ->
                Produto.criar(NomeProduto.de(nome), Preco.de(preco),
                        Quantidade.de(quantidade), categoria)
        );
    }

    @Provide
    Arbitrary<List<Produto>> listaProdutos() {
        return produtos().list().ofMinSize(0).ofMaxSize(10)
                .map(lista -> lista.stream().distinct().toList());
    }
}