package com.infnet.TP5.src.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import com.infnet.TP5.src.exception.BusinessException;
import com.infnet.TP5.src.exception.ResourceNotFoundException;
import com.infnet.TP5.src.exception.ValidationException;
import com.infnet.TP5.src.model.Cliente;
import com.infnet.TP5.src.model.Pedido;
import com.infnet.TP5.src.model.Produto;
import com.infnet.TP5.src.repository.IProdutoRepository;
import com.infnet.TP5.src.service.AutenticacaoService;
import com.infnet.TP5.src.service.PedidoService;

import java.util.List;

public class PedidoController {
    
    private final PedidoService pedidoService;
    private final AutenticacaoService authService;
    private final IProdutoRepository produtoRepository;
    
    public PedidoController(PedidoService pedidoService, AutenticacaoService authService, IProdutoRepository produtoRepo) {
        this.pedidoService = pedidoService;
        this.authService = authService;
        this.produtoRepository = produtoRepo;
    }
    
    private Cliente getClienteAutenticado(Context ctx) {
        String clienteIdHeader = ctx.header("X-Cliente-ID");
        
        if (clienteIdHeader == null || clienteIdHeader.trim().isEmpty()) {
            ctx.status(HttpStatus.UNAUTHORIZED);
            ctx.json(new ErrorResponse("Header X-Cliente-ID é obrigatório"));
            throw new ValidationException("Header X-Cliente-ID não encontrado");
        }
        
        try {
            long clienteId = Long.parseLong(clienteIdHeader);
            return authService.buscarPorIdObrigatorio(clienteId);
        } catch (NumberFormatException e) {
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.json(new ErrorResponse("X-Cliente-ID deve ser um número válido"));
            throw new ValidationException("X-Cliente-ID inválido");
        }
    }

    public void getTodosProdutos(Context ctx) {
        try {
            List<Produto> produtos = produtoRepository.listarTodos();
            ctx.json(produtos);
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR);
            ctx.json(new ErrorResponse("Erro ao buscar produtos"));
        }
    }

    public void getHistoricoPedidos(Context ctx) {
        try {
            Cliente cliente = getClienteAutenticado(ctx);
            List<Pedido> pedidos = pedidoService.listarPedidosPorCliente(cliente);
            ctx.json(pedidos);
            
        } catch (ValidationException e) {
            return;
        } catch (ResourceNotFoundException e) {
            ctx.status(HttpStatus.UNAUTHORIZED);
            ctx.json(new ErrorResponse("Cliente não encontrado"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR);
            ctx.json(new ErrorResponse("Erro ao buscar histórico de pedidos"));
        }
    }

    public void criarPedido(Context ctx) {
        try {
            Cliente cliente = getClienteAutenticado(ctx);
            PedidoRequest req = ctx.bodyAsClass(PedidoRequest.class);
            
            if (req == null) {
                ctx.status(HttpStatus.BAD_REQUEST);
                ctx.json(new ErrorResponse("Dados do pedido são obrigatórios"));
                return;
            }
            
            Pedido novoPedido = pedidoService.processarCriacaoPedido(cliente, req);
            
            ctx.status(HttpStatus.CREATED);
            ctx.json(new PedidoResponse(novoPedido));
            
        } catch (ValidationException e) {
            if (!e.getMessage().contains("X-Cliente-ID")) {
                ctx.status(HttpStatus.BAD_REQUEST);
                ctx.json(new ErrorResponse("Erro de validação: " + e.getMessage()));
            }
        } catch (BusinessException e) {
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.json(new ErrorResponse(e.getMessage()));
        } catch (ResourceNotFoundException e) {
            if (e.getMessage().contains("Cliente")) {
                ctx.status(HttpStatus.UNAUTHORIZED);
                ctx.json(new ErrorResponse("Cliente não encontrado"));
            } else {
                ctx.status(HttpStatus.BAD_REQUEST);
                ctx.json(new ErrorResponse(e.getMessage()));
            }
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR);
            ctx.json(new ErrorResponse("Erro interno ao criar pedido"));
            System.err.println("Erro ao criar pedido: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void cancelarPedido(Context ctx) {
        try {
            Cliente cliente = getClienteAutenticado(ctx);
            
            String pedidoIdParam = ctx.pathParam("id");
            if (pedidoIdParam == null || pedidoIdParam.trim().isEmpty()) {
                ctx.status(HttpStatus.BAD_REQUEST);
                ctx.json(new ErrorResponse("ID do pedido é obrigatório"));
                return;
            }
            
            long pedidoId;
            try {
                pedidoId = Long.parseLong(pedidoIdParam);
            } catch (NumberFormatException e) {
                ctx.status(HttpStatus.BAD_REQUEST);
                ctx.json(new ErrorResponse("ID do pedido deve ser um número válido"));
                return;
            }
            
            boolean sucesso = pedidoService.cancelarPedido(cliente, pedidoId);
            
            if (sucesso) {
                ctx.status(HttpStatus.OK);
                ctx.json(new SuccessResponse("Pedido cancelado com sucesso"));
            } else {
                ctx.status(HttpStatus.BAD_REQUEST);
                ctx.json(new ErrorResponse("Não foi possível cancelar o pedido"));
            }
            
        } catch (ValidationException e) {
            if (!e.getMessage().contains("X-Cliente-ID")) {
                ctx.status(HttpStatus.BAD_REQUEST);
                ctx.json(new ErrorResponse("Erro de validação: " + e.getMessage()));
            }
        } catch (BusinessException e) {
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.json(new ErrorResponse(e.getMessage()));
        } catch (ResourceNotFoundException e) {
            if (e.getMessage().contains("Cliente")) {
                ctx.status(HttpStatus.UNAUTHORIZED);
                ctx.json(new ErrorResponse("Cliente não encontrado"));
            } else {
                ctx.status(HttpStatus.NOT_FOUND);
                ctx.json(new ErrorResponse(e.getMessage()));
            }
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR);
            ctx.json(new ErrorResponse("Erro interno ao cancelar pedido"));
            System.err.println("Erro ao cancelar pedido: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static class PedidoRequest {
        public List<ItemPedidoRequest> carrinho;
        public long enderecoId;
        public long formaPagamentoId;
        
        public PedidoRequest() {}
    }
    
    public static class ItemPedidoRequest {
        public long produtoId;
        public int quantidade;
        
        public ItemPedidoRequest() {}
    }

    public static class PedidoResponse {
        public final long id;
        public final long clienteId;
        public final String status;
        public final String valorTotal;
        public final String dataPedido;
        public final int totalItens;
        
        public PedidoResponse(Pedido pedido) {
            this.id = pedido.getId();
            this.clienteId = pedido.getCliente().getId();
            this.status = pedido.getStatus().name();
            this.valorTotal = pedido.getValorTotal().toString();
            this.dataPedido = pedido.getDataPedido().toString();
            this.totalItens = pedido.getItens().size();
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
    
    public static class SuccessResponse {
        public final String mensagem;
        public final long timestamp;
        
        public SuccessResponse(String mensagem) {
            this.mensagem = mensagem;
            this.timestamp = System.currentTimeMillis();
        }
    }
}