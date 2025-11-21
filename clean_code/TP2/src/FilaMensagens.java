// Exercício 9: CQS em Sistema de Mensageria
// Problema: Método retorna E remove mensagem simultaneamente
// Solução: Separar visualização (Query) de remoção (Command)

import java.util.*;

public class FilaMensagens {
  private final Queue<String> mensagens = new LinkedList<>();

  public FilaMensagens() {
    // Inicializa com algumas mensagens de exemplo
    mensagens.offer("Mensagem 1: Bem-vindo ao sistema");
    mensagens.offer("Mensagem 2: Novo pedido recebido");
    mensagens.offer("Mensagem 3: Pagamento confirmado");
  }

  /**
   * VERSÃO ORIGINAL (INCORRETA) - mantida para demonstração.
   * 
   * PROBLEMA: Viola CQS - retorna valor E modifica estado.
   * 
   * Consequências em sistemas de mensageria:
   * - Impossível visualizar mensagem sem consumi-la
   * - Perde mensagens se houver erro após poll()
   * - Dificulta implementação de retry logic
   * - Problemas em sistemas distribuídos
   * - Não permite preview de mensagens
   */
  public String obterProximaMensagemERRADO() {
    return mensagens.poll(); // PROBLEMA: Retorna E remove!
  }

  /**
   * QUERY: Visualiza a próxima mensagem SEM removê-la.
   * Permite múltiplas consultas sem efeito colateral.
   * 
   * @return A próxima mensagem ou null se fila vazia
   */
  public String visualizarProxima() {
    return mensagens.peek(); // Apenas visualiza, não remove
  }

  /**
   * QUERY: Verifica se há mensagens na fila.
   */
  public boolean temMensagens() {
    return !mensagens.isEmpty();
  }

  /**
   * QUERY: Retorna o número de mensagens na fila.
   */
  public int getTamanho() {
    return mensagens.size();
  }

  /**
   * COMMAND: Remove a próxima mensagem da fila.
   * Não retorna valor de negócio, apenas confirma remoção.
   * 
   * @throws IllegalStateException se fila estiver vazia
   */
  public void removerProxima() {
    if (!temMensagens()) {
      throw new IllegalStateException("Fila vazia - não há mensagens para remover");
    }
    mensagens.poll();
  }

  /**
   * COMMAND: Adiciona mensagem à fila.
   */
  public void adicionarMensagem(String mensagem) {
    if (mensagem == null || mensagem.trim().isEmpty()) {
      throw new IllegalArgumentException("Mensagem não pode ser vazia");
    }
    mensagens.offer(mensagem);
  }

  /**
   * QUERY: Retorna cópia da lista de mensagens para visualização.
   * Retorna cópia para evitar modificação externa.
   */
  public List<String> visualizarTodas() {
    return new ArrayList<>(mensagens);
  }

  /**
   * Padrão de uso correto: Visualizar antes de processar/remover.
   * Permite implementar retry logic e tratamento de erros.
   */
  public void processarProximaMensagem() {
    // 1. QUERY: Visualiza sem remover
    String mensagem = visualizarProxima();

    if (mensagem == null) {
      System.out.println("Nenhuma mensagem para processar");
      return;
    }

    try {
      // 2. Processa a mensagem
      System.out.println("Processando: " + mensagem);

      // Simula processamento que pode falhar
      if (mensagem.contains("erro")) {
        throw new RuntimeException("Erro no processamento");
      }

      // 3. COMMAND: Remove apenas se processamento foi bem-sucedido
      removerProxima();
      System.out.println("✓ Mensagem processada e removida com sucesso");

    } catch (Exception e) {
      System.out.println("✗ Erro no processamento: " + e.getMessage());
      System.out.println("  Mensagem permanece na fila para retry");
    }
  }

