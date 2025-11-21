// Exercício 1: Nomeação de Variáveis e Funções
// Antes: Nomes indecifráveis (A, a, x, y, z)
// Depois: Nomes claros e descritivos

public class CalculadoraNumeros {
  /**
   * Calcula o dobro da soma de dois números.
   * 
   * Explicação da escolha dos nomes:
   * - CalculadoraNumeros: Nome da classe indica claramente que realiza cálculos
   * numéricos
   * - calcularDobroDaSoma: Nome do método descreve exatamente o que faz
   * - primeiroNumero/segundoNumero: Parâmetros descritivos indicam sua
   * posição/papel
   * - somaDosNumeros: Variável intermediária que armazena a soma antes da
   * multiplicação
   * 
   * @param primeiroNumero O primeiro número a ser somado
   * @param segundoNumero  O segundo número a ser somado
   * @return O dobro da soma dos dois números
   */
  public static int calcularDobroDaSoma(int primeiroNumero, int segundoNumero) {
    int somaDosNumeros = primeiroNumero + segundoNumero;
    return somaDosNumeros * 2;
  }

  public static void main(String[] args) {
    int resultado = calcularDobroDaSoma(5, 10);
    System.out.println("Exercício 1 - Nomeação Clara");
    System.out.println("Dobro da soma de 5 + 10 = " + resultado);
    System.out.println("Nomes claros tornam o código autoexplicativo\n");
  }
}

/*
 * EXPLICAÇÃO DAS MELHORIAS:
 * 1. Classe "A" → "CalculadoraNumeros": Indica o propósito da classe
 * 2. Método "a" → "calcularDobroDaSoma": Descreve a operação realizada
 * 3. Parâmetros "x, y" → "primeiroNumero, segundoNumero": Papel claro de cada
 * parâmetro
 * 4. Variável "z" → "somaDosNumeros": Indica o que a variável armazena
 * 5. Adicionada documentação JavaDoc para maior clareza
 */