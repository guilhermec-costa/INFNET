// Exercício 8: Command Query Separation (CQS)
// Problema: Método mistura consulta com alteração de estado
// Solução: Separar queries (consultas) de commands (comandos)

public class ContaBancaria {
  private double saldo;

  public ContaBancaria(double saldoInicial) {
    if (saldoInicial < 0) {
      throw new IllegalArgumentException("Saldo inicial não pode ser negativo");
    }
    this.saldo = saldoInicial;
  }

  /**
   * VERSÃO ORIGINAL (INCORRETA) - mantida para demonstração.
   * 
   * PROBLEMA: Método mistura Query (verificação) com Command (alteração).
   * - Retorna boolean (comportamento de Query)
   * - Modifica o saldo (comportamento de Command)
   * 
   * Consequências:
   * - Efeito colateral inesperado
   * - Dificulta depuração
   * - Quebra princípio de menor surpresa
   * - Testes ficam confusos
   */
  public boolean podeComprarERRADO(double valor) {
    if (saldo >= valor) {
      saldo -= valor; // PROBLEMA: Modifica estado enquanto consulta!
      return true;
    }
    return false;
  }

  /**
   * QUERY: Verifica se há saldo suficiente SEM modificar estado.
   * Pode ser chamada múltiplas vezes sem efeitos colaterais.
   * 
   * @param valor Valor a verificar
   * @return true se há saldo suficiente, false caso contrário
   */
  public boolean temSaldoSuficiente(double valor) {
    return saldo >= valor;
  }

  /**
   * COMMAND: Realiza a compra e altera o estado da conta.
   * Não retorna valor de negócio, apenas confirma execução.
   * 
   * @param valor Valor da compra
   * @throws IllegalStateException se não houver saldo suficiente
   */
  public void realizarCompra(double valor) {
    if (!temSaldoSuficiente(valor)) {
      throw new IllegalStateException(
          String.format("Saldo insuficiente. Saldo: %.2f, Valor: %.2f", saldo, valor));
    }
    saldo -= valor;
  }

  /**
   * QUERY: Retorna o saldo atual sem modificá-lo.
   */
  public double getSaldo() {
    return saldo;
  }

  /**
   * COMMAND: Adiciona valor à conta.
   */
  public void depositar(double valor) {
    if (valor <= 0) {
      throw new IllegalArgumentException("Valor de depósito deve ser positivo");
    }
    saldo += valor;
  }

