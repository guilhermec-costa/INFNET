package com.infnet.TP5.src.model;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private long id;
    private String nome;
    private String email;
    private String senha;
    private List<Endereco> enderecos = new ArrayList<>();
    private List<FormaPagamento> formasPagamento = new ArrayList<>();

    public Cliente() {}

    public Cliente(long id, String nome, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public long getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getSenha() { return senha; }
    public List<Endereco> getEnderecos() { return enderecos; }
    public List<FormaPagamento> getFormasPagamento() { return formasPagamento; }

    public void setNome(String nome) { this.nome = nome; }
    public void setEmail(String email) { this.email = email; }

    public void adicionarEndereco(Endereco endereco) {
        this.enderecos.add(endereco);
    }

    public void adicionarFormaPagamento(FormaPagamento formaPagamento) {
        this.formasPagamento.add(formaPagamento);
    }
}