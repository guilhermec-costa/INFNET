package com.infnet.TP5.src.controller;

import io.javalin.http.Context;
import com.infnet.TP5.src.model.Cliente;
import com.infnet.TP5.src.model.Endereco;
import com.infnet.TP5.src.model.FormaPagamento;
import com.infnet.TP5.src.service.AutenticacaoService;
import com.infnet.TP5.src.service.ClienteService;
import java.util.Optional;

public class ClienteController {
    private AutenticacaoService authService;
    private ClienteService clienteService;

    public ClienteController(AutenticacaoService authService, ClienteService clienteService) {
        this.authService = authService;
        this.clienteService = clienteService;
    }

    public static class LoginRequest {
        public String email;
        public String senha;
    }

    public void login(Context ctx) {
        LoginRequest loginReq = ctx.bodyAsClass(LoginRequest.class);
        Optional<Cliente> clienteOpt = authService.login(loginReq.email, loginReq.senha);
        if (clienteOpt.isPresent()) {
            ctx.json(clienteOpt.get());
        } else {
            ctx.status(401).result("Email ou senha inválidos.");
        }
    }
    
    private Optional<Cliente> getClienteAutenticado(Context ctx) {
        String clienteIdHeader = ctx.header("X-Cliente-ID");
        if (clienteIdHeader == null) {
            ctx.status(401).result("Header X-Cliente-ID não encontrado.");
            return Optional.empty();
        }
        return authService.buscarPorId(Long.parseLong(clienteIdHeader));
    }
    
    public void getDadosCliente(Context ctx) {
        getClienteAutenticado(ctx).ifPresent(ctx::json);
    }
    
    public void atualizarDados(Context ctx) {
        Optional<Cliente> clienteOpt = getClienteAutenticado(ctx);
        if (clienteOpt.isPresent()) {
            Cliente dadosAtualizados = ctx.bodyAsClass(Cliente.class);
            clienteService.atualizarDados(clienteOpt.get(), dadosAtualizados.getNome(), dadosAtualizados.getEmail());
            ctx.status(200).result("Dados atualizados com sucesso.");
        }
    }

    public void adicionarEndereco(Context ctx) {
        Optional<Cliente> clienteOpt = getClienteAutenticado(ctx);
        if (clienteOpt.isPresent()) {
            Endereco novoEndereco = ctx.bodyAsClass(Endereco.class);
            clienteService.adicionarEndereco(clienteOpt.get(), novoEndereco.getLogradouro(), novoEndereco.getNumero(), novoEndereco.getCep(), novoEndereco.getCidade());
            ctx.status(201).result("Endereço adicionado com sucesso.");
        }
    }
    
    public void adicionarFormaPagamento(Context ctx) {
        Optional<Cliente> clienteOpt = getClienteAutenticado(ctx);
        if (clienteOpt.isPresent()) {
            FormaPagamento novaForma = ctx.bodyAsClass(FormaPagamento.class);
            clienteService.adicionarFormaPagamento(clienteOpt.get(), novaForma.getTipo(), novaForma.getDescricao());
            ctx.status(201).result("Forma de pagamento adicionada com sucesso.");
        }
    }
}