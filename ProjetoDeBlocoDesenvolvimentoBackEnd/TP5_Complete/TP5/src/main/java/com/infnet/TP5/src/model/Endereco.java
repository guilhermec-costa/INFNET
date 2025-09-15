package com.infnet.TP5.src.model;

public class Endereco {
    private long id;
    private String logradouro;
    private String numero;
    private String cep;
    private String cidade;

    public Endereco() {}

    public Endereco(long id, String logradouro, String numero, String cep, String cidade) {
        this.id = id;
        this.logradouro = logradouro;
        this.numero = numero;
        this.cep = cep;
        this.cidade = cidade;
    }

    @Override
    public String toString() {
        return String.format("%s, %s - %s, CEP: %s", logradouro, numero, cidade, cep);
    }

    public long getId() {
        return id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public String getCep() {
        return cep;
    }

    public String getCidade() {
        return cidade;
    }
}
