// Exercício 12: Switch Exaustivo para Sistema de Notificações
// Problema: default com exception ainda oculta necessidade de tratar novos casos
// Solução: Switch exaustivo sem default para garantir tratamento completo

enum TipoNotificacao {
  EMAIL,
  SMS,
  PUSH
  // Ao adicionar novo tipo aqui (ex: WHATSAPP), todos os switches quebram
}

/**
 * Interface para estratégias de notificação.
 * Bonus: Combina switch exaustivo com Strategy pattern.
 */
interface NotificacaoStrategy {
  void enviar(String destinatario, String mensagem);

  String getCanal();
}

class EmailNotificacao implements NotificacaoStrategy {
  @Override
  public void enviar(String destinatario, String mensagem) {
    System.out.println("  Enviando e-mail...");
    System.out.println("   Para: " + destinatario);
    System.out.println("   Mensagem: " + mensagem);
    System.out.println("   Status: E-mail enviado com sucesso");
  }

  @Override
  public String getCanal() {
    return "E-mail";
  }
}

class SMSNotificacao implements NotificacaoStrategy {
  @Override
  public void enviar(String destinatario, String mensagem) {
    System.out.println(" Enviando SMS...");
    System.out.println("   Para: " + destinatario);
    System.out.println("   Mensagem: " + mensagem);
    System.out.println("   Status: SMS enviado com sucesso");
  }

  @Override
  public String getCanal() {
    return "SMS";
  }
}

class PushNotificacao implements NotificacaoStrategy {
  @Override
  public void enviar(String destinatario, String mensagem) {
    System.out.println(" Enviando notificação push...");
    System.out.println("   Para: " + destinatario);
    System.out.println("   Mensagem: " + mensagem);
    System.out.println("   Status: Push notification enviada com sucesso");
  }

  @Override
  public String getCanal() {
    return "Push Notification";
  }
}

public class NotificacaoService {
  /**
   * VERSÃO ORIGINAL (INCORRETA) - mantida para efeito de demonstração.
   * 
   * PROBLEMA: default com exception ainda permite compilação quando
   * novo tipo é adicionado ao enum.
   * - Erro só aparece em RUNTIME, não em COMPILE TIME
   * - Desenvolvedor pode esquecer de atualizar este método
   * - Testes podem não cobrir o novo tipo
   */
  public void enviarNotificacaoOriginal(TipoNotificacao tipo) {
    switch (tipo) {
      case EMAIL:
        System.out.println("Enviando e-mail...");
        break;
      case SMS:
        System.out.println("Enviando SMS...");
        break;
      case PUSH:
        System.out.println("Enviando notificação push...");
        break;
      default:
        throw new IllegalArgumentException("Tipo de notificação desconhecido");
    }
  }

  /**
   * VERSÃO REFATORADA: Switch exaustivo sem default.
   * 
   * Usa Factory pattern com switch exaustivo para criar a estratégia correta.
   * Se novo TipoNotificacao for adicionado, código NÃO COMPILA.
   */
  public void enviarNotificacao(TipoNotificacao tipo, String destinatario, String mensagem) {
    NotificacaoStrategy strategy = criarStrategy(tipo);

    System.out.println("\n" + "=".repeat(60));
    System.out.println("Canal: " + strategy.getCanal());
    System.out.println("=".repeat(60));

    strategy.enviar(destinatario, mensagem);

    System.out.println("=".repeat(60));
  }

  /**
   * Factory Method com switch exaustivo.
   * SEM DEFAULT: Compilador força tratamento de todos os casos.
   */
  private NotificacaoStrategy criarStrategy(TipoNotificacao tipo) {
    switch (tipo) {
      case EMAIL:
        return new EmailNotificacao();
      case SMS:
        return new SMSNotificacao();
      case PUSH:
        return new PushNotificacao();
    }
    // Esta linha nunca será executada se todos os casos forem tratados,
    // mas é necessária para satisfazer o compilador em métodos com retorno.
    throw new IllegalStateException("Tipo não tratado: " + tipo);
  }

  /**
   * Outro método que também deve tratar todos os tipos.
   * Demonstra que switch exaustivo força consistência em TODO o código.
   */
  public boolean requerConfirmacao(TipoNotificacao tipo) {
    // Switch exaustivo: todos os casos explícitos
    switch (tipo) {
      case EMAIL:
        return true; // Email requer confirmação de leitura
      case SMS:
        return false; // SMS não requer confirmação
      case PUSH:
        return false; // Push não requer confirmação
    }
    throw new IllegalStateException("Tipo não tratado: " + tipo);
  }

