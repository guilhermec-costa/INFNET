// Exercício 11: Switch Exaustivo com Enumerações
// Problema: default oculta casos não tratados quando enum é expandido
// Solução: Switch exaustivo sem default para forçar tratamento completo

enum StatusPedido {
  PENDENTE,
  PROCESSANDO,
  ENVIADO,
  ENTREGUE,
  // Novos status podem ser adicionados aqui
  // CANCELADO, // Ao adicionar, compilador força tratar em todos switches!
}

public class PedidoService {
  /**
   * VERSÃO ORIGINAL (INCORRETA) - mantida para demonstração.
   * 
   * PROBLEMA: Uso de default oculta casos não tratados.
   * - Se novo status for adicionado ao enum, código compila normalmente
   * - Default silenciosamente trata caso novo como "desconhecido"
   * - Bug em produção: novo status não tratado adequadamente
   */
  public void atualizarStatusOriginal(StatusPedido status) {
    switch (status) {
      case PENDENTE:
        System.out.println("O pedido está pendente.");
        break;
      case PROCESSANDO:
        System.out.println("O pedido está sendo processado.");
        break;
      case ENVIADO:
        System.out.println("O pedido foi enviado.");
        break;
      case ENTREGUE:
        System.out.println("O pedido foi entregue.");
        break;
      default:
        System.out.println("Status desconhecido"); // PROBLEMA: Oculta bugs
    }
  }

  /**
   * VERSÃO REFATORADA: Switch exaustivo sem default.
   * 
   * BENEFÍCIO: Se novo status for adicionado ao enum, código NÃO compila
   * até que todos os switches sejam atualizados para tratá-lo.
   * 
   * Isso força o desenvolvedor a considerar explicitamente o novo caso,
   * evitando bugs silenciosos em produção.
   */
  public void atualizarStatus(StatusPedido status) {
    switch (status) {
      case PENDENTE:
        processarPendente();
        break;
      case PROCESSANDO:
        processarEmProcessamento();
        break;
      case ENVIADO:
        processarEnviado();
        break;
      case ENTREGUE:
        processarEntregue();
        break;
      // Sem default! Se adicionar novo status, não compila até tratar aqui.
    }
  }

  private void processarPendente() {
    System.out.println("✓ Pedido PENDENTE: Aguardando processamento");
    System.out.println("  Ação: Notificar equipe de vendas");
  }

  private void processarEmProcessamento() {
    System.out.println("✓ Pedido PROCESSANDO: Em preparação");
    System.out.println("  Ação: Separar produtos no estoque");
  }

  private void processarEnviado() {
    System.out.println("✓ Pedido ENVIADO: Em trânsito");
    System.out.println("  Ação: Enviar código de rastreamento ao cliente");
  }

  private void processarEntregue() {
    System.out.println("✓ Pedido ENTREGUE: Concluído");
    System.out.println("  Ação: Solicitar avaliação do cliente");
  }

  /**
   * Método adicional que também usa switch exaustivo.
   * Demonstra que TODOS os switches devem ser atualizados quando enum muda.
   */
  public String obterDescricaoStatus(StatusPedido status) {
    switch (status) {
      case PENDENTE:
        return "Aguardando processamento do pedido";
      case PROCESSANDO:
        return "Pedido sendo preparado para envio";
      case ENVIADO:
        return "Pedido a caminho do destino";
      case ENTREGUE:
        return "Pedido entregue com sucesso";
    }
    // Compilador garante que todos os casos foram cobertos
    throw new IllegalStateException("Status não tratado: " + status);
  }

  /**
   * Demonstração de validação adicional quando necessário.
   */
  public boolean podeSerCancelado(StatusPedido status) {
    // Switch exaustivo para garantir análise de todos os casos
    switch (status) {
      case PENDENTE:
      case PROCESSANDO:
        return true; // Pode cancelar nesses estados
      case ENVIADO:
      case ENTREGUE:
        return false; // Não pode cancelar após envio
    }
    throw new IllegalStateException("Status não tratado: " + status);
  }

