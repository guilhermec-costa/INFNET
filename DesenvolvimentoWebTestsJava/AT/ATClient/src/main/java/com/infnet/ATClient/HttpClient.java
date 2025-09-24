package com.infnet.ATClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpClient {
    private static final String BASE_URL = "http://localhost:7000";

    public static void criarMensalista() {
        try {
            System.out.println("=== Criando novo Mensalista ===");
            
            URL url = new URL(BASE_URL + "/mensalistas");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            
            String jsonData = "{"
                    + "\"matricula\": \"123\","
                    + "\"nome\": \"Cliente Teste\","
                    + "\"email\": \"cliente@email.com\","
                    + "\"telefone\": \"(11) 12345-6789\""
                    + "}";
            
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonData.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            
            int responseCode = connection.getResponseCode();
            System.out.println("Status Code: " + responseCode);
            
            BufferedReader reader;
            if (responseCode >= 200 && responseCode < 300) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }
            
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            
            System.out.println("Resposta: " + response.toString());
            connection.disconnect();
            
        } catch (IOException e) {
            System.err.println("Erro ao criar mensalista: " + e.getMessage());
        }
        System.out.println();
    }

    public static void listarTodosMensalistas() {
        try {
            System.out.println("=== Listando todos os Mensalistas ===");
            
            URL url = new URL(BASE_URL + "/mensalistas");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            
            int responseCode = connection.getResponseCode();
            System.out.println("Status Code: " + responseCode);
            
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream())
            );
            
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            
            System.out.println("Lista de Mensalistas:");
            System.out.println(response.toString());
            connection.disconnect();
            
        } catch (IOException e) {
            System.err.println("Erro ao listar mensalistas: " + e.getMessage());
        }
        System.out.println();
    }

    public static void buscarMensalistaPorMatricula(String matricula) {
        try {
            System.out.println("=== Buscando Mensalista por matrícula: " + matricula + " ===");
            
            URL url = new URL(BASE_URL + "/mensalistas/" + matricula);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            
            int responseCode = connection.getResponseCode();
            System.out.println("Status Code: " + responseCode);
            
            BufferedReader reader;
            if (responseCode >= 200 && responseCode < 300) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }
            
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            
            System.out.println("Dados do Mensalista:");
            System.out.println(response.toString());
            connection.disconnect();
            
        } catch (IOException e) {
            System.err.println("Erro ao buscar mensalista: " + e.getMessage());
        }
        System.out.println();
    }

    public static void verificarStatus() {
        try {
            System.out.println("=== Verificando Status do Servidor ===");
            
            URL url = new URL(BASE_URL + "/status");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            
            int responseCode = connection.getResponseCode();
            System.out.println("Status Code: " + responseCode);
            
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream())
            );
            
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            
            System.out.println("Status do servidor:");
            System.out.println(response.toString());
            connection.disconnect();
            
        } catch (IOException e) {
            System.err.println("Erro ao verificar status: " + e.getMessage());
        }
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("Iniciando testes dos clientes HTTP...\n");
        
        //  Rubrica 4
        verificarStatus();
        
        // Rubrica 1
        criarMensalista();
        
        // Rubrica 2
        listarTodosMensalistas();
        
        // Rubrica 3
        buscarMensalistaPorMatricula("001");
        buscarMensalistaPorMatricula("123");
        
        System.out.println("Testes dos clientes HTTP finalizados!");
    }
}