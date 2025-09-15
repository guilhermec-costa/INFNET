package com.infnet.TP5.src.model;

public class FormaPagamento {
    private long id;
    private String tipo;
    private String descricao;

    public FormaPagamento() {}

    public FormaPagamento(long id, String tipo, String descricao) {
        this.id = id;
        this.tipo = tipo;
        this.descricao = descricao;
    }
    
    @Override
    public String toString() {
        return String.format("%s - %s", tipo, descricao);
    }

    public long getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDescricao() {
        return descricao;
    }
}