  /**
   * Método que retorna custo estimado por tipo.
   */
  public double obterCustoEstimado(TipoNotificacao tipo) {
    switch (tipo) {
      case EMAIL:
        return 0.01; // R$ 0,01 por email
      case SMS:
        return 0.10; // R$ 0,10 por SMS
      case PUSH:
        return 0.00; // Push é gratuito
    }
    throw new IllegalStateException("Tipo não tratado: " + tipo);
  }

  /**
   * Método que valida disponibilidade do canal.
   */
  public boolean isCanalDisponivel(TipoNotificacao tipo) {
    switch (tipo) {
      case EMAIL:
        return verificarServidorEmail();
      case SMS:
        return verificarGatewaySMS();
      case PUSH:
        return verificarServidorPush();
    }
    throw new IllegalStateException("Tipo não tratado: " + tipo);
  }

  private boolean verificarServidorEmail() {
    return true; // Simplificado
  }

  private boolean verificarGatewaySMS() {
    return true; // Simplificado
  }

  private boolean verificarServidorPush() {
    return true; // Simplificado
  }

  public static void main(String[] args) {
    NotificacaoService service = new NotificacaoService();

    System.out.println("Exercício 12 - Switch Exaustivo para Notificações\n");

    // Testa todos os tipos de notificação
    service.enviarNotificacao(
        TipoNotificacao.EMAIL,
        "usuario@example.com",
        "Seu pedido foi confirmado!");

    service.enviarNotificacao(
        TipoNotificacao.SMS,
        "+55 11 98765-4321",
        "Código de verificação: 123456");

    service.enviarNotificacao(
        TipoNotificacao.PUSH,
        "device_token_xyz",
        "Nova mensagem recebida");

    // Demonstra outros métodos exaustivos
    System.out.println("\n" + "=".repeat(70));
    System.out.println("ANÁLISE DE TODOS OS TIPOS:");
    System.out.println("=".repeat(70));

    for (TipoNotificacao tipo : TipoNotificacao.values()) {
      System.out.printf("\n%-15s | Requer confirmação: %-5s | Custo: R$ %.2f | Disponível: %s%n",
          tipo,
          service.requerConfirmacao(tipo),
          service.obterCustoEstimado(tipo),
          service.isCanalDisponivel(tipo));
    }

    System.out.println("\n" + "=".repeat(70));
    System.out.println("VIOLAÇÃO DO PRINCÍPIO DE SWITCH EXAUSTIVO:");
    System.out.println("=".repeat(70));
    System.out.println("PROBLEMA: Usar default, mesmo com exception");
    System.out.println();
    System.out.println("switch (tipo) {");
    System.out.println("    case EMAIL: ...");
    System.out.println("    case SMS: ...");
    System.out.println("    case PUSH: ...");
    System.out.println("    default:");
    System.out.println("        throw new IllegalArgumentException(\"Desconhecido\");");
    System.out.println("}");
    System.out.println();
    System.out.println("IMPACTO:");
    System.out.println(" Código COMPILA mesmo ao adicionar novo tipo ao enum");
    System.out.println(" Erro só aparece em RUNTIME quando novo tipo é usado");
    System.out.println(" Testes podem não cobrir o novo tipo");
    System.out.println(" Bug vai para produção");
    System.out.println(" Cliente vê exception ao usar nova funcionalidade");

    System.out.println("\n" + "=".repeat(70));
    System.out.println("IMPORTÂNCIA DE TRATAR EXPLICITAMENTE TODAS AS OPÇÕES:");
    System.out.println("=".repeat(70));
    System.out.println("1. COMPILAÇÃO SEGURA");
    System.out.println("   → Adicionar WHATSAPP ao enum quebra TODOS os switches");
    System.out.println("   → Impossível compilar sem tratar o novo caso");
    System.out.println("   → Erros detectados imediatamente");
    System.out.println();
    System.out.println("2. CONSISTÊNCIA DO CÓDIGO");
    System.out.println("   → TODOS os métodos precisam tratar TODOS os tipos");
    System.out.println("   → enviarNotificacao(), requerConfirmacao(), obterCustoEstimado()");
    System.out.println("   → Garante análise completa do novo tipo");
    System.out.println();
    System.out.println("3. DOCUMENTAÇÃO IMPLÍCITA");
    System.out.println("   → Switch exaustivo serve como documentação");
    System.out.println("   → Mostra explicitamente todos os tipos suportados");
    System.out.println("   → Fácil entender o sistema completo");
    System.out.println();
    System.out.println("4. MANUTENÇÃO FACILITADA");
    System.out.println("   → Adicionar funcionalidade = atualizar todos os pontos");
    System.out.println("   → Compilador é seu checklist automático");
    System.out.println("   → Zero chance de esquecer algo");
    System.out.println();
    System.out.println("5. REFATORAÇÃO CONFIÁVEL");
    System.out.println("   → Mudar enum força revisão de todo código dependente");
    System.out.println("   → Remove de enum = erros de compilação onde era usado");
    System.out.println("   → Refatoração 100% segura");

    System.out.println("\n" + "=".repeat(70));
    System.out.println("EXEMPLO PRÁTICO:");
    System.out.println("=".repeat(70));
    System.out.println("Cenário: Adicionar suporte para notificações via WhatsApp");
    System.out.println();
    System.out.println("PASSO 1: Adicionar ao enum");
    System.out.println("enum TipoNotificacao { EMAIL, SMS, PUSH, WHATSAPP }");
    System.out.println();
    System.out.println("PASSO 2: Compilar");
    System.out.println(" ERRO: criarStrategy não trata WHATSAPP");
    System.out.println(" ERRO: requerConfirmacao não trata WHATSAPP");
    System.out.println(" ERRO: obterCustoEstimado não trata WHATSAPP");
    System.out.println(" ERRO: isCanalDisponivel não trata WHATSAPP");
    System.out.println();
    System.out.println("PASSO 3: Implementar tratamento em TODOS os lugares");
    System.out.println("✓ Criar WhatsAppNotificacao");
    System.out.println("✓ Adicionar case WHATSAPP em criarStrategy");
    System.out.println("✓ Adicionar case WHATSAPP em requerConfirmacao");
    System.out.println("✓ Adicionar case WHATSAPP em obterCustoEstimado");
    System.out.println("✓ Adicionar case WHATSAPP em isCanalDisponivel");
    System.out.println();
    System.out.println("RESULTADO: Funcionalidade completa, nada esquecido\n");
  }
}

