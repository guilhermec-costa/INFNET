package com.infnet.TP3.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import com.infnet.TP3.domain.enums.TaskStatus;

/**
 * EXERCÍCIOS 1 e 3: Task (entidade imutável)
 *
 * Representa uma tarefa dentro de um Sprint. Demonstra imutabilidade em um
 * objeto menor do domínio.
 *
 * Benefícios da imutabilidade:
 * - Auditoria simples (cada mudança gera um novo objeto)
 * - Segurança em concorrência (nenhuma leitura sofre alterações inesperadas)
 * - Testes mais fáceis (métodos retornam novos objetos sem alterar o original)
 *
 * EXERCÍCIO 4: Tipos de dados usados
 * - UUID: identificador único
 * - String: título e descrição
 * - TaskStatus: enum para status válido
 * - User: responsável pela tarefa
 * - LocalDateTime: timestamps de criação/atualização
 */

public final class Task {

  private final UUID id;
  private final String title;
  private final String description;
  private final TaskStatus status;
  private final User assignee;
  private final LocalDateTime createdAt;
  private final LocalDateTime updatedAt;

  private Task(UUID id, String title, String description, TaskStatus status,
      User assignee, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.status = status;
    this.assignee = assignee;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public UUID getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public TaskStatus getStatus() {
    return status;
  }

  public User getAssignee() {
    return assignee;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  /**
   * EXERCÍCIO 2: Método sem efeitos colaterais
   * Método sem efeitos colaterais.
   *
   * Retorna uma nova tarefa com o responsável atribuído; o objeto original
   * permanece inalterado.
   *
   * Antes: modificava o estado interno (problemático em concorrência e auditoria)
   * Depois: cria novo objeto, garantindo segurança e rastreabilidade.
   */

  public Task atribuirResponsavel(User newAssignee) {
    if (newAssignee == null) {
      throw new IllegalArgumentException("Responsável não pode ser nulo");
    }
    return new Task(this.id, this.title, this.description, this.status,
        newAssignee, this.createdAt, LocalDateTime.now());
  }

  /**
   * EXERCÍCIO 2: Alteração de status sem efeitos colaterais
   * 
   * Alteração de status sem efeitos colaterais.
   *
   * Benefícios:
   * - Objeto original permanece inalterado (sem race conditions)
   * - Permite validações antes de criar novo objeto
   * - Facilita regras de transição de status
   * - Testável comparando objetos antes/depois
   */
  public Task alterarStatus(TaskStatus newStatus) {
    if (newStatus == null) {
      throw new IllegalArgumentException("Status não pode ser nulo");
    }
    if (!this.status.canTransitionTo(newStatus)) {
      throw new IllegalStateException(
          String.format("Transição inválida de %s para %s", this.status, newStatus));
    }
    return new Task(this.id, this.title, this.description, newStatus,
        this.assignee, this.createdAt, LocalDateTime.now());
  }

  /**
   * Retorna nova tarefa com título e descrição atualizados
   */
  public Task atualizar(String newTitle, String newDescription) {
    if (newTitle == null || newTitle.trim().isEmpty()) {
      throw new IllegalArgumentException("Título não pode ser vazio");
    }
    return new Task(this.id, newTitle, newDescription, this.status,
        this.assignee, this.createdAt, LocalDateTime.now());
  }

  public String exibirDetalhes() {
    return String.format(
        "Task[id=%s, title='%s', status=%s, assignee=%s, created=%s, updated=%s]",
        id, title, status,
        assignee != null ? assignee.getName() : "Não atribuído",
        createdAt, updatedAt);
  }

  /**
   * EXERCÍCIO 3: Builder com validação de dados
   * 
   * O Builder garante que apenas objetos válidos sejam criados.
   * Validações são feitas no método build(), antes da instanciação.
   */
  public static class Builder {
    private UUID id = UUID.randomUUID();
    private String title;
    private String description;
    private TaskStatus status = TaskStatus.TODO;
    private User assignee;
    private LocalDateTime createdAt = LocalDateTime.now();

    public Builder title(String title) {
      this.title = title;
      return this;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Builder status(TaskStatus status) {
      this.status = status;
      return this;
    }

    public Builder assignee(User assignee) {
      this.assignee = assignee;
      return this;
    }

    public Task build() {
      // EXERCÍCIO 3: Validação de dados na construção
      if (title == null || title.trim().isEmpty()) {
        throw new IllegalArgumentException("Título da tarefa é obrigatório");
      }
      if (status == null) {
        throw new IllegalArgumentException("Status da tarefa é obrigatório");
      }
      return new Task(id, title, description, status, assignee,
          createdAt, createdAt);
    }
  }

  public static Builder builder() {
    return new Builder();
  }
}