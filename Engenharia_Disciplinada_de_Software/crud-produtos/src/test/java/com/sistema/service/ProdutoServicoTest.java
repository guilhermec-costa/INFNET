package com.sistema.service;

import com.sistema.domain.*;
import com.sistema.exception.ProdutoNaoEncontradoException;
import com.sistema.repository.ProdutoRepositorioMemoria;
import net.jqwik.api.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoServicoTest {

    private ProdutoServico servico;

    @BeforeEach
    void setUp() {
        servico = new ProdutoServico(new ProdutoRepositorioMemoria());
    }

    @Test
    void deveRejeitarRepositorioNulo() {
        assertThrows(IllegalArgumentException.class, () -> new ProdutoServico(null));
    }

    @Test
    void deveCriarProduto() {
        Produto produto = servico.criar(
                NomeProduto.de("Notebook"),
                Preco.de(new BigDecimal("2500.00")),
                Quantidade.de(10),
                Categoria.ELETRONICOS
        );

        assertNotNull(produto);
        assertNotNull(produto.getId());
        assertTrue(servico.existe(produto.getId()));
    }

    @Test
    void deveBuscarProdutoPorId() {
        Produto criado = servico.criar(
                NomeProduto.de("Mouse"),
                Preco.de(new BigDecimal("50.00")),
                Quantidade.de(20),
                Categoria.ELETRONICOS
        );

        Produto encontrado = servico.buscar(criado.getId());

        assertEquals(criado.getId(), encontrado.getId());
        assertEquals(criado.getNome().getValor(), encontrado.getNome().getValor());
    }

    @Test
    void deveLancarExcecaoQuandoProdutoNaoEncontrado() {
        ProdutoId idInexistente = ProdutoId.gerar();
        assertThrows(ProdutoNaoEncontradoException.class, () -> servico.buscar(idInexistente));
    }

    @Test
    void deveListarTodosProdutos() {
        servico.criar(NomeProduto.de("Produto 1"), Preco.de(BigDecimal.TEN),
                Quantidade.de(5), Categoria.OUTROS);
        servico.criar(NomeProduto.de("Produto 2"), Preco.de(new BigDecimal("20")),
                Quantidade.de(3), Categoria.ALIMENTOS);

        List<Produto> produtos = servico.listarTodos();

        assertEquals(2, produtos.size());
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHaProdutos() {
        List<Produto> produtos = servico.listarTodos();
        assertTrue(produtos.isEmpty());
    }

    @Test
    void deveAtualizarProduto() {
        Produto criado = servico.criar(
                NomeProduto.de("Notebook"),
                Preco.de(new BigDecimal("2500.00")),
                Quantidade.de(10),
                Categoria.ELETRONICOS
        );

        Produto atualizado = servico.atualizar(
                criado.getId(),
                NomeProduto.de("Laptop"),
                Preco.de(new BigDecimal("2800.00")),
                Quantidade.de(8),
                Categoria.ELETRONICOS
        );

        assertEquals("Laptop", atualizado.getNome().getValor());
        assertEquals(new BigDecimal("2800.00"), atualizado.getPreco().getValor());
        assertEquals(8, atualizado.getQuantidade().getValor());
    }

    @Test
    void deveLancarExcecaoAoAtualizarProdutoInexistente() {
        ProdutoId idInexistente = ProdutoId.gerar();

        assertThrows(ProdutoNaoEncontradoException.class, () ->
                servico.atualizar(idInexistente, NomeProduto.de("Teste"),
                        Preco.de(BigDecimal.TEN), Quantidade.de(1), Categoria.OUTROS)
        );
    }

    @Test
    void deveDeletarProduto() {
        Produto criado = servico.criar(
                NomeProduto.de("Produto Temporário"),
                Preco.de(BigDecimal.ONE),
                Quantidade.de(1),
                Categoria.OUTROS
        );

        servico.deletar(criado.getId());

        assertFalse(servico.existe(criado.getId()));
        assertThrows(ProdutoNaoEncontradoException.class, () -> servico.buscar(criado.getId()));
    }

    @Test
    void deveLancarExcecaoAoDeletarProdutoInexistente() {
        ProdutoId idInexistente = ProdutoId.gerar();
        assertThrows(ProdutoNaoEncontradoException.class, () -> servico.deletar(idInexistente));
    }

    @Test
    void deveContarProdutos() {
        assertEquals(0, servico.contarProdutos());

        servico.criar(NomeProduto.de("Produto 1"), Preco.de(BigDecimal.TEN),
                Quantidade.de(1), Categoria.OUTROS);
        assertEquals(1, servico.contarProdutos());

        servico.criar(NomeProduto.de("Produto 2"), Preco.de(BigDecimal.TEN),
                Quantidade.de(1), Categoria.OUTROS);
        assertEquals(2, servico.contarProdutos());
    }

    @Test
    void deveVerificarExistenciaDeProduto() {
        Produto criado = servico.criar(
                NomeProduto.de("Teste"),
                Preco.de(BigDecimal.TEN),
                Quantidade.de(1),
                Categoria.OUTROS
        );

        assertTrue(servico.existe(criado.getId()));
        assertFalse(servico.existe(ProdutoId.gerar()));
    }

    @Property
    void produtoCriadoDeveSerBuscavel(@ForAll("nomesProduto") String nome,
                                       @ForAll("precos") BigDecimal preco,
                                       @ForAll("quantidades") int quantidade,
                                       @ForAll("categorias") Categoria categoria) {
        ProdutoServico servicoLocal = new ProdutoServico(new ProdutoRepositorioMemoria());

        Produto criado = servicoLocal.criar(
                NomeProduto.de(nome),
                Preco.de(preco),
                Quantidade.de(quantidade),
                categoria
        );

        Produto encontrado = servicoLocal.buscar(criado.getId());
        assertEquals(criado.getId(), encontrado.getId());
    }

    @Property
    void atualizacaoDeveMudarDadosMantendoId(@ForAll("produtos") Produto produto) {
        ProdutoServico servicoLocal = new ProdutoServico(new ProdutoRepositorioMemoria());

        Produto criado = servicoLocal.criar(
                produto.getNome(),
                produto.getPreco(),
                produto.getQuantidade(),
                produto.getCategoria()
        );

        Produto atualizado = servicoLocal.atualizar(
                criado.getId(),
                NomeProduto.de("Novo Nome Teste"),
                Preco.de(new BigDecimal("999.99")),
                Quantidade.de(100),
                Categoria.OUTROS
        );

        assertEquals(criado.getId(), atualizado.getId());
        assertNotEquals(produto.getNome(), atualizado.getNome());
    }

    @Property
    void deletarDeveTornarProdutoInexistente(@ForAll("produtos") Produto produto) {
        ProdutoServico servicoLocal = new ProdutoServico(new ProdutoRepositorioMemoria());

        Produto criado = servicoLocal.criar(
                produto.getNome(),
                produto.getPreco(),
                produto.getQuantidade(),
                produto.getCategoria()
        );

        assertTrue(servicoLocal.existe(criado.getId()));
        servicoLocal.deletar(criado.getId());
        assertFalse(servicoLocal.existe(criado.getId()));
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