/*
 * TRATAMENTO EXPLÍCITO DE ENUMS:
 * ==============================
 * 
 * PRINCÍPIO FUNDAMENTAL:
 * Ao trabalhar com enums, trate EXPLICITAMENTE todos os valores possíveis.
 * Evite default para que o compilador force a completude.
 * 
 * POR QUE DEFAULT É PROBLEMÁTICO:
 * 1. Silencia erros de compilação
 * 2. Novos valores caem no default automaticamente
 * 3. Comportamento inesperado não é detectado
 * 4. Testes podem não cobrir novos casos
 * 5. Bugs aparecem em produção, não desenvolvimento
 * 
 * POR QUE THROW APÓS SWITCH É NECESSÁRIO:
 * - Compilador Java requer retorno em todos os caminhos
 * - Throw após switch satisfaz compilador
 * - Nunca é executado se todos os casos forem tratados
 * - Serve como fail-fast se lógica estiver errada
 * 
 * PATTERN: Exhaustive Enum Switch
 * ```java
 * String metodo(MeuEnum valor) {
 * switch (valor) {
 * case OPCAO1: return "resultado1";
 * case OPCAO2: return "resultado2";
 * case OPCAO3: return "resultado3";
 * }
 * throw new IllegalStateException("Não tratado: " + valor);
 * }
 * ```
 * 
 * BENEFÍCIOS:
 * 1. Type-safety em tempo de compilação
 * 2. Refatoração segura e confiável
 * 3. Documentação implícita completa
 * 4. Impossível esquecer casos
 * 5. Código é consistente em todo o sistema
 * 
 * 
 * QUANDO EXCEÇÕES SÃO ACEITÁVEIS:
 * - Input de fonte externa não confiável
 * - Enum de terceiros que pode mudar
 * - Genuinamente deseja tratamento genérico para novos casos
 */