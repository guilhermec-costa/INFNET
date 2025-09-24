package com.infnet.AT;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.infnet.AT.models.Mensalista;
import com.infnet.AT.services.MensalistaService;

import io.javalin.Javalin;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class App {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final MensalistaService mensalistaService = new MensalistaService();

    public static void main(String[] args) {
        Javalin app = createApp();
        app.start(7000);
    }

    public static Javalin createApp() {
        Javalin app = Javalin.create(config -> {
            config.http.defaultContentType = "application/json";
        });

        app.get("/hello", ctx -> {
            ctx.result("Hello, Javalin!");
        });

        app.get("/status", ctx -> {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "ok");
            response.put("timestamp", Instant.now().toString());
            ctx.json(response);
        });

        app.post("/echo", ctx -> {
            try {
                String body = ctx.body();
                Map<String, Object> request = objectMapper.readValue(body, Map.class);
                
                if (request.containsKey("mensagem")) {
                    ctx.json(request);
                } else {
                    ctx.status(400).json(Map.of("error", "Chave 'mensagem' não encontrada"));
                }
            } catch (Exception e) {
                ctx.status(400).json(Map.of("error", "JSON inválido"));
            }
        });

        app.get("/saudacao/{nome}", ctx -> {
            String nome = ctx.pathParam("nome");
            Map<String, String> response = new HashMap<>();
            response.put("mensagem", "Olá, " + nome + "!");
            ctx.json(response);
        });


        app.get("/mensalistas", ctx -> {
            ctx.json(mensalistaService.listarTodos());
        });

        app.get("/mensalistas/{matricula}", ctx -> {
            String matricula = ctx.pathParam("matricula");
            Optional<Mensalista> mensalista = mensalistaService.buscarPorMatricula(matricula);
            
            if (mensalista.isPresent()) {
                ctx.json(mensalista.get());
            } else {
                ctx.status(404).json(Map.of("error", "Mensalista não encontrado"));
            }
        });

        app.post("/mensalistas", ctx -> {
            try {
                Mensalista mensalista = ctx.bodyAsClass(Mensalista.class);
                Mensalista criado = mensalistaService.criar(mensalista);
                ctx.status(201).json(criado);
            } catch (IllegalArgumentException e) {
                ctx.status(400).json(Map.of("error", e.getMessage()));
            } catch (Exception e) {
                ctx.status(400).json(Map.of("error", "Dados inválidos"));
            }
        });

        return app;
    }
}