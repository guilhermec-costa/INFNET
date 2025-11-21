// Exercício 3: Evitando NullPointerException - Padrão Null Object
// Problema: getNomeCliente() crasha quando cliente é null
// Solução: Implementação do padrão Null Object

interface Cliente {
  String getNome();

  boolean isValido();
}

class ClienteReal implements Cliente {
  private final String nome;

  public ClienteReal(String nome) {
    if (nome == null || nome.trim().isEmpty()) {
      throw new IllegalArgumentException("Nome do cliente não pode ser vazio");
    }
    this.nome = nome;
  }

  @Override
  public String getNome() {
    return nome;
  }

  @Override
  public boolean isValido() {
    return true;
  }
}

/**
 * Null Object: Representa um cliente inexistente sem usar null.
 * Evita NullPointerException fornecendo comportamento padrão seguro.
 */
class ClienteInexistente implements Cliente {
  @Override
  public String getNome() {
    return "Cliente não cadastrado";
  }

  @Override
  public boolean isValido() {
    return false;
  }
}

public class Pedido {
  private final Cliente cliente;

  /**
   * Construtor que sempre garante um objeto Cliente válido.
   * Se o cliente for null, usa ClienteInexistente (Null Object).
   */
  public Pedido(Cliente cliente) {
    this.cliente = (cliente != null) ? cliente : new ClienteInexistente();
  }

  /**
   * Retorna o nome do cliente sem risco de NullPointerException.
   * Se o cliente for inexistente, retorna mensagem padrão.
   */
  public String getNomeCliente() {
    return cliente.getNome();
  }

  /**
   * Verifica se o pedido possui um cliente válido.
   */
  public boolean temClienteValido() {
    return cliente.isValido();
  }

  /**
   * Processa o pedido apenas se o cliente for válido.
   */
  public String processar() {
    if (!temClienteValido()) {
      return "Erro: Não é possível processar pedido sem cliente cadastrado.";
    }
    return "Pedido processado com sucesso para: " + getNomeCliente();
  }

  public static void main(String[] args) {
    System.out.println("Exercício 3 - Null Object Pattern\n");

    // Cenário 1: Cliente válido
    Cliente clienteValido = new ClienteReal("João Silva");
    Pedido pedido1 = new Pedido(clienteValido);
    System.out.println("Pedido 1:");
    System.out.println("Nome: " + pedido1.getNomeCliente());
    System.out.println(pedido1.processar());

    System.out.println("\n" + "-".repeat(50) + "\n");

    // Cenário 2: Cliente null (não crasha)
    Pedido pedido2 = new Pedido(null);
    System.out.println("Pedido 2 (cliente null):");
    System.out.println("Nome: " + pedido2.getNomeCliente());
    System.out.println(pedido2.processar());

    System.out.println("\n" + "=".repeat(50));
    System.out.println("EXPLICAÇÃO DA ABORDAGEM:");
    System.out.println("=".repeat(50));
    System.out.println("✓ Interface Cliente define o contrato");
    System.out.println("✓ ClienteReal: implementação para clientes válidos");
    System.out.println("✓ ClienteInexistente: Null Object com comportamento padrão");
    System.out.println("✓ Sem verificações if (cliente != null)");
    System.out.println("✓ Sem risco de NullPointerException");
    System.out.println("✓ Código mais limpo e seguro\n");
  }
}

/*
 * EXPLICAÇÃO DO PADRÃO NULL OBJECT:
 * 1. Interface Cliente: Define o contrato que todas as implementações seguem
 * 2. ClienteReal: Representa um cliente válido com dados reais
 * 3. ClienteInexistente: Objeto que representa "ausência de cliente" sem usar
 * null
 * 4. Benefícios:
 * - Elimina verificações null espalhadas pelo código
 * - Comportamento padrão seguro para casos de ausência
 * - Código mais limpo e menos propenso a erros
 * - Polimorfismo ao invés de condicionais null
 */