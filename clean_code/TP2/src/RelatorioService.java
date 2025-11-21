// Exercício 7: Abstract Factory Pattern para Geração de Relatórios
// Problema: Ifs complicados com múltiplas chamadas de métodos
// Solução: Abstract Factory + Switch para criação limpa

/**
 * Interface que define o contrato para geração de relatórios.
 * Todas as fábricas concretas implementam esta interface.
 */
interface RelatorioFactory {
  void gerarCabecalho();

  void gerarCorpo();

  void gerarRodape();

  /**
   * Template Method: Define o algoritmo de geração completa.
   */
  default void gerar() {
    gerarCabecalho();
    gerarCorpo();
    gerarRodape();
  }
}

/**
 * Fábrica concreta para relatórios PDF.
 */
class PDFRelatorioFactory implements RelatorioFactory {
  @Override
  public void gerarCabecalho() {
    System.out.println("=== CABEÇALHO PDF ===");
    System.out.println("Documento: Relatório.pdf");
    System.out.println("Formato: Portable Document Format");
  }

  @Override
  public void gerarCorpo() {
    System.out.println("\n--- CORPO DO RELATÓRIO PDF ---");
    System.out.println("Conteúdo formatado em PDF");
    System.out.println("Com gráficos e tabelas vetorizadas");
  }

  @Override
  public void gerarRodape() {
    System.out.println("\n--- RODAPÉ PDF ---");
    System.out.println("Página 1 de 1");
    System.out.println("Gerado em: " + java.time.LocalDateTime.now());
  }
}

/**
 * Fábrica concreta para relatórios CSV.
 */
class CSVRelatorioFactory implements RelatorioFactory {
  @Override
  public void gerarCabecalho() {
    System.out.println("Nome,Valor,Data,Status");
  }

  @Override
  public void gerarCorpo() {
    System.out.println("Produto A,1500.00,2025-01-15,Ativo");
    System.out.println("Produto B,2300.00,2025-01-16,Ativo");
    System.out.println("Produto C,890.00,2025-01-17,Pendente");
  }

  @Override
  public void gerarRodape() {
    System.out.println("Total de registros: 3");
  }
}

/**
 * Fábrica concreta para relatórios JSON.
 */
class JSONRelatorioFactory implements RelatorioFactory {
  @Override
  public void gerarCabecalho() {
    System.out.println("{");
    System.out.println("  \"relatorio\": {");
    System.out.println("    \"tipo\": \"JSON\",");
    System.out.println("    \"versao\": \"1.0\",");
  }

  @Override
  public void gerarCorpo() {
    System.out.println("    \"dados\": [");
    System.out.println("      {\"id\": 1, \"nome\": \"Produto A\", \"valor\": 1500.00},");
    System.out.println("      {\"id\": 2, \"nome\": \"Produto B\", \"valor\": 2300.00},");
    System.out.println("      {\"id\": 3, \"nome\": \"Produto C\", \"valor\": 890.00}");
    System.out.println("    ],");
  }

  @Override
  public void gerarRodape() {
    System.out.println("    \"total_registros\": 3");
    System.out.println("  }");
    System.out.println("}");
  }
}

/**
 * Enum para tipos de relatório.
 * Melhora type-safety e facilita manutenção.
 */
enum TipoRelatorio {
  PDF, CSV, JSON
}

public class RelatorioService {
  /**
   * ABORDAGEM REFATORADA: Abstract Factory + Switch.
   * Switch é usado APENAS para criação, não para lógica de negócio.
   * Sem default para forçar tratamento de todos os casos.
   */
  public void gerarRelatorio(TipoRelatorio tipo) {
    RelatorioFactory factory = criarFactory(tipo);
    System.out.println("\n" + "=".repeat(60));
    System.out.println("Gerando relatório " + tipo + "...");
    System.out.println("=".repeat(60));
    factory.gerar();
    System.out.println("=".repeat(60));
  }

  /**
   * Factory Method: Cria a fábrica apropriada com base no tipo.
   * Switch exaustivo (sem default) para garantir cobertura completa.
   */
  private RelatorioFactory criarFactory(TipoRelatorio tipo) {
    switch (tipo) {
      case PDF:
        return new PDFRelatorioFactory();
      case CSV:
        return new CSVRelatorioFactory();
      case JSON:
        return new JSONRelatorioFactory();
    }
    throw new IllegalArgumentException("Tipo desconhecido: " + tipo);
  }

