import java.io.*;
import java.util.*;

public class CSVManager {
    private static final String CLIENTES_FILE = "clientes.csv";
    private static final String PRODUTOS_FILE = "produtos.csv";
    private static final String PEDIDOS_FILE = "pedidos.csv";
    private static final String ITENS_PEDIDO_FILE = "itens_pedido.csv";
    
    public static void salvarClientes(List<Cliente> clientes) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CLIENTES_FILE))) {
            writer.println("id,nome,email,telefone,dataCadastro");
            for (Cliente cliente : clientes) {
                writer.println(cliente.toCSV());
            }
        }
    }
    
    public static List<Cliente> carregarClientes() throws IOException {
        List<Cliente> clientes = new ArrayList<>();
        File file = new File(CLIENTES_FILE);
        
        if (!file.exists()) {
            return clientes;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha = reader.readLine();
            while ((linha = reader.readLine()) != null) {
                clientes.add(Cliente.fromCSV(linha));
            }
        }
        return clientes;
    }
    
    public static void salvarProdutos(List<Produto> produtos) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(PRODUTOS_FILE))) {
            writer.println("id,nome,descricao,preco,categoria,estoque");
            for (Produto produto : produtos) {
                writer.println(produto.toCSV());
            }
        }
    }
    
    public static List<Produto> carregarProdutos() throws IOException {
        List<Produto> produtos = new ArrayList<>();
        File file = new File(PRODUTOS_FILE);
        
        if (!file.exists()) {
            return produtos;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha = reader.readLine(); 
            while ((linha = reader.readLine()) != null) {
                produtos.add(Produto.fromCSV(linha));
            }
        }
        return produtos;
    }
    
    public static void salvarPedidos(List<Pedido> pedidos) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(PEDIDOS_FILE))) {
            writer.println("id,numero,dataPedido,valorTotal,clienteId,enderecoId,formaPagamentoId");
            for (Pedido pedido : pedidos) {
                writer.println(pedido.toCSV());
            }
        }
    }
    
    public static List<Pedido> carregarPedidos() throws IOException {
        List<Pedido> pedidos = new ArrayList<>();
        File file = new File(PEDIDOS_FILE);
        
        if (!file.exists()) {
            return pedidos;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha = reader.readLine(); 
            while ((linha = reader.readLine()) != null) {
                pedidos.add(Pedido.fromCSV(linha));
            }
        }
        return pedidos;
    }
    
    public static void salvarItensPedido(List<ItemPedido> itens) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ITENS_PEDIDO_FILE))) {
            writer.println("id,pedidoId,produtoId,quantidade,precoUnitario");
            for (ItemPedido item : itens) {
                writer.println(item.toCSV());
            }
        }
    }
    
    public static List<ItemPedido> carregarItensPedido() throws IOException {
        List<ItemPedido> itens = new ArrayList<>();
        File file = new File(ITENS_PEDIDO_FILE);
        
        if (!file.exists()) {
            return itens;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha = reader.readLine(); 
            while ((linha = reader.readLine()) != null) {
                itens.add(ItemPedido.fromCSV(linha));
            }
        }
        return itens;
    }
    
    public static Cliente buscarClientePorId(Long id) throws IOException {
        List<Cliente> clientes = carregarClientes();
        return clientes.stream()
                      .filter(c -> c.getId().equals(id))
                      .findFirst()
                      .orElse(null);
    }
    
    public static Produto buscarProdutoPorId(Long id) throws IOException {
        List<Produto> produtos = carregarProdutos();
        return produtos.stream()
                      .filter(p -> p.getId().equals(id))
                      .findFirst()
                      .orElse(null);
    }
    
    public static List<Pedido> buscarPedidosPorCliente(Long clienteId) throws IOException {
        List<Pedido> pedidos = carregarPedidos();
        return pedidos.stream()
                     .filter(p -> p.getClienteId().equals(clienteId))
                     .toList();
    }
    
    public static List<ItemPedido> buscarItensPorPedido(Long pedidoId) throws IOException {
        List<ItemPedido> itens = carregarItensPedido();
        return itens.stream()
                   .filter(i -> i.getPedidoId().equals(pedidoId))
                   .toList();
    }
}