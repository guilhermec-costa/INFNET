package com.infnet.TP5.src.model;

import java.math.BigDecimal;

public class Produto {
    private long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private int estoque;

    public Produto() {}

    public Produto(long id, String nome, String descricao, BigDecimal preco, int estoque) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.estoque = estoque;
    }

    public long getId() { return id; }
    public String getNome() { return nome; }
    public BigDecimal getPreco() { return preco; }
    public int getEstoque() { return estoque; }
    public String getDescricao() { return descricao; }
    public void setEstoque(int estoque) { this.estoque = estoque; }

    @Override
    public String toString() {
        return String.format("ID: %d | Produto: %s | Preço: R$%.2f | Estoque: %d", id, nome, preco, estoque);
    }
}
