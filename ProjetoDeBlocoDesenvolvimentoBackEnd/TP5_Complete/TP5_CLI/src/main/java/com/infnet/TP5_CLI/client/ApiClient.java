package com.infnet.TP5_CLI.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ApiClient {
    
    private static final String BASE_URL = "http://localhost:7070";
    private final OkHttpClient client;
    private final ObjectMapper mapper;
    
    public ApiClient() {
        this.client = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();
            
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
    }
    
    private ApiResponse makeRequest(Request request) {
        try (Response response = client.newCall(request).execute()) {
            String body = response.body() != null ? response.body().string() : "";
            
            JsonNode jsonNode = null;
            if (!body.isEmpty()) {
                try {
                    jsonNode = mapper.readTree(body);
                } catch (Exception e) {
                }
            }
            
            return new ApiResponse(response.code(), body, jsonNode, response.isSuccessful());
        } catch (IOException e) {
            return new ApiResponse(0, "Erro de conexão: " + e.getMessage(), null, false);
        }
    }
    
    public ApiResponse post(String endpoint, String jsonBody) {
        RequestBody body = RequestBody.create(jsonBody, MediaType.get("application/json"));
        Request request = new Request.Builder()
            .url(BASE_URL + endpoint)
            .post(body)
            .build();
        return makeRequest(request);
    }
    
    public ApiResponse get(String endpoint) {
        Request request = new Request.Builder()
            .url(BASE_URL + endpoint)
            .build();
        return makeRequest(request);
    }
    
    public ApiResponse get(String endpoint, String clienteId) {
        Request request = new Request.Builder()
            .url(BASE_URL + endpoint)
            .header("X-Cliente-ID", clienteId)
            .build();
        return makeRequest(request);
    }
    
    public ApiResponse put(String endpoint, String jsonBody, String clienteId) {
        RequestBody body = RequestBody.create(jsonBody, MediaType.get("application/json"));
        Request request = new Request.Builder()
            .url(BASE_URL + endpoint)
            .put(body)
            .header("X-Cliente-ID", clienteId)
            .build();
        return makeRequest(request);
    }
    
    public ApiResponse post(String endpoint, String jsonBody, String clienteId) {
        RequestBody body = RequestBody.create(jsonBody, MediaType.get("application/json"));
        Request request = new Request.Builder()
            .url(BASE_URL + endpoint)
            .post(body)
            .header("X-Cliente-ID", clienteId)
            .build();
        return makeRequest(request);
    }
    
    public static class ApiResponse {
        public final int statusCode;
        public final String body;
        public final JsonNode json;
        public final boolean success;
        
        public ApiResponse(int statusCode, String body, JsonNode json, boolean success) {
            this.statusCode = statusCode;
            this.body = body;
            this.json = json;
            this.success = success;
        }
        
        public boolean isSuccess() {
            return success && statusCode >= 200 && statusCode < 300;
        }
    }
}