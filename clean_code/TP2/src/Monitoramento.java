// Exercício 10: CQS em Sistema de Monitoramento
// Problema: Getter incrementa contador (mistura consulta com atualização)
// Solução: Separar consulta de incremento

public class Monitoramento {
  private int contadorAcessos = 0;

  /**
   * VERSÃO ORIGINAL (INCORRETA) - mantida para demonstração.
   * 
   * PROBLEMA: Método com nome de getter (get) modifica estado
   * - Nome sugere Query, mas comporta-se como Command
   * - Viola princípio de menor surpresa
   * - Torna métricas não confiáveis
   */
  public int getContadorAcessosERRADO() {
    return ++contadorAcessos; // PROBLEMA: Incrementa ao consultar
  }

  /**
   * QUERY: Retorna o contador atual SEM modificá-lo.
   * Pode ser chamado múltiplas vezes com mesmo resultado.
   * 
   * @return O número atual de acessos
   */
  public int getContadorAcessos() {
    return contadorAcessos;
  }

  /**
   * COMMAND: Registra um novo acesso incrementando o contador.
   * Nome deixa claro que é uma ação que modifica estado.
   */
  public void registrarAcesso() {
    contadorAcessos++;
  }

  /**
   * COMMAND: Registra múltiplos acessos de uma vez.
   */
  public void registrarAcessos(int quantidade) {
    if (quantidade < 0) {
      throw new IllegalArgumentException("Quantidade não pode ser negativa");
    }
    contadorAcessos += quantidade;
  }

  /**
   * COMMAND: Reseta o contador (útil para testes ou início de período).
   */
  public void resetarContador() {
    contadorAcessos = 0;
  }

  /**
   * QUERY: Retorna estatísticas completas do monitoramento.
   * Exemplo de Query que retorna objeto complexo.
   */
  public EstatisticasAcesso obterEstatisticas() {
    return new EstatisticasAcesso(
        contadorAcessos,
        java.time.LocalDateTime.now());
  }

  /**
   * Classe imutável para retornar estatísticas.
   * Query deve retornar valores imutáveis quando possível.
   */
  public static class EstatisticasAcesso {
    private final int totalAcessos;
    private final java.time.LocalDateTime dataConsulta;

    public EstatisticasAcesso(int totalAcessos, java.time.LocalDateTime dataConsulta) {
      this.totalAcessos = totalAcessos;
      this.dataConsulta = dataConsulta;
    }

    public int getTotalAcessos() {
      return totalAcessos;
    }

    public java.time.LocalDateTime getDataConsulta() {
      return dataConsulta;
    }

    @Override
    public String toString() {
      return String.format("Total: %d acessos (consultado em %s)",
          totalAcessos, dataConsulta);
    }
  }

  public static void main(String[] args) {
    System.out.println("Exercício 10 - CQS em Sistema de Monitoramento\n");
    System.out.println("=".repeat(70));

    // Demonstração da ABORDAGEM MELHORADA
    Monitoramento monitor = new Monitoramento();

    System.out.println("--- Cenário 1: Uso correto (CQS) ---");
    System.out.println("Contador inicial: " + monitor.getContadorAcessos());

    System.out.println("\nSimulando 5 acessos ao sistema:");
    for (int i = 1; i <= 5; i++) {
      monitor.registrarAcesso();
      System.out.println("  Acesso #" + i + " registrado");
    }

    System.out.println("\nConsultando contador (múltiplas vezes):");
    System.out.println("1ª consulta: " + monitor.getContadorAcessos());
    System.out.println("2ª consulta: " + monitor.getContadorAcessos());
    System.out.println("3ª consulta: " + monitor.getContadorAcessos());
    System.out.println("✓ Todas retornam o mesmo valor");

    System.out.println("\n--- Cenário 2: Dashboard de métricas ---");
    System.out.println("Consultando para exibir em dashboard...");
    EstatisticasAcesso stats1 = monitor.obterEstatisticas();
    System.out.println("Dashboard 1: " + stats1);

    // Simula atualização de dashboard após alguns segundos
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
    }

    EstatisticasAcesso stats2 = monitor.obterEstatisticas();
    System.out.println("Dashboard 2: " + stats2);
    System.out.println(" Métricas consistentes");

    System.out.println("\n--- Cenário 3: Registro em lote ---");
    System.out.println("Registrando 10 acessos em lote...");
    monitor.registrarAcessos(10);
    System.out.println("Total de acessos: " + monitor.getContadorAcessos());

