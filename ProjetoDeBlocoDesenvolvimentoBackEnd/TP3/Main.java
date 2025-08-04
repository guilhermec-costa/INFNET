import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class Main {
  public static void main(String[] args) {
        try {
            List<Cliente> clientes = Arrays.asList(
                new Cliente(1L, "João Silva", "joao@email.com", "11999999999"),
                new Cliente(2L, "Maria Santos", "maria@email.com", "11888888888")
            );
            
            List<Produto> produtos = Arrays.asList(
                new Produto(1L, "Notebook", "Notebook Dell", new BigDecimal("2500.00"), "Eletrônicos", 10),
                new Produto(2L, "Mouse", "Mouse sem fio", new BigDecimal("50.00"), "Acessórios", 100)
            );
            
            List<Pedido> pedidos = Arrays.asList(
                new Pedido(1L, "PED001", 1L, 1L, 1L)
            );
            pedidos.get(0).setValorTotal(new BigDecimal("2550.00"));
            
            List<ItemPedido> itens = Arrays.asList(
                new ItemPedido(1L, 1L, 1L, 1, new BigDecimal("2500.00")),
                new ItemPedido(2L, 1L, 2L, 1, new BigDecimal("50.00"))
            );

            CSVManager.salvarClientes(clientes);
            CSVManager.salvarProdutos(produtos);
            CSVManager.salvarPedidos(pedidos);
            CSVManager.salvarItensPedido(itens);
            
            System.out.println("Todos os arquivos CSV criados com sucesso!");
            
            System.out.println("=== TESTE DE CARREGAMENTO ===");
            System.out.println("Clientes: " + CSVManager.carregarClientes().size());
            System.out.println("Produtos: " + CSVManager.carregarProdutos().size());
            System.out.println("Pedidos: " + CSVManager.carregarPedidos().size());
            System.out.println("Itens: " + CSVManager.carregarItensPedido().size());
            
            System.out.println("\n=== TESTE DE BUSCAS ===");
            Cliente cliente = CSVManager.buscarClientePorId(1L);
            if (cliente != null) {
                System.out.println("Cliente: " + cliente.getNome());
                List<Pedido> pedidosCliente = CSVManager.buscarPedidosPorCliente(1L);
                System.out.println("Pedidos do cliente: " + pedidosCliente.size());
            }
            
        } catch (IOException e) {
            System.err.println("Erro ao trabalhar com arquivos CSV: " + e.getMessage());
        }
    }
}
