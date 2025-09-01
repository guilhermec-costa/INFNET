package src.service;

import java.util.Optional;

import src.model.Cliente;
import src.repository.IClienteRepository;

public class AutenticacaoService {
    private IClienteRepository clienteRepository;

    public AutenticacaoService(IClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Optional<Cliente> login(String email, String senha) {
        Optional<Cliente> clienteOpt = clienteRepository.buscarPorEmail(email);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            if (cliente.getSenha().equals(senha)) {
                return Optional.of(cliente);
            }
        }
        return Optional.empty();
    }
}