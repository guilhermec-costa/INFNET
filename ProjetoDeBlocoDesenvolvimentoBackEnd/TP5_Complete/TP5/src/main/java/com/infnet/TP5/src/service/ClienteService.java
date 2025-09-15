package com.infnet.TP5.src.service;

import com.infnet.TP5.src.exception.BusinessException;
import com.infnet.TP5.src.exception.ValidationException;
import com.infnet.TP5.src.model.Cliente;
import com.infnet.TP5.src.model.Endereco;
import com.infnet.TP5.src.model.FormaPagamento;
import com.infnet.TP5.src.repository.IClienteRepository;
import com.infnet.TP5.src.util.ValidationUtil;

public class ClienteService {
    
    private final IClienteRepository clienteRepository;
    private static long proximoClienteId = 1000;
    private static long proximoEnderecoId = 10;
    private static long proximoPagamentoId = 10;
    
    public ClienteService(IClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }
    
    public Cliente criarCliente(String nome, String email, String senha) {
        ValidationUtil.validateNotEmpty(nome, "Nome");
        ValidationUtil.validateEmail(email);
        ValidationUtil.validateNotEmpty(senha, "Senha");
        
        if (clienteRepository.buscarPorEmail(email).isPresent()) {
            throw new BusinessException("Email já está em uso");
        }
        
        if (senha.length() < 6) {
            throw new ValidationException("Senha deve ter pelo menos 6 caracteres");
        }
        
        Cliente novoCliente = new Cliente(proximoClienteId++, nome, email, senha);
        
        if (clienteRepository instanceof com.infnet.TP5.src.repository.csv.ClienteRepositoryCSV) {
            ((com.infnet.TP5.src.repository.csv.ClienteRepositoryCSV) clienteRepository).salvar(novoCliente);
        }
        
        return novoCliente;
    }
    
    public void atualizarDados(Cliente cliente, String novoNome, String novoEmail) {
        ValidationUtil.validateNotNull(cliente, "Cliente");
        
        if (novoNome != null && !novoNome.trim().isEmpty()) {
            cliente.setNome(novoNome.trim());
        }
        
        if (novoEmail != null && !novoEmail.trim().isEmpty()) {
            ValidationUtil.validateEmail(novoEmail);
            
            var clienteExistente = clienteRepository.buscarPorEmail(novoEmail);
            if (clienteExistente.isPresent() && clienteExistente.get().getId() != cliente.getId()) {
                throw new BusinessException("Email já está em uso por outro cliente");
            }
            
            cliente.setEmail(novoEmail.trim());
        }
        
        if (clienteRepository instanceof com.infnet.TP5.src.repository.csv.ClienteRepositoryCSV) {
            ((com.infnet.TP5.src.repository.csv.ClienteRepositoryCSV) clienteRepository).salvar(cliente);
        }
    }
    
    public void adicionarEndereco(Cliente cliente, String logradouro, String numero, String cep, String cidade) {
        ValidationUtil.validateNotNull(cliente, "Cliente");
        ValidationUtil.validateNotEmpty(logradouro, "Logradouro");
        ValidationUtil.validateNotEmpty(numero, "Número");
        ValidationUtil.validateNotEmpty(cep, "CEP");
        ValidationUtil.validateNotEmpty(cidade, "Cidade");
        
        String cepLimpo = cep.replaceAll("[^0-9-]", "");
        if (cepLimpo.length() < 8) {
            throw new ValidationException("CEP deve ter formato válido");
        }
        
        Endereco novoEndereco = new Endereco(proximoEnderecoId++, 
            logradouro.trim(), numero.trim(), cepLimpo, cidade.trim());
        cliente.adicionarEndereco(novoEndereco);
        
        if (clienteRepository instanceof com.infnet.TP5.src.repository.csv.ClienteRepositoryCSV) {
            ((com.infnet.TP5.src.repository.csv.ClienteRepositoryCSV) clienteRepository).salvar(cliente);
        }
    }
    
    public void adicionarFormaPagamento(Cliente cliente, String tipo, String descricao) {
        ValidationUtil.validateNotNull(cliente, "Cliente");
        ValidationUtil.validateNotEmpty(tipo, "Tipo");
        ValidationUtil.validateNotEmpty(descricao, "Descrição");
        
        String tipoUpper = tipo.trim().toUpperCase();
        if (!tipoUpper.matches("CARTAO_CREDITO|CARTAO_DEBITO|PIX|BOLETO")) {
            throw new ValidationException("Tipo de pagamento deve ser: CARTAO_CREDITO, CARTAO_DEBITO, PIX ou BOLETO");
        }
        
        FormaPagamento novaForma = new FormaPagamento(proximoPagamentoId++, 
            tipoUpper, descricao.trim());
        cliente.adicionarFormaPagamento(novaForma);
        
        if (clienteRepository instanceof com.infnet.TP5.src.repository.csv.ClienteRepositoryCSV) {
            ((com.infnet.TP5.src.repository.csv.ClienteRepositoryCSV) clienteRepository).salvar(cliente);
        }
    }
    
    public long getProximoClienteId() {
        return proximoClienteId++;
    }
}