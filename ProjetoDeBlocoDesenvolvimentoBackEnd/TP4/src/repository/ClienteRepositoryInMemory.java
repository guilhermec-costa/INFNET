package src.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import src.model.Cliente;
import src.model.Endereco;
import src.model.FormaPagamento;

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
}