package com.infnet.TP3;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class StatelessApiExercises {

    private static final String BASE_URL = "https://apichallenges.eviltester.com/sim/entities";

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
        System.out.println("Status Code: " + statusCode);

        if ("OPTIONS".equalsIgnoreCase(method)) {
            System.out.println("Allowed Methods: " + conn.getHeaderField("Allow"));
        }

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
        System.out.println("--------------------------------------------------");

        conn.disconnect();
        return responseBody.toString();
    }

    public void exercicio1() throws Exception {
        System.out.println("--- Exercício 1: GET simples de todas as entidades ---");
        executeRequest(BASE_URL, "GET", null);
    }

    public void exercicio2() throws Exception {
        System.out.println("--- Exercício 2: GET de entidade específica ---");
        for (int i = 2; i <= 8; i++) { // Demonstração com IDs de 2 a 8
            System.out.println("Buscando entidade com ID: " + i);
            executeRequest(BASE_URL + "/" + i, "GET", null);
        }
    }

    public void exercicio3() throws Exception {
        System.out.println("--- Exercício 3: GET de entidade inexistente ---");
        System.out.println("Buscando entidade com ID: 13");
        executeRequest(BASE_URL + "/13", "GET", null);
    }

    public void exercicio4() throws Exception {
        System.out.println("--- Exercício 4: GET com parâmetros na URL ---");
        String urlComParams = BASE_URL + "?categoria=teste&limite=5";
        System.out.println("URL Montada: " + urlComParams);
        executeRequest(urlComParams, "GET", null);
    }

    public void exercicio5() throws Exception {
        System.out.println("--- Exercício 5: POST criando uma nova entidade ---");
        String jsonPayload = "{\"name\": \"aluno\"}";
        executeRequest(BASE_URL, "POST", jsonPayload);
    }
    
    public void exercicio6() throws Exception {
        System.out.println("--- Exercício 6: GET da entidade criada ---");
        System.out.println("Buscando entidade com ID: 11 (criada no exercício anterior)");
        executeRequest(BASE_URL + "/11", "GET", null);
    }

    public void exercicio7() throws Exception {
        System.out.println("--- Exercício 7: POST para atualizar uma entidade ---");
        String jsonPayload = "{\"name\": \"atualizado\"}";
        System.out.println("Atualizando entidade 10 com POST:");
        executeRequest(BASE_URL + "/10", "POST", jsonPayload);

        System.out.println("\nVerificando a entidade 10 após a atualização:");
        executeRequest(BASE_URL + "/10", "GET", null);
    }
    
    public void exercicio8() throws Exception {
        System.out.println("--- Exercício 8: PUT para atualizar entidade ---");
        String jsonPayload = "{\"name\": \"atualizado\"}";
        System.out.println("Atualizando entidade 10 com PUT:");
        executeRequest(BASE_URL + "/10", "PUT", jsonPayload);

        System.out.println("\nVerificando a entidade 10 após a atualização com PUT:");
        executeRequest(BASE_URL + "/10", "GET", null);
    }

    public void exercicio9() throws Exception {
        System.out.println("--- Exercício 9: DELETE de entidade válida ---");
        System.out.println("Deletando a entidade 9:");
        executeRequest(BASE_URL + "/9", "DELETE", null);
        
        System.out.println("\nTentando acessar a entidade 9 após o DELETE para confirmar (esperado 404):");
        executeRequest(BASE_URL + "/9", "GET", null);
    }

    public void exercicio10() throws Exception {
        System.out.println("--- Exercício 10: DELETE inválido ---");
        System.out.println("Tentando deletar a entidade 2 (não pode ser removida):");
        executeRequest(BASE_URL + "/2", "DELETE", null);
    }
    
    public void exercicio11() throws Exception {
        System.out.println("--- Exercício 11: OPTIONS com verificação de métodos ---");
        executeRequest(BASE_URL, "OPTIONS", null);
    }
}