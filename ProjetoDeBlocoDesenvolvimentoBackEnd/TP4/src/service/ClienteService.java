package src.service;

import src.model.Cliente;
import src.model.Endereco;
import src.model.FormaPagamento;

public class ClienteService {
    private static long proximoEnderecoId = 10;
    private static long proximoPagamentoId = 10;
    
    public void atualizarDados(Cliente cliente, String novoNome, String novoEmail) {
        if (novoNome != null && !novoNome.isEmpty()) {
            cliente.setNome(novoNome);
        }
        if (novoEmail != null && !novoEmail.isEmpty()) {
            cliente.setEmail(novoEmail);
        }
    }

    public void adicionarEndereco(Cliente cliente, String logradouro, String numero, String cep, String cidade) {
        Endereco novoEndereco = new Endereco(proximoEnderecoId++, logradouro, numero, cep, cidade);
        cliente.adicionarEndereco(novoEndereco);
    }
    
    public void adicionarFormaPagamento(Cliente cliente, String tipo, String descricao) {
        FormaPagamento novaForma = new FormaPagamento(proximoPagamentoId++, tipo, descricao);
        cliente.adicionarFormaPagamento(novaForma);
    }
}