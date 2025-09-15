package com.infnet.TP5_CLI;

import com.fasterxml.jackson.databind.JsonNode;
import com.infnet.TP5_CLI.client.ApiClient;
import com.infnet.TP5_CLI.session.SessionManager;
import com.infnet.TP5_CLI.util.ConsoleUtil;
import picocli.CommandLine.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Command(name = "ecommerce-cli", description = "CLI para interação com API de E-commerce")
public class EcommerceCLI implements Runnable {
    
    private final ApiClient apiClient;
    private final SessionManager session;
    private final Scanner scanner;
    
    public EcommerceCLI() {
        this.apiClient = new ApiClient();
        this.session = SessionManager.getInstance();
        this.scanner = new Scanner(System.in);
    }
    
    public static void main(String[] args) {
        ConsoleUtil.clearScreen();
        ConsoleUtil.printTitle("E-commerce CLI");
        ConsoleUtil.printInfo("Bem-vindo ao sistema de e-commerce!");
        
        EcommerceCLI cli = new EcommerceCLI();
        cli.run();
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                if (!session.isLoggedIn()) {
                    showMainMenu();
                } else {
                    showLoggedInMenu();
                }
            } catch (Exception e) {
                ConsoleUtil.printError("Erro inesperado: " + e.getMessage());
                ConsoleUtil.waitForEnter();
            }
        }
    }
    
    private void showMainMenu() {
        ConsoleUtil.printTitle("Menu Principal");
        System.out.println("1. Registrar nova conta");
        System.out.println("2. Fazer login");
        System.out.println("3. Ver produtos disponíveis");
        System.out.println("4. Verificar status da API");
        System.out.println("0. Sair");
        
        int opcao = ConsoleUtil.readInt("\nEscolha uma opção");
        
        switch (opcao) {
            case 1 -> registrarConta();
            case 2 -> fazerLogin();
            case 3 -> listarProdutos();
            case 4 -> verificarStatusAPI();
            case 0 -> {
                ConsoleUtil.printInfo("Obrigado por usar o E-commerce CLI!");
                System.exit(0);
            }
            default -> ConsoleUtil.printWarning("Opção inválida!");
        }
    }
    
    private void showLoggedInMenu() {
        ConsoleUtil.printTitle("Menu do Cliente - " + session.getNomeCliente());
        System.out.println("1. Ver produtos");
        System.out.println("2. Meus dados");
        System.out.println("3. Gerenciar endereços");
        System.out.println("4. Gerenciar formas de pagamento");
        System.out.println("5. Fazer pedido");
        System.out.println("6. Meus pedidos");
        System.out.println("7. Cancelar pedido");
        System.out.println("8. Atualizar meus dados");
        System.out.println("0. Logout");
        
        int opcao = ConsoleUtil.readInt("\nEscolha uma opção");
        
        switch (opcao) {
            case 1 -> listarProdutos();
            case 2 -> mostrarDadosCliente();
            case 3 -> gerenciarEnderecos();
            case 4 -> gerenciarFormasPagamento();
            case 5 -> fazerPedido();
            case 6 -> listarMeusPedidos();
            case 7 -> cancelarPedido();
            case 8 -> atualizarDados();
            case 0 -> {
                session.logout();
                ConsoleUtil.printSuccess("Logout realizado com sucesso!");
                ConsoleUtil.waitForEnter();
            }
            default -> ConsoleUtil.printWarning("Opção inválida!");
        }
    }
    
    private void registrarConta() {
        ConsoleUtil.printTitle("Registrar Nova Conta");
        
        String nome = ConsoleUtil.readLine("Nome completo");
        String email = ConsoleUtil.readLine("Email");
        String senha = ConsoleUtil.readPassword("Senha (mín. 6 caracteres)");
        
        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            ConsoleUtil.printError("Todos os campos são obrigatórios!");
            ConsoleUtil.waitForEnter();
            return;
        }
        
        String json = String.format("{\"nome\":\"%s\",\"email\":\"%s\",\"senha\":\"%s\"}", 
            nome, email, senha);
        
        ApiClient.ApiResponse response = apiClient.post("/registro", json);
        
        if (response.isSuccess()) {
            ConsoleUtil.printSuccess("Conta criada com sucesso!");
            if (response.json != null) {
                ConsoleUtil.printInfo("ID: " + response.json.get("id").asText());
                ConsoleUtil.printInfo("Nome: " + response.json.get("nome").asText());
                ConsoleUtil.printInfo("Email: " + response.json.get("email").asText());
            }
        } else {
            ConsoleUtil.printError("Erro ao criar conta: " + getErrorMessage(response));
        }
        
        ConsoleUtil.waitForEnter();
    }
    
    private void fazerLogin() {
        ConsoleUtil.printTitle("Login");
        
        String email = ConsoleUtil.readLine("Email");
        String senha = ConsoleUtil.readPassword("Senha");
        
        if (email.isEmpty() || senha.isEmpty()) {
            ConsoleUtil.printError("Email e senha são obrigatórios!");
            ConsoleUtil.waitForEnter();
            return;
        }
        
        String json = String.format("{\"email\":\"%s\",\"senha\":\"%s\"}", email, senha);
        
        ApiClient.ApiResponse response = apiClient.post("/login", json);
        
        if (response.isSuccess() && response.json != null) {
            String clienteId = response.json.get("id").asText();
            String nome = response.json.get("nome").asText();
            String emailResp = response.json.get("email").asText();
            
            session.login(clienteId, nome, emailResp);
            ConsoleUtil.printSuccess("Login realizado com sucesso!");
            ConsoleUtil.printInfo("Bem-vindo, " + nome + "!");
        } else {
            ConsoleUtil.printError("Email ou senha incorretos!");
        }
        
        ConsoleUtil.waitForEnter();
    }
    
    private void listarProdutos() {
        ConsoleUtil.printTitle("Produtos Disponíveis");
        
        ApiClient.ApiResponse response = apiClient.get("/produtos");
        
        if (response.isSuccess() && response.json != null && response.json.isArray()) {
            List<String[]> produtos = new ArrayList<>();
            
            for (JsonNode produto : response.json) {
                produtos.add(new String[]{
                    produto.get("id").asText(),
                    produto.get("nome").asText(),
                    "R$ " + produto.get("preco").asText(),
                    produto.get("estoque").asText(),
                    produto.get("descricao").asText()
                });
            }
            
            if (produtos.isEmpty()) {
                ConsoleUtil.printWarning("Nenhum produto encontrado.");
            } else {
                String[] headers = {"ID", "Nome", "Preço", "Estoque", "Descrição"};
                ConsoleUtil.printTable(headers, produtos.toArray(new String[0][]));
            }
        } else {
            ConsoleUtil.printError("Erro ao buscar produtos: " + getErrorMessage(response));
        }
        
        ConsoleUtil.waitForEnter();
    }
    
    private void mostrarDadosCliente() {
        ConsoleUtil.printTitle("Meus Dados");
        
        ApiClient.ApiResponse response = apiClient.get("/clientes/me", session.getClienteId());
        
        if (response.isSuccess() && response.json != null) {
            JsonNode cliente = response.json;
            
            System.out.println("ID: " + cliente.get("id").asText());
            System.out.println("Nome: " + cliente.get("nome").asText());
            System.out.println("Email: " + cliente.get("email").asText());
            
            JsonNode enderecos = cliente.get("enderecos");
            if (enderecos != null && enderecos.isArray() && enderecos.size() > 0) {
                System.out.println("\nEndereços:");
                for (JsonNode endereco : enderecos) {
                    System.out.println("  - ID " + endereco.get("id").asText() + ": " +
                        endereco.get("logradouro").asText() + ", " +
                        endereco.get("numero").asText() + " - " +
                        endereco.get("cidade").asText() + ", CEP: " +
                        endereco.get("cep").asText());
                }
            } else {
                System.out.println("\nNenhum endereço cadastrado.");
            }
            
            JsonNode formasPag = cliente.get("formasPagamento");
            if (formasPag != null && formasPag.isArray() && formasPag.size() > 0) {
                System.out.println("\nFormas de Pagamento:");
                for (JsonNode forma : formasPag) {
                    System.out.println("  - ID " + forma.get("id").asText() + ": " +
                        forma.get("tipo").asText() + " - " +
                        forma.get("descricao").asText());
                }
            } else {
                System.out.println("\nNenhuma forma de pagamento cadastrada.");
            }
        } else {
            ConsoleUtil.printError("Erro ao buscar dados: " + getErrorMessage(response));
        }
        
        ConsoleUtil.waitForEnter();
    }
    
    private void gerenciarEnderecos() {
        ConsoleUtil.printTitle("Adicionar Endereço");
        
        String logradouro = ConsoleUtil.readLine("Logradouro (rua/av)");
        String numero = ConsoleUtil.readLine("Número");
        String cep = ConsoleUtil.readLine("CEP");
        String cidade = ConsoleUtil.readLine("Cidade");
        
        if (logradouro.isEmpty() || numero.isEmpty() || cep.isEmpty() || cidade.isEmpty()) {
            ConsoleUtil.printError("Todos os campos são obrigatórios!");
            ConsoleUtil.waitForEnter();
            return;
        }
        
        String json = String.format("{\"logradouro\":\"%s\",\"numero\":\"%s\",\"cep\":\"%s\",\"cidade\":\"%s\"}", 
            logradouro, numero, cep, cidade);
        
        ApiClient.ApiResponse response = apiClient.post("/clientes/me/enderecos", json, session.getClienteId());
        
        if (response.isSuccess()) {
            ConsoleUtil.printSuccess("Endereço adicionado com sucesso!");
        } else {
            ConsoleUtil.printError("Erro ao adicionar endereço: " + getErrorMessage(response));
        }
        
        ConsoleUtil.waitForEnter();
    }
    
    private void gerenciarFormasPagamento() {
        ConsoleUtil.printTitle("Adicionar Forma de Pagamento");
        
        System.out.println("Tipos disponíveis: CARTAO_CREDITO, CARTAO_DEBITO, PIX, BOLETO");
        String tipo = ConsoleUtil.readLine("Tipo");
        String descricao = ConsoleUtil.readLine("Descrição");
        
        if (tipo.isEmpty() || descricao.isEmpty()) {
            ConsoleUtil.printError("Todos os campos são obrigatórios!");
            ConsoleUtil.waitForEnter();
            return;
        }
        
        String json = String.format("{\"tipo\":\"%s\",\"descricao\":\"%s\"}", tipo, descricao);
        
        ApiClient.ApiResponse response = apiClient.post("/clientes/me/formaspagamento", json, session.getClienteId());
        
        if (response.isSuccess()) {
            ConsoleUtil.printSuccess("Forma de pagamento adicionada com sucesso!");
        } else {
            ConsoleUtil.printError("Erro ao adicionar forma de pagamento: " + getErrorMessage(response));
        }
        
        ConsoleUtil.waitForEnter();
    }
    
    private void fazerPedido() {
        ConsoleUtil.printTitle("Fazer Pedido");
        
        listarProdutos();
        
        List<String> carrinho = new ArrayList<>();
        
        while (true) {
            long produtoId = ConsoleUtil.readLong("ID do produto (0 para finalizar)");
            if (produtoId == 0) break;
            
            int quantidade = ConsoleUtil.readInt("Quantidade");
            
            carrinho.add(String.format("{\"produtoId\":%d,\"quantidade\":%d}", produtoId, quantidade));
        }
        
        if (carrinho.isEmpty()) {
            ConsoleUtil.printWarning("Carrinho vazio!");
            ConsoleUtil.waitForEnter();
            return;
        }
        
        long enderecoId = ConsoleUtil.readLong("ID do endereço de entrega");
        long formaPagamentoId = ConsoleUtil.readLong("ID da forma de pagamento");
        
        String json = String.format("{\"carrinho\":[%s],\"enderecoId\":%d,\"formaPagamentoId\":%d}", 
            String.join(",", carrinho), enderecoId, formaPagamentoId);
        
        ApiClient.ApiResponse response = apiClient.post("/pedidos", json, session.getClienteId());
        
        if (response.isSuccess()) {
            ConsoleUtil.printSuccess("Pedido criado com sucesso!");
            if (response.json != null) {
                ConsoleUtil.printInfo("ID do pedido: " + response.json.get("id").asText());
                ConsoleUtil.printInfo("Valor total: R$ " + response.json.get("valorTotal").asText());
            }
        } else {
            ConsoleUtil.printError("Erro ao criar pedido: " + getErrorMessage(response));
        }
        
        ConsoleUtil.waitForEnter();
    }
    
    private void listarMeusPedidos() {
        ConsoleUtil.printTitle("Meus Pedidos");
        
        ApiClient.ApiResponse response = apiClient.get("/pedidos", session.getClienteId());
        
        if (response.isSuccess() && response.json != null && response.json.isArray()) {
            List<String[]> pedidos = new ArrayList<>();
            
            for (JsonNode pedido : response.json) {
                pedidos.add(new String[]{
                    pedido.get("id").asText(),
                    pedido.get("status").asText(),
                    "R$ " + pedido.get("valorTotal").asText(),
                    pedido.get("dataPedido").asText()
                });
            }
            
            if (pedidos.isEmpty()) {
                ConsoleUtil.printWarning("Você não tem pedidos.");
            } else {
                String[] headers = {"ID", "Status", "Valor", "Data"};
                ConsoleUtil.printTable(headers, pedidos.toArray(new String[0][]));
            }
        } else {
            ConsoleUtil.printError("Erro ao buscar pedidos: " + getErrorMessage(response));
        }
        
        ConsoleUtil.waitForEnter();
    }
    
    private void cancelarPedido() {
        ConsoleUtil.printTitle("Cancelar Pedido");
        
        long pedidoId = ConsoleUtil.readLong("ID do pedido para cancelar");
        
        ApiClient.ApiResponse response = apiClient.post("/pedidos/" + pedidoId + "/cancelar", "", session.getClienteId());
        
        if (response.isSuccess()) {
            ConsoleUtil.printSuccess("Pedido cancelado com sucesso!");
        } else {
            ConsoleUtil.printError("Erro ao cancelar pedido: " + getErrorMessage(response));
        }
        
        ConsoleUtil.waitForEnter();
    }
    
    private void atualizarDados() {
        ConsoleUtil.printTitle("Atualizar Dados");
        
        String novoNome = ConsoleUtil.readLine("Novo nome (deixe vazio para manter atual)");
        String novoEmail = ConsoleUtil.readLine("Novo email (deixe vazio para manter atual)");
        
        String json = String.format("{\"nome\":\"%s\",\"email\":\"%s\"}", novoNome, novoEmail);
        
        ApiClient.ApiResponse response = apiClient.put("/clientes/me", json, session.getClienteId());
        
        if (response.isSuccess()) {
            ConsoleUtil.printSuccess("Dados atualizados com sucesso!");
            if (!novoNome.isEmpty()) {
                session.login(session.getClienteId(), novoNome, 
                    novoEmail.isEmpty() ? session.getEmailCliente() : novoEmail);
            }
        } else {
            ConsoleUtil.printError("Erro ao atualizar dados: " + getErrorMessage(response));
        }
        
        ConsoleUtil.waitForEnter();
    }
    
    private void verificarStatusAPI() {
        ConsoleUtil.printTitle("Status da API");
        
        ApiClient.ApiResponse response = apiClient.get("/health");
        
        if (response.isSuccess()) {
            ConsoleUtil.printSuccess("API está funcionando!");
            if (response.json != null) {
                ConsoleUtil.printInfo("Status: " + response.json.get("status").asText());
            }
        } else {
            ConsoleUtil.printError("API não está respondendo!");
            ConsoleUtil.printError("Verifique se o servidor está rodando em http://localhost:7070");
        }
        
        ConsoleUtil.waitForEnter();
    }
    
    private String getErrorMessage(ApiClient.ApiResponse response) {
        if (response.json != null && response.json.has("erro")) {
            return response.json.get("erro").asText();
        }
        return "Status: " + response.statusCode + " - " + response.body;
    }
}