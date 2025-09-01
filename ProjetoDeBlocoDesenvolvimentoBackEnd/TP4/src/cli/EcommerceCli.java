package src.cli;

import src.model.*;
import src.service.AutenticacaoService;
import src.service.ClienteService;
import src.service.PedidoService;
import src.repository.IProdutoRepository;
import src.repository.ProdutoRepositoryInMemory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class EcommerceCli {
  private final Scanner scanner;
    private final AutenticacaoService authService;
    private final PedidoService pedidoService;
    private final ClienteService clienteService;
    private final IProdutoRepository produtoRepository;

    private Cliente clienteLogado = null;

    public EcommerceCli(AutenticacaoService authService, PedidoService pedidoService, ClienteService clienteService, IProdutoRepository produtoRepository) {
        this.scanner = new Scanner(System.in);
        this.authService = authService;
        this.pedidoService = pedidoService;
        this.produtoRepository = produtoRepository;
        this.clienteService = clienteService;
    }

    private int lerInt() {
      while (true) {
          try {
              return Integer.parseInt(scanner.nextLine());
          } catch (NumberFormatException e) {
              System.out.print("Entrada inválida. Por favor, digite um número: ");
          }
      }
    }

    public void iniciar() {
        while (true) {
            if (clienteLogado == null) {
                exibirMenuDeslogado();
            } else {
                exibirMenuLogado();
            }
        }
    }

    private void exibirMenuDeslogado() {
        System.out.println("\n=== BEM-VINDO AO E-COMMERCE ===");
        System.out.println("1. Login");
        System.out.println("2. Sair");
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine(); 

        if (opcao == 1) {
            fazerLogin();
        } else if (opcao == 2) {
            System.out.println("Até logo!");
            System.exit(0);
        }
    }

   private void exibirMenuLogado() {
        System.out.println("\n--- Olá, " + clienteLogado.getNome() + "! ---");
        System.out.println("1. Comprar (Ver produtos)");
        System.out.println("2. Ver meu histórico de pedidos");
        System.out.println("3. Cancelar um pedido");
        System.out.println("--- Minha Conta ---");
        System.out.println("4. Editar meus dados");
        System.out.println("5. Gerenciar endereços");
        System.out.println("6. Gerenciar formas de pagamento");
        System.out.println("-------------------");
        System.out.println("7. Logout");
        System.out.print("Escolha uma opção: ");
        int opcao = lerInt();

        switch (opcao) {
            case 1: iniciarCompra(); break;
            case 2: consultarHistorico(); break;
            case 3: cancelarPedido(); break;
            case 4: editarDadosCadastrais(); break;
            case 5: gerenciarEnderecos(); break;
            case 6: gerenciarFormasPagamento(); break;
            case 7: clienteLogado = null; System.out.println("Logout realizado com sucesso."); break;
            default: System.out.println("Opção inválida."); break;
        }
    } 

    private void editarDadosCadastrais() {
        System.out.println("\n--- EDITAR DADOS ---");
        System.out.println("Nome atual: " + clienteLogado.getNome());
        System.out.print("Novo nome (deixe em branco para não alterar): ");
        String novoNome = scanner.nextLine();

        System.out.println("Email atual: " + clienteLogado.getEmail());
        System.out.print("Novo email (deixe em branco para não alterar): ");
        String novoEmail = scanner.nextLine();
        
        clienteService.atualizarDados(clienteLogado, novoNome, novoEmail);
        System.out.println("Dados atualizados com sucesso!");
    }

    private void gerenciarEnderecos() {
        System.out.println("\n--- MEUS ENDEREÇOS ---");
        clienteLogado.getEnderecos().forEach(System.out::println);
        System.out.println("------------------------");
        System.out.println("1. Adicionar novo endereço");
        System.out.println("2. Voltar");
        System.out.print("Opção: ");
        if (lerInt() == 1) {
            System.out.print("Logradouro (Rua/Av): "); String log = scanner.nextLine();
            System.out.print("Número: "); String num = scanner.nextLine();
            System.out.print("CEP: "); String cep = scanner.nextLine();
            System.out.print("Cidade: "); String cid = scanner.nextLine();
            clienteService.adicionarEndereco(clienteLogado, log, num, cep, cid);
            System.out.println("Endereço adicionado com sucesso!");
        }
    }
    
    private void gerenciarFormasPagamento() {
        System.out.println("\n--- MINHAS FORMAS DE PAGAMENTO ---");
        clienteLogado.getFormasPagamento().forEach(System.out::println);
        System.out.println("----------------------------------");
        System.out.println("1. Adicionar nova forma de pagamento");
        System.out.println("2. Voltar");
        System.out.print("Opção: ");
        if (lerInt() == 1) {
            System.out.print("Tipo (Ex: Cartão de Crédito, PIX): "); String tipo = scanner.nextLine();
            System.out.print("Descrição (Ex: final 1234): "); String desc = scanner.nextLine();
            clienteService.adicionarFormaPagamento(clienteLogado, tipo, desc);
            System.out.println("Forma de pagamento adicionada com sucesso!");
        }
    }

    private void cancelarPedido() {
        consultarHistorico();
        System.out.print("\nDigite o ID do pedido que deseja cancelar (ou 0 para voltar): ");
        long pedidoId = lerInt();
        if (pedidoId == 0) return;

        if (pedidoService.cancelarPedido(clienteLogado, pedidoId)) {
            System.out.println("Pedido #" + pedidoId + " cancelado com sucesso e estoque devolvido.");
        } else {
            System.out.println("Erro: Pedido não encontrado, já processado ou não pertence a você.");
        }
    }

    private void fazerLogin() {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Optional<Cliente> clienteOpt = authService.login(email, senha);
        if (clienteOpt.isPresent()) {
            clienteLogado = clienteOpt.get();
            System.out.println("Login bem-sucedido!");
        } else {
            System.out.println("Email ou senha inválidos.");
        }
    }

    private void iniciarCompra() {
        List<ItemPedido> carrinho = new ArrayList<>();
        while (true) {
            System.out.println("\n--- PRODUTOS DISPONÍVEIS ---");
            produtoRepository.listarTodos().forEach(System.out::println);
            System.out.println("-------------------------------------");
            System.out.print("Digite o ID do produto para adicionar ao carrinho (ou 0 para finalizar): ");
            long produtoId = scanner.nextLong();
            scanner.nextLine();

            if (produtoId == 0) break;

            System.out.print("Digite a quantidade: ");
            int quantidade = scanner.nextInt();
            scanner.nextLine();

            Optional<Produto> produtoOpt = produtoRepository.buscarPorId(produtoId);
            if (produtoOpt.isPresent()) {
                carrinho.add(new ItemPedido(produtoOpt.get(), quantidade));
                System.out.println("Produto adicionado ao carrinho!");
            } else {
                System.out.println("Produto não encontrado.");
            }
        }

        if (!carrinho.isEmpty()) {
            finalizarPedido(carrinho);
        }
    }
    
    private void finalizarPedido(List<ItemPedido> carrinho) {
        System.out.println("\nSelecione o endereço de entrega:");
        List<Endereco> enderecos = clienteLogado.getEnderecos();
        for (int i = 0; i < enderecos.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, enderecos.get(i));
        }
        System.out.print("Opção: ");
        int endOpcao = scanner.nextInt() - 1;
        scanner.nextLine();
        Endereco enderecoEscolhido = enderecos.get(endOpcao);

        System.out.println("\nSelecione a forma de pagamento:");
        List<FormaPagamento> formasPagamento = clienteLogado.getFormasPagamento();
         for (int i = 0; i < formasPagamento.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, formasPagamento.get(i));
        }
        System.out.print("Opção: ");
        int pagOpcao = scanner.nextInt() - 1;
        scanner.nextLine();
        FormaPagamento formaPagamentoEscolhida = formasPagamento.get(pagOpcao);

        Pedido novoPedido = pedidoService.criarPedido(clienteLogado, carrinho, enderecoEscolhido, formaPagamentoEscolhida);
        if (novoPedido != null) {
            System.out.println("\n--- PEDIDO REGISTRADO COM SUCESSO! ---");
            System.out.println("ID do Pedido: " + novoPedido.getId());
            System.out.println("Valor Total: R$" + novoPedido.getValorTotal());
            System.out.println("Status: " + novoPedido.getStatus());
        } else {
            System.out.println("Não foi possível registrar o pedido.");
        }
    }
    
    private void consultarHistorico() {
        List<Pedido> pedidos = pedidoService.listarPedidosPorCliente(clienteLogado);
        System.out.println("\n--- MEUS PEDIDOS ---");
        if (pedidos.isEmpty()) {
            System.out.println("Você ainda не tem pedidos.");
        } else {
            for (Pedido p : pedidos) {
                System.out.printf("Pedido #%d | Data: %s | Valor: R$%.2f | Status: %s\n",
                    p.getId(), p.getDataPedido().toLocalDate(), p.getValorTotal(), p.getStatus());
            }
        }
    }
}