package com.infnet.AT;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.infnet.AT.models.Mensalista;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import okhttp3.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {
    private static Javalin app;
    private static final int PORT = 7001;
    private static final String BASE_URL = "http://localhost:" + PORT;
    private static final OkHttpClient client = new OkHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    static void setUp() {
        app = App.createApp();
        app.start(PORT);
    }

    @AfterAll
    static void tearDown() {
        app.stop();
    }

    @Test
    void testHelloEndpoint() throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL + "/hello")
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            assertEquals(200, response.code());
            assertEquals("Hello, Javalin!", response.body().string());
        }
    }

    @Test
    void testCreateMensalista() throws IOException {
        Mensalista novoMensalista = new Mensalista("999", "Teste Usuario", "teste@email.com", "(11) 77777-7777");
        
        String json = objectMapper.writeValueAsString(novoMensalista);
        RequestBody body = RequestBody.create(json, MediaType.get("application/json"));
        
        Request request = new Request.Builder()
                .url(BASE_URL + "/mensalistas")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            assertEquals(201, response.code());
            
            String responseBody = response.body().string();
            Mensalista created = objectMapper.readValue(responseBody, Mensalista.class);
            
            assertEquals("999", created.getMatricula());
            assertEquals("Teste Usuario", created.getNome());
        }
    }

    @Test
    void testGetMensalistaPorMatricula() throws IOException {
        Mensalista novoMensalista = new Mensalista("888", "Busca Usuario", "busca@email.com", "(11) 66666-6666");
        
        String json = objectMapper.writeValueAsString(novoMensalista);
        RequestBody body = RequestBody.create(json, MediaType.get("application/json"));
        
        Request createRequest = new Request.Builder()
                .url(BASE_URL + "/mensalistas")
                .post(body)
                .build();

        try (Response createResponse = client.newCall(createRequest).execute()) {
            assertEquals(201, createResponse.code());
        }

        Request getRequest = new Request.Builder()
                .url(BASE_URL + "/mensalistas/888")
                .get()
                .build();

        try (Response response = client.newCall(getRequest).execute()) {
            assertEquals(200, response.code());
            
            String responseBody = response.body().string();
            Mensalista found = objectMapper.readValue(responseBody, Mensalista.class);
            
            assertEquals("888", found.getMatricula());
            assertEquals("Busca Usuario", found.getNome());
        }
    }

    @Test
    void testListarTodosMensalistas() throws IOException {
        Mensalista novoMensalista = new Mensalista("777", "Lista Usuario", "lista@email.com", "(11) 55555-5555");
        
        String json = objectMapper.writeValueAsString(novoMensalista);
        RequestBody body = RequestBody.create(json, MediaType.get("application/json"));
        
        Request createRequest = new Request.Builder()
                .url(BASE_URL + "/mensalistas")
                .post(body)
                .build();

        try (Response createResponse = client.newCall(createRequest).execute()) {
            assertEquals(201, createResponse.code());
        }

        Request listRequest = new Request.Builder()
                .url(BASE_URL + "/mensalistas")
                .get()
                .build();

        try (Response response = client.newCall(listRequest).execute()) {
            assertEquals(200, response.code());
            
            String responseBody = response.body().string();
            List<Mensalista> mensalistas = objectMapper.readValue(responseBody, new TypeReference<List<Mensalista>>() {});
            
            assertFalse(mensalistas.isEmpty());
            assertTrue(mensalistas.size() >= 1);
        }
    }

    @Test
    void testStatusEndpoint() throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL + "/status")
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            assertEquals(200, response.code());
            
            String responseBody = response.body().string();
            Map<String, Object> status = objectMapper.readValue(responseBody, new TypeReference<Map<String, Object>>() {});
            
            assertEquals("ok", status.get("status"));
            assertNotNull(status.get("timestamp"));
        }
    }
}