  public static void main(String[] args) {
    System.out.println("Exercício 8 - Command Query Separation (CQS)\n");
    System.out.println("=".repeat(70));

    // Demonstração da ABORDAGEM CORRETA
    ContaBancaria conta = new ContaBancaria(1000.0);
    System.out.println("Saldo inicial: R$ " + conta.getSaldo());

    System.out.println("\n--- Cenário 1: Compra com saldo suficiente ---");
    double valorCompra = 300.0;

    // QUERY: Pode ser chamada múltiplas vezes sem efeito colateral
    System.out.println("Verificando saldo (1ª vez): " + conta.temSaldoSuficiente(valorCompra));
    System.out.println("Verificando saldo (2ª vez): " + conta.temSaldoSuficiente(valorCompra));
    System.out.println("Saldo ainda é: R$ " + conta.getSaldo() + " (não foi alterado)");

    // COMMAND: Realiza a alteração
    if (conta.temSaldoSuficiente(valorCompra)) {
      conta.realizarCompra(valorCompra);
      System.out.println("Compra realizada");
    }
    System.out.println("Saldo após compra: R$ " + conta.getSaldo());

    System.out.println("\n--- Cenário 2: Tentativa de compra sem saldo ---");
    valorCompra = 1000.0;
    System.out.println("Tentando comprar R$ " + valorCompra);

    if (conta.temSaldoSuficiente(valorCompra)) {
      conta.realizarCompra(valorCompra);
      System.out.println("Compra realizada");
    } else {
      System.out.println("Compra negada: saldo insuficiente");
    }
    System.out.println("Saldo final: R$ " + conta.getSaldo());

    // Demonstração do PROBLEMA da abordagem errada
    System.out.println("\n" + "=".repeat(70));
    System.out.println("PROBLEMA DA VERSÃO ORIGINAL (podeComprarERRADO):");
    System.out.println("=".repeat(70));

    ContaBancaria contaProblema = new ContaBancaria(500.0);
    System.out.println("Saldo inicial: R$ " + contaProblema.getSaldo());

    System.out.println("\nChamando podeComprarERRADO(200) duas vezes:");
    boolean resultado1 = contaProblema.podeComprarERRADO(200.0);
    System.out.println("1ª chamada retornou: " + resultado1);
    System.out.println("Saldo após 1ª chamada: R$ " + contaProblema.getSaldo());

    boolean resultado2 = contaProblema.podeComprarERRADO(200.0);
    System.out.println("2ª chamada retornou: " + resultado2);
    System.out.println("Saldo após 2ª chamada: R$ " + contaProblema.getSaldo());

    System.out.println("\n Problema: Chamar uma 'consulta' alterou o estado");

    System.out.println("\n" + "=".repeat(70));
    System.out.println("IMPORTÂNCIA DO COMMAND QUERY SEPARATION:");
    System.out.println("=".repeat(70));
    System.out.println("1. PREVISIBILIDADE");
    System.out.println("   → Queries sempre retornam mesmo resultado para mesmo estado");
    System.out.println();
    System.out.println("2. DEPURAÇÃO FACILITADA");
    System.out.println("   → Queries podem ser chamadas em debugger sem efeitos colaterais");
    System.out.println();
    System.out.println("3. TESTABILIDADE");
    System.out.println("   → Testes de queries não alteram estado");
    System.out.println("   → Testes de commands podem verificar mudanças de estado");
    System.out.println();
    System.out.println("4. THREAD-SAFETY");
    System.out.println("   → Queries são naturalmente thread-safe");
    System.out.println();
    System.out.println("5. CLAREZA DE CÓDIGO");
    System.out.println("   → Nome do método indica claramente se altera estado");

    System.out.println("\n" + "=".repeat(70));
    System.out.println("REGRAS DO CQS:");
    System.out.println("=".repeat(70));
    System.out.println("QUERIES (Consultas):");
    System.out.println("✓ Retornam valores");
    System.out.println("✓ NÃO modificam estado");
    System.out.println("✓ Sem efeitos colaterais");
    System.out.println("✓ Podem ser chamadas múltiplas vezes com mesmo resultado");
    System.out.println();
    System.out.println("COMMANDS (Comandos):");
    System.out.println("✓ Modificam estado");
    System.out.println("✓ NÃO retornam valores de negócio (void ou status)");
    System.out.println("✓ Podem ter efeitos colaterais");
    System.out.println("✓ Nome indica ação (realizar, executar, processar)\n");
  }
}

/*
 * COMMAND QUERY SEPARATION (CQS):
 * ===============================
 * 
 * PRINCÍPIO:
 * "Cada método deve ser um comando OU uma query, nunca ambos."
 * 
 * DEFINIÇÕES:
 * - QUERY: Retorna valor, não altera estado (efeito colateral)
 * - COMMAND: Altera estado, não retorna valor de negócio
 * 
 * BENEFÍCIOS:
 * 1. Código mais previsível e compreensível
 * 2. Facilita depuração (queries não alteram estado)
 * 3. Melhora testabilidade
 * 4. Reduz bugs causados por efeitos colaterais inesperados
 * 5. Facilita otimizações (caching de queries)
 * 
 * EXCEÇÕES:
 * - Stack.pop() - Por razões de performance, pode ser aceitável
 * - Métodos de sincronização (compareAndSwap)
 * - Casos onde separação causaria problemas de atomicidade
 * 
 * APLICAÇÃO NO EXERCÍCIO:
 * - podeComprar() separado em:
 * - temSaldoSuficiente() - QUERY
 * - realizarCompra() - COMMAND
 * - getSaldo() - QUERY pura
 * - depositar() - COMMAND puro
 */