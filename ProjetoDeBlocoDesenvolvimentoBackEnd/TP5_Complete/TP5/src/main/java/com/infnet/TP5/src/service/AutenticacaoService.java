package com.infnet.TP5.src.service;

import java.util.Optional;
import com.infnet.TP5.src.exception.ResourceNotFoundException;
import com.infnet.TP5.src.model.Cliente;
import com.infnet.TP5.src.repository.IClienteRepository;
import com.infnet.TP5.src.util.ValidationUtil;

public class AutenticacaoService {
    
    private final IClienteRepository clienteRepository;
    
    public AutenticacaoService(IClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }
    
    public Optional<Cliente> login(String email, String senha) {
        ValidationUtil.validateEmail(email);
        ValidationUtil.validateNotEmpty(senha, "Senha");
        
        Optional<Cliente> clienteOpt = clienteRepository.buscarPorEmail(email);
        
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            if (cliente.getSenha().equals(senha)) {
                return Optional.of(cliente);
            }
        }
        
        return Optional.empty();
    }
    
    public Cliente buscarPorIdObrigatorio(long clienteId) {
        ValidationUtil.validatePositive(clienteId, "ID do cliente");
        
        return clienteRepository.buscarPorId(clienteId)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + clienteId));
    }
    
    public Optional<Cliente> buscarPorId(long clienteId) {
        ValidationUtil.validatePositive(clienteId, "ID do cliente");
        return clienteRepository.buscarPorId(clienteId);
    }
}