    // Demonstração do PROBLEMA
    System.out.println("\n" + "=".repeat(70));
    System.out.println("PROBLEMA DA VERSÃO ORIGINAL:");
    System.out.println("=".repeat(70));

    Monitoramento monitorProblema = new Monitoramento();
    System.out.println("Usando getContadorAcessosERRADO():\n");

    System.out.println("Supondo que queremos exibir o contador 3 vezes:");
    int valor1 = monitorProblema.getContadorAcessosERRADO();
    System.out.println("1ª consulta (dashboard): " + valor1);

    int valor2 = monitorProblema.getContadorAcessosERRADO();
    System.out.println("2ª consulta (relatório): " + valor2);

    int valor3 = monitorProblema.getContadorAcessosERRADO();
    System.out.println("3ª consulta (email): " + valor3);

    System.out.println("\n Problema: Cada consulta incrementou o contador");
    System.out.println(" Métricas ficam incorretas e não confiáveis");

    System.out.println("\n" + "=".repeat(70));
    System.out.println("PROBLEMAS CAUSADOS PELA MISTURA:");
    System.out.println("=".repeat(70));
    System.out.println("1. MÉTRICAS INCORRETAS");
    System.out.println("   → Simples consulta incrementa contador");
    System.out.println("   → Dashboard distorce números reais");
    System.out.println("   → Relatórios não confiáveis");
    System.out.println();
    System.out.println("2. COMPORTAMENTO INESPERADO");
    System.out.println("   → Nome 'get' sugere leitura, não escrita");
    System.out.println("   → Quebra princípio de menor surpresa");
    System.out.println();
    System.out.println("3. DIFICULDADE DE DEBUG");
    System.out.println("   → Observar variável no debugger altera seu valor");
    System.out.println("   → Heisenbug: bug aparece/desaparece ao investigar");
    System.out.println();
    System.out.println("4. PROBLEMAS DE CONCORRÊNCIA");
    System.out.println("   → Múltiplas threads consultando causam race condition");
    System.out.println("   → Contadores inconsistentes");
    System.out.println();
    System.out.println("5. SERIALIZAÇÃO/FRAMEWORKS");
    System.out.println("   → Frameworks chamam getters automaticamente");
    System.out.println("   → JSON serialization incrementaria contador");
    System.out.println("   → Reflection APIs causariam efeitos colaterais");

    System.out.println("\n" + "=".repeat(70));
    System.out.println("BOAS PRÁTICAS PARA CONTADORES E MÉTRICAS:");
    System.out.println("=".repeat(70));
    System.out.println("✓ Getters NUNCA modificam estado");
    System.out.println("✓ Incremento via método explícito (registrarAcesso)");
    System.out.println("✓ Nomes de métodos indicam claramente ações");
    System.out.println("✓ Queries retornam snapshots imutáveis");
    System.out.println("✓ Thread-safety quando necessário");
    System.out.println("✓ Separação entre coleta e consulta de métricas");
    System.out.println();
    System.out.println("NOMENCLATURA CLARA:");
    System.out.println("Queries:  getContador(), obterEstatisticas(), consultarTotal()");
    System.out.println("Commands: registrarAcesso(), incrementar(), resetar()\n");
  }
}

/*
 * CQS EM SISTEMAS DE MONITORAMENTO:
 * =================================
 * 
 * PROBLEMA CLÁSSICO:
 * - getX() que incrementa contador viola CQS
 * - Nome getter sugere Query, mas age como Command
 * - Causa métricas incorretas e bugs sutis
 * 
 * IMPACTO EM SISTEMAS REAIS:
 * 1. Dashboards: Cada refresh incrementa métricas
 * 2. Relatórios: Consultas alteram dados
 * 3. APIs: GET requests modificam estado (viola REST)
 * 4. Logging: Log de variável altera variável
 * 5. Debugger: Observar variável muda seu valor
 * 
 * BOAS PRÁTICAS:
 * 1. Queries (getX): Sempre puras, sem efeitos colaterais
 * 2. Commands: Nomes verbais claros (registrar, incrementar)
 * 3. Separação: Coleta ≠ Consulta de métricas
 * 4. Imutabilidade: Retornar snapshots imutáveis
 * 5. Thread-safety: Considerar acesso concurrent
 * 
 * PATTERN: Event Sourcing para Métricas
 * - Eventos: registrarAcesso() adiciona evento
 * - Query: getContador() calcula de eventos
 * - Vantagens: Auditoria, replay, análise temporal
 */