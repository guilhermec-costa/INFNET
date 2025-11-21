// Exercício 4: Evitando Mutação de Parâmetros
// Problema: Modificação direta do objeto causa efeitos colaterais
// Solução: Imutabilidade e criação de novos objetos

class Produto {
  private final String nome;
  private final double preco;

  public Produto(String nome, double preco) {
    this.nome = nome;
    this.preco = preco;
  }

  public String getNome() {
    return nome;
  }

  public double getPreco() {
    return preco;
  }

  /**
   * Cria um NOVO produto com desconto aplicado.
   * O produto original permanece inalterado (imutável).
   */
  public Produto aplicarDesconto(double valorDesconto) {
    double novoPreco = this.preco - valorDesconto;
    return new Produto(this.nome, novoPreco);
  }

  @Override
  public String toString() {
    return String.format("%s: R$ %.2f", nome, preco);
  }
}

public class AjustePreco {
  private static final double VALOR_DESCONTO = 10.0;

  /**
   * ABORDAGEM ERRADA
   * public void aplicarDescontoErrado(Produto produto) {
   * produto.preco -= 10; // PROBLEMA: Mutação direta
   * }
   * 
   * PROBLEMAS CAUSADOS:
   * 1. Efeitos colaterais: Todos os lugares que referenciam o objeto são afetados
   * 2. Dificulta rastreamento: Não se sabe onde o objeto foi modificado
   * 3. Bugs em relatórios: Valores inconsistentes em diferentes partes do sistema
   * 4. Problemas de concorrência: Múltiplas threads podem modificar
   * simultaneamente
   */

  /**
   * ABORDAGEM CORRETA: Retorna um novo produto com desconto aplicado.
   * O produto original permanece inalterado.
   */
  public Produto aplicarDesconto(Produto produto) {
    return produto.aplicarDesconto(VALOR_DESCONTO);
  }

  public static void main(String[] args) {
    System.out.println("Exercício 4 - Evitando Mutação de Parâmetros\n");
    System.out.println("=".repeat(60));

    // Criando produto original
    Produto produtoOriginal = new Produto("Notebook", 3000.0);
    System.out.println("Produto original: " + produtoOriginal);

    // Aplicando desconto (sem mutar o original)
    AjustePreco ajuste = new AjustePreco();
    Produto produtoComDesconto = ajuste.aplicarDesconto(produtoOriginal);

    System.out.println("\nApós aplicar desconto:");
    System.out.println("Produto original: " + produtoOriginal + " (inalterado)");
    System.out.println("Produto com desconto: " + produtoComDesconto);

    System.out.println("\n" + "=".repeat(60));
    System.out.println("IMPACTOS NEGATIVOS DA MUTAÇÃO DIRETA:");
    System.out.println("=".repeat(60));
    System.out.println("1. RELATÓRIOS FINANCEIROS INCORRETOS");
    System.out.println("   → Preços alterados sem controle causam erros de faturamento");
    System.out.println();
    System.out.println("2. ERROS EM MÚLTIPLAS COMPRAS");
    System.out.println("   → Mesmo produto no carrinho pode ter preços diferentes");
    System.out.println();
    System.out.println("3. PROBLEMAS EM LOGS E AUDITORIAS");
    System.out.println("   → Histórico de preços é perdido ou corrompido");
    System.out.println();
    System.out.println("4. BUGS DIFÍCEIS DE RASTREAR");
    System.out.println("   → Não se sabe onde/quando o objeto foi modificado");
    System.out.println();
    System.out.println("5. PROBLEMAS DE CONCORRÊNCIA");
    System.out.println("   → Múltiplas threads modificando o mesmo objeto");

    System.out.println("\n" + "=".repeat(60));
    System.out.println("BENEFÍCIOS DA IMUTABILIDADE:");
    System.out.println("=".repeat(60));
    System.out.println("✓ Segurança: Objeto original nunca muda");
    System.out.println("✓ Rastreabilidade: Histórico preservado");
    System.out.println("✓ Thread-safe: Imutável = seguro para concorrência");
    System.out.println("✓ Previsibilidade: Comportamento consistente");
    System.out.println("✓ Testabilidade: Testes mais confiáveis\n");
  }
}

/*
 * DISCUSSÃO DETALHADA:
 * ===================
 * 
 * PROBLEMA DA MUTAÇÃO:
 * - Quando modificamos diretamente um objeto passado como parâmetro, todos os
 * lugares
 * que possuem referência para esse objeto são afetados
 * - Isso quebra o princípio de menor surpresa: quem chama o método não espera
 * que o objeto seja modificado
 * 
 * IMPORTÂNCIA DE EVITAR MUDANÇAS DIRETAS:
 * 1. Previsibilidade: Código se comporta como esperado
 * 2. Manutenibilidade: Mais fácil entender e modificar
 * 3. Debugging: Mais fácil rastrear bugs
 * 4. Auditoria: Histórico de valores é preservado
 * 5. Concorrência: Objetos imutáveis são thread-safe
 * 
 * SOLUÇÃO:
 * - Tornar objetos imutáveis (campos final, sem setters)
 * - Métodos que "modificam" retornam NOVOS objetos
 * - Seguir o padrão functional programming onde aplicável
 */