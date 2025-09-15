package com.infnet.TP5.src.repository.memory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.infnet.TP5.src.model.Cliente;
import com.infnet.TP5.src.model.Endereco;
import com.infnet.TP5.src.model.FormaPagamento;
import com.infnet.TP5.src.repository.IClienteRepository;

public class ClienteRepositoryInMemory implements IClienteRepository {
    private List<Cliente> clientes = new ArrayList<>();

    public ClienteRepositoryInMemory() {
        Cliente cliente1 = new Cliente(1, "Guilherme China", "gui@email.com", "123");
        cliente1.adicionarEndereco(new Endereco(1, "Rua das Flores", "123", "12345-000", "São Paulo"));
        cliente1.adicionarFormaPagamento(new FormaPagamento(1, "Cartão de Crédito", "**** **** **** 1234"));
        
        Cliente cliente2 = new Cliente(2, "Maria Silva", "maria@email.com", "abc");
        cliente2.adicionarEndereco(new Endereco(2, "Avenida Principal", "456", "54321-000", "Rio de Janeiro"));
        
        clientes.add(cliente1);
        clientes.add(cliente2);
    }

    public Optional<Cliente> buscarPorEmail(String email) {
        return clientes.stream().filter(c -> c.getEmail().equalsIgnoreCase(email)).findFirst();
    }

    @Override
    public Optional<Cliente> buscarPorId(long clientId) {
        return clientes.stream().filter(c -> c.getId() == clientId).findFirst();
    }
}