  /**
   * VERSÃO ORIGINAL (mantida para efeito de comparação).
   * Problemas: Ifs aninhados, lógica misturada, difícil extensão.
   */
  public void gerarRelatorioOriginal(String tipo) {
    if (tipo.equals("PDF")) {
      System.out.println("Gerando relatório em PDF...");
      // gerarCabecalhoPDF();
      // gerarCorpoPDF();
      // gerarRodapePDF();
    } else if (tipo.equals("CSV")) {
      System.out.println("Gerando relatório em CSV...");
      // gerarCabecalhoCSV();
      // gerarCorpoCSV();
      // gerarRodapeCSV();
    } else if (tipo.equals("JSON")) {
      System.out.println("Gerando relatório em JSON...");
      // gerarCabecalhoJSON();
      // gerarCorpoJSON();
      // gerarRodapeJSON();
    } else {
      System.out.println("Tipo de relatório desconhecido");
    }
  }

  public static void main(String[] args) {
    RelatorioService service = new RelatorioService();

    System.out.println("Exercício 7 - Abstract Factory Pattern\n");

    // Gerar todos os tipos de relatórios
    for (TipoRelatorio tipo : TipoRelatorio.values()) {
      service.gerarRelatorio(tipo);
    }

    System.out.println("\n" + "=".repeat(70));
    System.out.println("PROBLEMAS DO CÓDIGO ORIGINAL:");
    System.out.println("=".repeat(70));
    System.out.println("1. IFS EXCESSIVOS");
    System.out.println("   → Múltiplas condições aninhadas dificultam leitura");
    System.out.println();
    System.out.println("2. LÓGICA MISTURADA");
    System.out.println("   → Controle de fluxo + lógica de negócio no mesmo lugar");
    System.out.println();
    System.out.println("3. VIOLAÇÃO DO OPEN/CLOSED");
    System.out.println("   → Adicionar novo formato requer modificar método existente");
    System.out.println();
    System.out.println("4. BAIXA COESÃO");
    System.out.println("   → Um método conhece detalhes de todos os formatos");
    System.out.println();
    System.out.println("5. DIFÍCIL TESTAR");
    System.out.println("   → Impossível testar formatos isoladamente");

    System.out.println("\n" + "=".repeat(70));
    System.out.println("BENEFÍCIOS DO ABSTRACT FACTORY:");
    System.out.println("=".repeat(70));
    System.out.println("✓ Separação de responsabilidades: Cada factory cuida de um formato");
    System.out.println("✓ Open/Closed: Adicionar formato = criar nova factory");
    System.out.println("✓ Testabilidade: Cada factory testável isoladamente");
    System.out.println("✓ Template Method: Algoritmo de geração centralizado");
    System.out.println("✓ Switch para criação: Uso apropriado para factory selection");
    System.out.println("✓ Type-safety: Enum ao invés de Strings");
    System.out.println("✓ Polimorfismo: Comportamento via interface\n");

    System.out.println("=".repeat(70));
    System.out.println("USO APROPRIADO DE SWITCH:");
    System.out.println("=".repeat(70));
    System.out.println("Switch é APROPRIADO quando:");
    System.out.println("• Usado apenas para CRIAÇÃO/SELEÇÃO de objetos");
    System.out.println("• Não contém lógica de negócio complexa");
    System.out.println("• Exaustivo (sem default) com enums");
    System.out.println("• Centralizado em um único ponto (Factory Method)");
    System.out.println();
    System.out.println("Switch é INAPROPRIADO quando:");
    System.out.println("• Contém lógica de negócio complexa em cada case");
    System.out.println("• Espalhado por múltiplos lugares do código");
    System.out.println("• Usado com strings ao invés de enums");
    System.out.println("• Possui default que oculta casos não tratados\n");
  }
}

/*
 * ABSTRACT FACTORY PATTERN:
 * ========================
 * 
 * PROPÓSITO:
 * - Fornecer interface para criar famílias de objetos relacionados
 * - Sem especificar suas classes concretas
 * 
 * COMPONENTES:
 * 1. AbstractFactory (interface): RelatorioFactory
 * 2. ConcreteFactories: PDFRelatorioFactory, CSVRelatorioFactory,
 * JSONRelatorioFactory
 * 3. AbstractProduct (implícito): Os métodos da interface
 * 4. Client: RelatorioService
 * 
 * QUANDO USAR:
 * - Sistema precisa ser independente de como produtos são criados
 * - Sistema configurado com múltiplas famílias de produtos
 * - Família de produtos relacionados deve ser usada em conjunto
 * - Quer ocultar implementações, mostrando apenas interfaces
 * 
 * COMBINAÇÃO COM SWITCH:
 * - Switch é usado APENAS no Factory Method (criarFactory)
 * - Não contém lógica de negócio, apenas criação de objetos
 * - Exaustivo (sem default) para garantir tratamento completo
 * - Toda lógica complexa está nas factories individuais
 */