package com.infnet.TP3;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class SimpleApiExercises {

    private static final String BASE_URL = "https://apichallenges.eviltester.com/simpleapi";

    private String executeRequest(String urlString, String method, String jsonBody) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method);
        conn.setRequestProperty("Accept", "application/json");

        if (jsonBody != null && !jsonBody.isEmpty()) {
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            try (DataOutputStream dos = new DataOutputStream(conn.getOutputStream())) {
                dos.writeBytes(jsonBody);
            }
        }

        int statusCode = conn.getResponseCode();
        System.out.println("URL: " + urlString);
        System.out.println("Status Code: " + statusCode);

        StringBuilder responseBody = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                statusCode >= 200 && statusCode < 300 ? conn.getInputStream() : conn.getErrorStream(),
                StandardCharsets.UTF_8))) {
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                responseBody.append(responseLine.trim());
            }
        }

        System.out.println("Response Body: " + responseBody.toString());
        System.out.println("---------------------------------------");

        conn.disconnect();
        return responseBody.toString();
    }

    public void exercicio12() throws Exception {
        System.out.println("--- Exercício 12: Experimentos com a Simple API ---");

        System.out.println("\nParte 1: GET em todos os itens");
        executeRequest(BASE_URL + "/items", "GET", null);

        System.out.println("\nParte 2: Gerar ISBN aleatório");
        String isbn = executeRequest(BASE_URL + "/randomisbn", "GET", null).trim();
        System.out.println("ISBN Gerado: " + isbn);

        System.out.println("\nParte 3: Criar item com POST usando o ISBN gerado");
        String newItemJson = String.format(
            "{\"type\": \"book\", \"isbn13\": \"%s\", \"price\": 5.99, \"numberinstock\": 5}",
            isbn
        );
        executeRequest(BASE_URL + "/items", "POST", newItemJson);
        
        System.out.println("\nVerificando todos os itens após a criação:");
        executeRequest(BASE_URL + "/items", "GET", null);

        System.out.println("\nParte 4: Atualizar item com PUT");
        String updatedItemJson = String.format(
            "{\"type\": \"book\", \"isbn13\": \"%s\", \"price\": 9.99, \"numberinstock\": 2}",
            isbn
        );
        executeRequest(BASE_URL + "/items/" + isbn, "PUT", updatedItemJson);

        System.out.println("\nVerificando todos os itens após a atualização:");
        executeRequest(BASE_URL + "/items", "GET", null);

        System.out.println("\nParte 5: Remover item com DELETE");
        executeRequest(BASE_URL + "/items/" + isbn, "DELETE", null);

        System.out.println("\nVerificando todos os itens após a remoção:");
        executeRequest(BASE_URL + "/items", "GET", null);
    }
}