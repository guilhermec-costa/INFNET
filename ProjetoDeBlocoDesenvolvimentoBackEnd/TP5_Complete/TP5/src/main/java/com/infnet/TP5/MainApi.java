package com.infnet.TP5;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.infnet.TP5.src.controller.ClienteController;
import com.infnet.TP5.src.controller.PedidoController;
import com.infnet.TP5.src.controller.RegistroController;
import com.infnet.TP5.src.exception.BusinessException;
import com.infnet.TP5.src.exception.ResourceNotFoundException;
import com.infnet.TP5.src.exception.ValidationException;
import com.infnet.TP5.src.repository.IClienteRepository;
import com.infnet.TP5.src.repository.IPedidoRepository;
import com.infnet.TP5.src.repository.IProdutoRepository;
import com.infnet.TP5.src.repository.csv.ClienteRepositoryCSV;
import com.infnet.TP5.src.repository.csv.PedidoRepositoryCSV;
import com.infnet.TP5.src.repository.csv.ProdutoRepositoryCSV;
import com.infnet.TP5.src.repository.memory.ClienteRepositoryInMemory;
import com.infnet.TP5.src.repository.memory.PedidoRepositoryInMemory;
import com.infnet.TP5.src.repository.memory.ProdutoRepositoryInMemory;
import com.infnet.TP5.src.service.AutenticacaoService;
import com.infnet.TP5.src.service.ClienteService;
import com.infnet.TP5.src.service.PedidoService;
import io.javalin.Javalin;
import io.javalin.http.HttpStatus;
import io.javalin.json.JavalinJackson;

public class MainApi {
    
    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(formatter));
        objectMapper.registerModule(javaTimeModule);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        
        IClienteRepository clienteRepo = new ClienteRepositoryInMemory();
        IProdutoRepository produtoRepo = new ProdutoRepositoryInMemory();
        IPedidoRepository pedidoRepo = new PedidoRepositoryInMemory();
        
        AutenticacaoService authService = new AutenticacaoService(clienteRepo);
        ClienteService clienteService = new ClienteService(clienteRepo);
        PedidoService pedidoService = new PedidoService(pedidoRepo, produtoRepo);
        
        RegistroController registroController = new RegistroController(clienteService);
        ClienteController clienteController = new ClienteController(authService, clienteService);
        PedidoController pedidoController = new PedidoController(pedidoService, authService, produtoRepo);
        
        Javalin app = Javalin.create(config -> {
            config.jsonMapper(new JavalinJackson(objectMapper));
            config.http.defaultContentType = "application/json";
            
            config.plugins.enableCors(cors -> {
                cors.add(it -> {
                    it.anyHost();
                    it.allowCredentials = false;
                });
            });
        });
        
        app.exception(ValidationException.class, (e, ctx) -> {
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.json(new RegistroController.ErrorResponse("Erro de validação: " + e.getMessage()));
        });
        
        app.exception(BusinessException.class, (e, ctx) -> {
            ctx.status(HttpStatus.CONFLICT);
            ctx.json(new RegistroController.ErrorResponse(e.getMessage()));
        });
        
        app.exception(ResourceNotFoundException.class, (e, ctx) -> {
            ctx.status(HttpStatus.NOT_FOUND);
            ctx.json(new RegistroController.ErrorResponse(e.getMessage()));
        });
        
        app.exception(Exception.class, (e, ctx) -> {
            System.err.println("Erro não tratado: " + e.getMessage());
            e.printStackTrace();
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR);
            ctx.json(new RegistroController.ErrorResponse("Erro interno do servidor"));
        });
        
        // Rotas públicas
        app.post("/registro", registroController::registrar);
        app.post("/login", clienteController::login);
        app.get("/produtos", pedidoController::getTodosProdutos);
        
        // Rotas autenticadas - clientes
        app.get("/clientes/me", clienteController::getDadosCliente);
        app.put("/clientes/me", clienteController::atualizarDados);
        app.post("/clientes/me/enderecos", clienteController::adicionarEndereco);
        app.post("/clientes/me/formaspagamento", clienteController::adicionarFormaPagamento);
        
        // Rotas autenticadas - pedidos
        app.get("/pedidos", pedidoController::getHistoricoPedidos);
        app.post("/pedidos", pedidoController::criarPedido);
        app.post("/pedidos/{id}/cancelar", pedidoController::cancelarPedido);
        
        // Rota para health check
        app.get("/health", ctx -> {
            ctx.json(new HealthResponse("OK", System.currentTimeMillis()));
        });
        
        app.start(7070);
        System.out.println("Servidor API rodando em http://localhost:7070");
        System.out.println("Health check: http://localhost:7070/health");
        System.out.println("Produtos: http://localhost:7070/produtos");
    }
    
    public static class HealthResponse {
        public final String status;
        public final long timestamp;
        
        public HealthResponse(String status, long timestamp) {
            this.status = status;
            this.timestamp = timestamp;
        }
    }
}