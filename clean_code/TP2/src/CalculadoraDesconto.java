// Exercício 2: Código Autoexplicativo e Evitando Valores Mágicos
// Problema: Valores "mágicos" (1000, 0.9) não são claros
// Solução: Constantes nomeadas + correção da lógica de desconto

public class CalculadoraDesconto {
  // Constantes nomeadas eliminam valores mágicos
  private static final double VALOR_MINIMO_PARA_DESCONTO = 1000.0;
  private static final double PERCENTUAL_DESCONTO = 0.10; // 10% de desconto
  private static final double FATOR_CALCULO_DESCONTO = 1.0 - PERCENTUAL_DESCONTO; // 0.90

  /**
   * Calcula o preço final com desconto aplicado se elegível.
   * 
   * Regra de negócio: Clientes que compram produtos no valor de 1000 reais
   * ou mais recebem 10% de desconto.
   * 
   * @param precoOriginal O preço original do produto
   * @return O preço final com desconto aplicado (se elegível)
   */
  public double calcularPrecoComDesconto(double precoOriginal) {
    if (isElegivelParaDesconto(precoOriginal)) {
      return aplicarDesconto(precoOriginal);
    }
    return precoOriginal;
  }

  /**
   * Verifica se o valor é elegível para desconto.
   * Usa >= para incluir o valor mínimo (1000) na elegibilidade.
   */
  private boolean isElegivelParaDesconto(double preco) {
    return preco >= VALOR_MINIMO_PARA_DESCONTO;
  }

  /**
   * Aplica o desconto ao preço.
   */
  private double aplicarDesconto(double preco) {
    return preco * FATOR_CALCULO_DESCONTO;
  }

  public static void main(String[] args) {
    CalculadoraDesconto calculadora = new CalculadoraDesconto();

    System.out.println("Exercício 2 - Evitando Valores Mágicos");
    System.out.println("Regra: Desconto de 10% para compras >= R$ 1000,00\n");

    double[] precosTeste = { 999.0, 1000.0, 1500.0 };

    for (double preco : precosTeste) {
      double precoFinal = calculadora.calcularPrecoComDesconto(preco);
      System.out.printf("Preço original: R$ %.2f → Preço final: R$ %.2f%n",
          preco, precoFinal);
    }

    System.out.println("\nMelhorias aplicadas:");
    System.out.println("✓ Constantes nomeadas (sem valores mágicos)");
    System.out.println("✓ Correção do bug: >= ao invés de >");
    System.out.println("✓ Métodos extraídos para maior clareza");
    System.out.println("✓ Regra de negócio explícita na documentação\n");
  }
}

/*
 * MELHORIAS APLICADAS:
 * 1. Valores mágicos (1000, 0.9) substituídos por constantes nomeadas
 * 2. Correção do bug: operador > mudado para >= (inclui valor exato de 1000)
 * 3. Extração de métodos isElegivelParaDesconto() e aplicarDesconto()
 * 4. Documentação clara da regra de negócio
 * 5. Código autoexplicativo que não requer comentários adicionais
 */