  public static void main(String[] args) {
    System.out.println("Exercício 9 - CQS em Sistema de Mensageria\n");
    System.out.println("=".repeat(70));

    // Demonstração da ABORDAGEM MELHORADA
    FilaMensagens fila = new FilaMensagens();

    System.out.println("Estado inicial da fila:");
    System.out.println("Tamanho: " + fila.getTamanho());
    System.out.println("Mensagens: " + fila.visualizarTodas());

    System.out.println("\n--- Cenário 1: Visualizar sem remover ---");
    System.out.println("1ª visualização: " + fila.visualizarProxima());
    System.out.println("2ª visualização: " + fila.visualizarProxima());
    System.out.println("3ª visualização: " + fila.visualizarProxima());
    System.out.println("Tamanho após visualizações: " + fila.getTamanho() + " (não mudou)");

    System.out.println("\n--- Cenário 2: Processar mensagem com sucesso ---");
    fila.processarProximaMensagem();
    System.out.println("Tamanho após processamento: " + fila.getTamanho());

    System.out.println("\n--- Cenário 3: Erro no processamento (mensagem mantida) ---");
    fila.adicionarMensagem("Mensagem com erro: teste");
    System.out.println("Mensagens antes do erro: " + fila.visualizarTodas());
    fila.processarProximaMensagem();
    System.out.println("Mensagens após erro: " + fila.visualizarTodas());
    System.out.println("(Mensagem mantida na fila para retry)");

    // Demonstração do PROBLEMA
    System.out.println("\n" + "=".repeat(70));
    System.out.println("PROBLEMA DA VERSÃO ORIGINAL:");
    System.out.println("=".repeat(70));

    FilaMensagens filaProblema = new FilaMensagens();
    System.out.println("Tamanho inicial: " + filaProblema.getTamanho());

    System.out.println("\nChamando obterProximaMensagemERRADO() 3 vezes:");
    for (int i = 1; i <= 3; i++) {
      String msg = filaProblema.obterProximaMensagemERRADO();
      System.out.println(i + "ª chamada: " + msg);
      System.out.println("   Tamanho agora: " + filaProblema.getTamanho());
    }

    System.out.println("\n Problema: Não conseguimos visualizar sem remover");
    System.out.println(" Se houver erro no processamento, mensagem é perdida");

    System.out.println("\n" + "=".repeat(70));
    System.out.println("CONSEQUÊNCIAS EM SISTEMAS DE MENSAGERIA:");
    System.out.println("=".repeat(70));
    System.out.println("1. PERDA DE MENSAGENS");
    System.out.println("   → Mensagem removida antes de confirmar processamento");
    System.out.println("   → Erro após poll() causa perda permanente");
    System.out.println();
    System.out.println("2. IMPOSSÍVEL IMPLEMENTAR RETRY");
    System.out.println("   → Não há como reprocessar mensagem que falhou");
    System.out.println("   → Sistemas resilientes precisam de retry logic");
    System.out.println();
    System.out.println("3. PROBLEMAS EM SISTEMAS DISTRIBUÍDOS");
    System.out.println("   → Consumidor pode crashar após receber mensagem");
    System.out.println("   → Mensagem perdida sem confirmação (ACK)");
    System.out.println();
    System.out.println("4. DIFICULDADE DE MONITORAMENTO");
    System.out.println("   → Não é possível inspecionar mensagens sem consumi-las");
    System.out.println("   → Debugging requer consumir mensagens de produção");
    System.out.println();
    System.out.println("5. VIOLAÇÃO DE IDEMPOTÊNCIA");
    System.out.println("   → Chamar método múltiplas vezes tem efeitos diferentes");

    System.out.println("\n" + "=".repeat(70));
    System.out.println("BENEFÍCIOS DA SEPARAÇÃO (CQS):");
    System.out.println("=".repeat(70));
    System.out.println("✓ visualizarProxima(): Permite preview sem consumir");
    System.out.println("✓ removerProxima(): Remove apenas quando desejado");
    System.out.println("✓ Implementação de retry logic segura");
    System.out.println("✓ Transações: commit apenas se processamento OK");
    System.out.println("✓ Monitoring: Inspecionar fila sem afetar mensagens");
    System.out.println("✓ Testing: Verificar estado sem modificá-lo");
    System.out.println("✓ At-least-once delivery: Garantia de entrega\n");
  }
}

/*
 * CQS EM SISTEMAS DE MENSAGERIA:
 * ==============================
 * 
 * PATTERN: Peek-Process-Remove
 * 1. Peek: Visualiza mensagem (Query)
 * 2. Process: Processa conteúdo
 * 3. Remove: Remove apenas se sucesso (Command)
 * 
 * COMPARAÇÃO COM SISTEMAS REAIS:
 * - RabbitMQ: basic.get (peek) + basic.ack (remove)
 * - AWS SQS: ReceiveMessage (peek) + DeleteMessage (remove)
 * - Kafka: poll() + commit offset (separado)
 * 
 * PROBLEMAS DA VIOLAÇÃO:
 * 1. Perda de mensagens em caso de falha
 * 2. Impossibilidade de retry automático
 * 3. Não suporta transações
 * 4. Dificulta debugging e monitoramento
 * 
 * SOLUÇÃO CQS:
 * - Queries: visualizarProxima(), temMensagens(), getTamanho()
 * - Commands: removerProxima(), adicionarMensagem()
 * - Pattern seguro: visualizar → processar → remover se OK
 * 
 * GARANTIAS:
 * - At-least-once delivery
 * - Retry em caso de falha
 * - Transações seguras
 * - Idempotência de queries
 */