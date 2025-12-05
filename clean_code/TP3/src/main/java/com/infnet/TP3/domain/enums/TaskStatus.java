package com.infnet.TP3.domain.enums;

/**
 * EXERCÍCIO 4: TaskStatus (enum)
 *
 * Representa os estados possíveis de uma tarefa.
 *
 * Por que usar enum?
 * - Garante type-safety
 * - Impede valores inválidos
 * - Facilita refatoração e manutenção
 * - Código mais legível (com autocomplete da IDE)
 * - Permite comportamentos específicos por status no futuro
 */

public enum TaskStatus {
  TODO("A Fazer", "Tarefa ainda não iniciada"),
  IN_PROGRESS("Em Progresso", "Tarefa em desenvolvimento"),
  DONE("Concluída", "Tarefa finalizada");

  private final String displayName;
  private final String description;

  TaskStatus(String displayName, String description) {
    this.displayName = displayName;
    this.description = description;
  }

  public String getDisplayName() {
    return displayName;
  }

  public String getDescription() {
    return description;
  }

  /**
   * Permite transições válidas de status
   */
  public boolean canTransitionTo(TaskStatus newStatus) {
    return switch (this) {
      case TODO -> newStatus == IN_PROGRESS;
      case IN_PROGRESS -> newStatus == DONE || newStatus == TODO;
      case DONE -> false;
    };
  }
}