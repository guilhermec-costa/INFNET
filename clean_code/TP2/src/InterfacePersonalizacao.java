// Exercício 6: Personalização da Interface - Strategy Pattern
// Problema: Switch/case cria forte acoplamento e dificulta extensão
// Solução: Strategy Pattern para desacoplar e facilitar manutenção

import java.util.*;

enum Color {
  RED, WHITE, BLUE, BLACK, YELLOW, GREEN, GRAY
}

enum Nationality {
  DUTCH, GERMAN, BELGIAN, FRENCH, ITALIAN, UNCLASSIFIED
}

/**
 * Interface Strategy: Define o contrato para obtenção de cores da bandeira.
 */
interface FlagColorStrategy {
  List<Color> getColors();
}

// Implementações concretas de cada estratégia
class DutchFlagColors implements FlagColorStrategy {
  @Override
  public List<Color> getColors() {
    return Arrays.asList(Color.RED, Color.WHITE, Color.BLUE);
  }
}

class GermanFlagColors implements FlagColorStrategy {
  @Override
  public List<Color> getColors() {
    return Arrays.asList(Color.BLACK, Color.RED, Color.YELLOW);
  }
}

class BelgianFlagColors implements FlagColorStrategy {
  @Override
  public List<Color> getColors() {
    return Arrays.asList(Color.BLACK, Color.YELLOW, Color.RED);
  }
}

class FrenchFlagColors implements FlagColorStrategy {
  @Override
  public List<Color> getColors() {
    return Arrays.asList(Color.BLUE, Color.WHITE, Color.RED);
  }
}

class ItalianFlagColors implements FlagColorStrategy {
  @Override
  public List<Color> getColors() {
    return Arrays.asList(Color.GREEN, Color.WHITE, Color.RED);
  }
}

class UnclassifiedFlagColors implements FlagColorStrategy {
  @Override
  public List<Color> getColors() {
    return Collections.singletonList(Color.GRAY);
  }
}

/**
 * Factory para criar as estratégias corretas.
 * Centraliza a lógica de seleção.
 */
class FlagColorStrategyFactory {
  private static final Map<Nationality, FlagColorStrategy> strategies = new HashMap<>();

  static {
    strategies.put(Nationality.DUTCH, new DutchFlagColors());
    strategies.put(Nationality.GERMAN, new GermanFlagColors());
    strategies.put(Nationality.BELGIAN, new BelgianFlagColors());
    strategies.put(Nationality.FRENCH, new FrenchFlagColors());
    strategies.put(Nationality.ITALIAN, new ItalianFlagColors());
    strategies.put(Nationality.UNCLASSIFIED, new UnclassifiedFlagColors());
  }

  public static FlagColorStrategy getStrategy(Nationality nationality) {
    return strategies.getOrDefault(nationality, new UnclassifiedFlagColors());
  }
}

public class InterfacePersonalizacao {
  /**
   * ABORDAGEM REFATORADA: Usa Strategy Pattern.
   * Desacoplado, extensível e fácil de testar.
   */
  public List<Color> getFlagColors(Nationality nationality) {
    FlagColorStrategy strategy = FlagColorStrategyFactory.getStrategy(nationality);
    return strategy.getColors();
  }

  /**
   * VERSÃO ORIGINAL (mantida para comparação).
   * Problema: Fortemente acoplado, difícil de estender.
   */
  public List<Color> getFlagColorsOriginal(Nationality nationality) {
    List<Color> result;
    switch (nationality) {
      case DUTCH:
        result = Arrays.asList(Color.RED, Color.WHITE, Color.BLUE);
        break;
      case GERMAN:
        result = Arrays.asList(Color.BLACK, Color.RED, Color.YELLOW);
        break;
      case BELGIAN:
        result = Arrays.asList(Color.BLACK, Color.YELLOW, Color.RED);
        break;
      case FRENCH:
        result = Arrays.asList(Color.BLUE, Color.WHITE, Color.RED);
        break;
      case ITALIAN:
        result = Arrays.asList(Color.GREEN, Color.WHITE, Color.RED);
        break;
      case UNCLASSIFIED:
      default:
        result = Arrays.asList(Color.GRAY);
        break;
    }
    return result;
  }

