package com.sistema.domain;

import java.util.Objects;

public final class Produto {
    private final ProdutoId id;
    private final NomeProduto nome;
    private final Preco preco;
    private final Quantidade quantidade;
    private final Categoria categoria;

    private Produto(ProdutoId id, NomeProduto nome, Preco preco, Quantidade quantidade, Categoria categoria) {
        if (id == null) {
            throw new IllegalArgumentException("ID do produto não pode ser nulo");
        }
        if (nome == null) {
            throw new IllegalArgumentException("Nome do produto não pode ser nulo");
        }
        if (preco == null) {
            throw new IllegalArgumentException("Preço do produto não pode ser nulo");
        }
        if (quantidade == null) {
            throw new IllegalArgumentException("Quantidade do produto não pode ser nula");
        }
        if (categoria == null) {
            throw new IllegalArgumentException("Categoria do produto não pode ser nula");
        }

        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.categoria = categoria;
    }

    public static Produto criar(NomeProduto nome, Preco preco, Quantidade quantidade, Categoria categoria) {
        return new Produto(ProdutoId.gerar(), nome, preco, quantidade, categoria);
    }

    public static Produto reconstituir(ProdutoId id, NomeProduto nome, Preco preco, Quantidade quantidade, Categoria categoria) {
        return new Produto(id, nome, preco, quantidade, categoria);
    }

    public ProdutoId getId() {
        return id;
    }

    public NomeProduto getNome() {
        return nome;
    }

    public Preco getPreco() {
        return preco;
    }

    public Quantidade getQuantidade() {
        return quantidade;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Produto atualizarNome(NomeProduto novoNome) {
        return new Produto(this.id, novoNome, this.preco, this.quantidade, this.categoria);
    }

    public Produto atualizarPreco(Preco novoPreco) {
        return new Produto(this.id, this.nome, novoPreco, this.quantidade, this.categoria);
    }

    public Produto atualizarQuantidade(Quantidade novaQuantidade) {
        return new Produto(this.id, this.nome, this.preco, novaQuantidade, this.categoria);
    }

    public Produto atualizarCategoria(Categoria novaCategoria) {
        return new Produto(this.id, this.nome, this.preco, this.quantidade, novaCategoria);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return Objects.equals(id, produto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Produto[id=%s, nome=%s, preco=%s, quantidade=%s, categoria=%s]",
                id, nome, preco, quantidade, categoria);
    }
}