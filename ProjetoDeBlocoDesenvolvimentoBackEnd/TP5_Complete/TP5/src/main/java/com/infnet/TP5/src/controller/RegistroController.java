package com.infnet.TP5.src.controller;

import com.infnet.TP5.src.exception.BusinessException;
import com.infnet.TP5.src.exception.ValidationException;
import com.infnet.TP5.src.model.Cliente;
import com.infnet.TP5.src.service.ClienteService;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

public class RegistroController {
    
    private final ClienteService clienteService;
    
    public RegistroController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }
    
    public void registrar(Context ctx) {
        try {
            var request = ctx.bodyAsClass(RegistroRequest.class);
            
            Cliente novoCliente = clienteService.criarCliente(
                request.nome, 
                request.email, 
                request.senha
            );
            
            var response = new RegistroResponse(
                novoCliente.getId(),
                novoCliente.getNome(),
                novoCliente.getEmail()
            );
            
            ctx.status(HttpStatus.CREATED);
            ctx.json(response);
            
        } catch (ValidationException e) {
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.json(new ErrorResponse("Erro de validação: " + e.getMessage()));
            
        } catch (BusinessException e) {
            ctx.status(HttpStatus.CONFLICT);
            ctx.json(new ErrorResponse(e.getMessage()));
            
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR);
            ctx.json(new ErrorResponse("Erro interno do servidor"));
        }
    }
    
    public static class RegistroRequest {
        public String nome;
        public String email;
        public String senha;
    }
    
    public static class RegistroResponse {
        public final long id;
        public final String nome;
        public final String email;
        
        public RegistroResponse(long id, String nome, String email) {
            this.id = id;
            this.nome = nome;
            this.email = email;
        }
    }
    
    public static class ErrorResponse {
        public final String erro;
        public final long timestamp;
        
        public ErrorResponse(String erro) {
            this.erro = erro;
            this.timestamp = System.currentTimeMillis();
        }
    }
}