// Exercício 5: Código com Muitos Ifs Aninhados
// Problema: Complexidade ciclomática alta, difícil manutenção
// Solução: Early returns, extração de métodos, classes de critérios

class CriteriosCliente {
  private static final int IDADE_SENIOR = 60;
  private static final double RENDA_ALTA_SENIOR = 5000.0;
  private static final double RENDA_ALTA_JOVEM = 7000.0;
  private static final int COMPRAS_PREMIUM_SENIOR = 10;
  private static final int COMPRAS_PREMIUM_JOVEM = 20;

  private final int idade;
  private final double renda;
  private final int compras;

  public CriteriosCliente(int idade, double renda, int compras) {
    this.idade = idade;
    this.renda = renda;
    this.compras = compras;
  }

  public boolean isSenior() {
    return idade > IDADE_SENIOR;
  }

  public boolean isJovem() {
    return !isSenior();
  }

  public boolean temRendaAltaSenior() {
    return renda > RENDA_ALTA_SENIOR;
  }

  public boolean temRendaAltaJovem() {
    return renda > RENDA_ALTA_JOVEM;
  }

  public boolean isComprasPremiumSenior() {
    return compras > COMPRAS_PREMIUM_SENIOR;
  }

  public boolean isComprasPremiumJovem() {
    return compras > COMPRAS_PREMIUM_JOVEM;
  }
}

public class ClienteClassificador {
  /**
   * ABORDAGEM REFATORADA: Métodos separados com early returns.
   * Elimina aninhamento excessivo e melhora legibilidade.
   */
  public String classificarCliente(int idade, double renda, int compras, String localizacao) {
    CriteriosCliente criterios = new CriteriosCliente(idade, renda, compras);

    if (criterios.isSenior()) {
      return classificarClienteSenior(criterios);
    }
    return classificarClienteJovem(criterios);
  }

  private String classificarClienteSenior(CriteriosCliente criterios) {
    // Early return: reduz aninhamento
    if (!criterios.temRendaAltaSenior()) {
      return "Cliente Sênior Baixa Renda";
    }

    if (criterios.isComprasPremiumSenior()) {
      return "Cliente Premium Sênior";
    }

    return "Cliente Regular Sênior";
  }

  private String classificarClienteJovem(CriteriosCliente criterios) {
    if (!criterios.temRendaAltaJovem()) {
      return "Cliente Jovem Baixa Renda";
    }

    if (criterios.isComprasPremiumJovem()) {
      return "Cliente Premium Jovem";
    }

    return "Cliente Regular Jovem";
  }

  /**
   * VERSÃO ORIGINAL (mantida para comparação):
   */
  public String classificarClienteOriginal(int idade, double renda, int compras, String localizacao) {
    if (idade > 60) {
      if (renda > 5000) {
        if (compras > 10) {
          return "Cliente Premium Sênior";
        } else {
          return "Cliente Regular Sênior";
        }
      } else {
        return "Cliente Sênior Baixa Renda";
      }
    } else {
      if (renda > 7000) {
        if (compras > 20) {
          return "Cliente Premium Jovem";
        } else {
          return "Cliente Regular Jovem";
        }
      } else {
        return "Cliente Jovem Baixa Renda";
      }
    }
  }

  public static void main(String[] args) {
    ClienteClassificador classificador = new ClienteClassificador();

    System.out.println("Exercício 5 - Reduzindo Ifs Aninhados\n");
    System.out.println("=".repeat(70));

    // Casos de teste
    Object[][] casos = {
        { 65, 6000.0, 15, "Cliente Premium Sênior" },
        { 65, 6000.0, 5, "Cliente Regular Sênior" },
        { 65, 4000.0, 15, "Cliente Sênior Baixa Renda" },
        { 35, 8000.0, 25, "Cliente Premium Jovem" },
        { 35, 8000.0, 10, "Cliente Regular Jovem" },
        { 35, 5000.0, 25, "Cliente Jovem Baixa Renda" }
    };

    for (Object[] caso : casos) {
      int idade = (int) caso[0];
      double renda = (double) caso[1];
      int compras = (int) caso[2];
      String esperado = (String) caso[3];

      String resultado = classificador.classificarCliente(idade, renda, compras, "BR");
      String status = resultado.equals(esperado) ? "✓" : "✗";

      System.out.printf("%s Idade: %d, Renda: %.0f, Compras: %d → %s%n",
          status, idade, renda, compras, resultado);
    }

    System.out.println("\n" + "=".repeat(70));
    System.out.println("PROBLEMAS DO CÓDIGO ALTAMENTE ANINHADO:");
    System.out.println("=".repeat(70));
    System.out.println("1. COMPLEXIDADE COGNITIVA ALTA");
    System.out.println("   → Difícil entender a lógica e fluxo de execução");
    System.out.println();
    System.out.println("2. DIFÍCIL MANUTENÇÃO");
    System.out.println("   → Adicionar nova regra requer alteração em múltiplos níveis");
    System.out.println();
    System.out.println("3. DIFÍCIL TESTAR");
    System.out.println("   → Muitos caminhos de execução para cobrir");
    System.out.println();
    System.out.println("4. PROPENSO A BUGS");
    System.out.println("   → Fácil errar ao modificar condições aninhadas");
    System.out.println();
    System.out.println("5. VALORES MÁGICOS");
    System.out.println("   → Números hardcoded (60, 5000, 10) sem contexto");

    System.out.println("\n" + "=".repeat(70));
    System.out.println("MELHORIAS APLICADAS:");
    System.out.println("=".repeat(70));
    System.out.println("✓ Extração de classe CriteriosCliente (encapsula lógica)");
    System.out.println("✓ Constantes nomeadas (elimina valores mágicos)");
    System.out.println("✓ Métodos extraídos por responsabilidade");
    System.out.println("✓ Early returns (reduz aninhamento)");
    System.out.println("✓ Guard clauses (condições de saída antecipadas)");
    System.out.println("✓ Complexidade ciclomática reduzida");
    System.out.println("✓ Código mais testável e manutenível\n");
  }
}

/*
 * ANÁLISE DE COMPLEXIDADE:
 * =======================
 * 
 * CÓDIGO ORIGINAL:
 * - Níveis de aninhamento: 4
 * - Linhas por método: 18
 * - Testabilidade: Baixa
 * 
 * CÓDIGO REFATORADO:
 * - Níveis de aninhamento: 2
 * - Linhas por método: 5-8
 * - Testabilidade: Alta
 * 
 * TÉCNICAS APLICADAS:
 * 1. Extract Method: Dividir em métodos menores
 * 2. Extract Class: CriteriosCliente encapsula lógica de critérios
 * 3. Replace Magic Number with Constant
 * 4. Replace Nested Conditional with Guard Clauses
 * 5. Early Return Pattern
 */