  public static void main(String[] args) {
    PedidoService service = new PedidoService();

    System.out.println("Exercício 11 - Switch Exaustivo com Enum\n");
    System.out.println("=".repeat(70));
    System.out.println("Processando todos os status de pedido:\n");

    // Processa todos os status existentes
    for (StatusPedido status : StatusPedido.values()) {
      System.out.println("-".repeat(70));
      System.out.println("Status: " + status);
      System.out.println("-".repeat(70));
      service.atualizarStatus(status);
      System.out.println("Descrição: " + service.obterDescricaoStatus(status));
      System.out.println("Pode cancelar? " + service.podeSerCancelado(status));
      System.out.println();
    }

    System.out.println("=".repeat(70));
    System.out.println("PROBLEMA DO USO DE DEFAULT:");
    System.out.println("=".repeat(70));
    System.out.println("CENÁRIO: Empresa adiciona novo status CANCELADO ao enum");
    System.out.println();
    System.out.println("COM DEFAULT:");
    System.out.println(" Código compila normalmente");
    System.out.println(" Novo status cai no default ('Status desconhecido')");
    System.out.println(" Bug silencioso em produção");
    System.out.println(" Cliente com pedido cancelado vê 'status desconhecido'");
    System.out.println(" Notificações não são enviadas corretamente");
    System.out.println();
    System.out.println("SEM DEFAULT (EXAUSTIVO):");
    System.out.println("✓ Código NÃO compila");
    System.out.println("✓ Compilador força tratar o novo caso");
    System.out.println("✓ Desenvolvedor considera explicitamente novo status");
    System.out.println("✓ Impossível esquecer de atualizar algum switch");
    System.out.println("✓ Bug detectado em tempo de compilação, não em produção");

    System.out.println("\n" + "=".repeat(70));
    System.out.println("IMPORTÂNCIA DO SWITCH EXAUSTIVO:");
    System.out.println("=".repeat(70));
    System.out.println("1. SEGURANÇA EM TEMPO DE COMPILAÇÃO");
    System.out.println("   → Erro de compilação força tratamento completo");
    System.out.println("   → Previne bugs antes de chegar à produção");
    System.out.println();
    System.out.println("2. MANUTENIBILIDADE");
    System.out.println("   → Adicionar valor ao enum = atualizar todos os switches");
    System.out.println("   → Não há como esquecer casos");
    System.out.println();
    System.out.println("3. DOCUMENTAÇÃO VIVA");
    System.out.println("   → Switch mostra explicitamente todos os casos possíveis");
    System.out.println("   → Código é autoexplicativo");
    System.out.println();
    System.out.println("4. REFATORAÇÃO SEGURA");
    System.out.println("   → Mudar enum causa erros de compilação em locais afetados");
    System.out.println("   → Garante que todas as dependências são atualizadas");
    System.out.println();
    System.out.println("5. PREVENTS DEFENSIVE PROGRAMMING");
    System.out.println("   → Não precisa de checks adicionais");
    System.out.println("   → Compilador garante completude");

    System.out.println("\n" + "=".repeat(70));
    System.out.println("QUANDO USAR DEFAULT:");
    System.out.println("=".repeat(70));
    System.out.println("Default é APROPRIADO quando:");
    System.out.println("• Switch não é sobre enum (int, String, etc.)");
    System.out.println("• Múltiplos valores compartilham mesmo tratamento");
    System.out.println("• Valor vem de fonte externa não controlada");
    System.out.println();
    System.out.println("Default é INAPROPRIADO quando:");
    System.out.println("• Switch é sobre enum que você controla");
    System.out.println("• Quer garantir tratamento explícito de novos casos");
    System.out.println("• Manutenibilidade e type-safety são importantes");

    System.out.println("\n" + "=".repeat(70));
    System.out.println("TÉCNICA: Throw no final para garantir exaustividade");
    System.out.println("=".repeat(70));
    System.out.println("Se método retorna valor, adicione após o switch:");
    System.out.println("throw new IllegalStateException(\"Status não tratado\");");
    System.out.println();
    System.out.println("Benefícios:");
    System.out.println("✓ Compilador garante que switch está completo");
    System.out.println("✓ Falha rápida se lógica estiver incorreta");
    System.out.println("✓ Impossível retornar null acidentalmente\n");
  }
}

/*
 * SWITCH EXAUSTIVO COM ENUM:
 * =========================
 * 
 * PRINCÍPIO:
 * Quando switch opera sobre enum, NÃO use default para forçar tratamento
 * explícito de todos os casos. Compilador detecta casos faltantes.
 * 
 * PROBLEMA DO DEFAULT:
 * - Silenciosamente trata casos não previstos
 * - Novos valores de enum caem no default sem aviso
 * - Bugs aparecem em produção, não em compilação
 * - Desenvolvedor não é forçado a considerar novo caso
 * 
 * BENEFÍCIOS SEM DEFAULT:
 * 1. Erro de compilação se caso não for tratado
 * 2. Impossível esquecer de atualizar código ao expandir enum
 * 3. Refatoração segura e confiável
 * 4. Documentação implícita de todos os casos
 * 5. Type-safety máxima
 * 
 * PATTERN: Exhaustive Switch
 * - Lista explicitamente TODOS os valores do enum
 * - SEM default (exceto se realmente desejado)
 * - Throw IllegalStateException após switch para métodos com retorno
 * - Compilador força completude
 * 
 * EXCEÇÕES (quando usar default):
 * - Switch sobre tipos não-enum (int, String)
 * - Múltiplos casos compartilham tratamento
 * - Input vem de fonte externa não controlada
 * - Tratamento genérico é genuinamente desejado
 * 
 * FERRAMENTAS:
 * - Java 14+: Switch expressions são exaustivas por padrão
 * - IDEs: Warnings sobre switches não-exaustivos
 * - Error Prone: Regra RequiresSwitchDefault
 * 
 * EXEMPLO DE EVOLUÇÃO:
 * 1. Adiciona CANCELADO ao enum
 * 2. Todos os switches quebram (não compilam)
 * 3. Desenvolvedor força a tratar CANCELADO
 * 4. Impossível deixar passar
 */
