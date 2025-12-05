package com.infnet.TP3.domain.valueObjects;

import java.util.Objects;

/**
 * EXERCÍCIO 3: TaskItem (objeto menor imutável com validação)
 *
 * Representa um item/checklist dentro de uma tarefa.
 *
 * Características:
 * - Classe final e atributos final
 * - Sem setters
 * - Validações no construtor
 * - Métodos retornam novos objetos
 *
 * Benefícios da imutabilidade:
 * - Auditoria fácil (cada mudança gera novo objeto)
 * - Sem conflitos em concorrência
 * - Cache seguro (objeto nunca muda)
 * - Código simples e sem necessidade de sincronização
 *
 * Exemplo conceitual:
 * item1 = TaskItem.of("Tarefa", 5, false)
 * item2 = item1.withQuantity(10)
 * // item1 continua com 5, item2 tem 10 — objetos diferentes
 */
public final class TaskItem {

  private final String code;
  private final int quantity;
  private final Money estimatedCost;
  private final boolean completed;

  private TaskItem(String code, int quantity, Money estimatedCost, boolean completed) {
    this.code = code;
    this.quantity = quantity;
    this.estimatedCost = estimatedCost;
    this.completed = completed;
  }

  /**
   * EXERCÍCIO 3: Factory method com validação
   *
   * Garante criação apenas de objetos válidos:
   * - Código não vazio
   * - Quantidade positiva
   * - Custo não nulo
   */

  public static TaskItem of(String code, int quantity, Money estimatedCost) {
    if (code == null || code.trim().isEmpty()) {
      throw new IllegalArgumentException("Código é obrigatório");
    }
    if (quantity <= 0) {
      throw new IllegalArgumentException(
          "Quantidade deve ser positiva: " + quantity);
    }
    if (estimatedCost == null) {
      throw new IllegalArgumentException("Custo estimado é obrigatório");
    }

    return new TaskItem(code.trim(), quantity, estimatedCost, false);
  }

  public String getCode() {
    return code;
  }

  public int getQuantity() {
    return quantity;
  }

  public Money getEstimatedCost() {
    return estimatedCost;
  }

  public boolean isCompleted() {
    return completed;
  }

  /**
   * EXERCÍCIO 3: Métodos de "atualização" que retornam novos objetos
   * 
   * Cada método retorna uma NOVA instância com o valor modificado.
   * O objeto original permanece completamente inalterado.
   */
  public TaskItem withQuantity(int newQuantity) {
    if (newQuantity <= 0) {
      throw new IllegalArgumentException("Quantidade deve ser positiva");
    }
    return new TaskItem(this.code, newQuantity, this.estimatedCost, this.completed);
  }

  public TaskItem withEstimatedCost(Money newCost) {
    if (newCost == null) {
      throw new IllegalArgumentException("Custo não pode ser nulo");
    }
    return new TaskItem(this.code, this.quantity, newCost, this.completed);
  }

  public TaskItem markAsCompleted() {
    if (this.completed) {
      return this;
    }
    return new TaskItem(this.code, this.quantity, this.estimatedCost, true);
  }

  public TaskItem markAsIncomplete() {
    if (!this.completed) {
      return this;
    }
    return new TaskItem(this.code, this.quantity, this.estimatedCost, false);
  }

  /**
   * EXERCÍCIO 3: Controle de concorrência com objetos imutáveis.
   *
   * Cenário: duas threads editam o mesmo TaskItem simultaneamente.
   * Cada operação cria uma nova versão:
   * - original: quantidade=5, completed=false
   * - modificado1: quantidade=10, completed=false
   * - modificado2: quantidade=5, completed=true
   *
   * Benefício: sem conflitos, cada thread trabalha com sua própria versão.
   * Com objetos mutáveis, o estado final seria imprevisível.
   */

  /**
   * Calcula custo total (quantidade × custo unitário)
   * Retorna novo objeto Money (também imutável)
   */
  public Money getTotalCost() {
    return estimatedCost.multiply(java.math.BigDecimal.valueOf(quantity));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    TaskItem taskItem = (TaskItem) o;
    return quantity == taskItem.quantity &&
        completed == taskItem.completed &&
        code.equals(taskItem.code) &&
        estimatedCost.equals(taskItem.estimatedCost);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, quantity, estimatedCost, completed);
  }

  @Override
  public String toString() {
    return String.format("TaskItem[code='%s', qty=%d, cost=%s, completed=%s]",
        code, quantity, estimatedCost, completed);
  }
}