  public static void main(String[] args) {
    InterfacePersonalizacao personalizacao = new InterfacePersonalizacao();

    System.out.println("Exercício 6 - Strategy Pattern para Bandeiras\n");
    System.out.println("=".repeat(60));

    // Testando todas as nacionalidades
    for (Nationality nationality : Nationality.values()) {
      List<Color> colors = personalizacao.getFlagColors(nationality);
      System.out.printf("%-15s → %s%n", nationality, colors);
    }

    System.out.println("\n" + "=".repeat(60));
    System.out.println("PROBLEMAS DA ABORDAGEM CONDICIONAL:");
    System.out.println("=".repeat(60));
    System.out.println("1. FORTE ACOPLAMENTO");
    System.out.println("   → Lógica de negócio misturada com código de controle");
    System.out.println();
    System.out.println("2. VIOLAÇÃO DO OPEN/CLOSED PRINCIPLE");
    System.out.println("   → Para adicionar nova nacionalidade, precisa modificar o método");
    System.out.println();
    System.out.println("3. DIFÍCIL TESTAR");
    System.out.println("   → Impossível testar casos individuais isoladamente");
    System.out.println();
    System.out.println("4. CÓDIGO REPETITIVO");
    System.out.println("   → Estrutura switch/case se repete em múltiplos lugares");
    System.out.println();
    System.out.println("5. BAIXA COESÃO");
    System.out.println("   → Um método conhece detalhes de todas as bandeiras");

    System.out.println("\n" + "=".repeat(60));
    System.out.println("BENEFÍCIOS DO STRATEGY PATTERN:");
    System.out.println("=".repeat(60));
    System.out.println("✓ Desacoplamento: Cada estratégia é independente");
    System.out.println("✓ Open/Closed: Adicionar nova nacionalidade = nova classe");
    System.out.println("✓ Testabilidade: Cada estratégia testável isoladamente");
    System.out.println("✓ Single Responsibility: Cada classe tem um propósito");
    System.out.println("✓ Fácil extensão: Sem modificar código existente");
    System.out.println("✓ Factory Pattern: Centraliza criação de estratégias");
    System.out.println("✓ Polimorfismo: Comportamento via interface, não condicionais\n");

    System.out.println("=".repeat(60));
    System.out.println("EXEMPLO DE EXTENSÃO:");
    System.out.println("=".repeat(60));
    System.out.println("Para adicionar Espanha:");
    System.out.println("1. Criar: class SpanishFlagColors implements FlagColorStrategy");
    System.out.println("2. Adicionar no Factory: strategies.put(SPANISH, new SpanishFlagColors())");
    System.out.println("3. ZERO modificações no código existente\n");
  }
}

/*
 * DISCUSSÃO DETALHADA:
 * ===================
 * 
 * PROBLEMA DO CÓDIGO ORIGINAL:
 * - Switch/case centraliza toda lógica de bandeiras
 * - Adicionar nova nacionalidade requer modificar o método
 * - Viola princípio Open/Closed (aberto para extensão, fechado para
 * modificação)
 * - Alto acoplamento entre código cliente e implementações específicas
 * 
 * STRATEGY PATTERN:
 * - Define família de algoritmos (estratégias)
 * - Encapsula cada um em uma classe separada
 * - Torna os algoritmos intercambiáveis
 * - Cliente usa interface, não implementação concreta
 * 
 * COMPONENTES:
 * 1. Strategy (interface): FlagColorStrategy
 * 2. Concrete Strategies: DutchFlagColors, GermanFlagColors, etc.
 * 3. Context: InterfacePersonalizacao
 * 4. Factory: FlagColorStrategyFactory (bonus para simplicidade)
 * 
 * QUANDO USAR:
 * - Múltiplas variações de um algoritmo
 * - Comportamento selecionado em runtime
 * - Eliminar condicionais complexas
 * - Isolar lógica de negócio específica
 */