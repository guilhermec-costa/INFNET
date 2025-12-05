package com.infnet.TP3.domain.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * EXERCÍCIO 1: Sprint (entidade imutável)
 *
 * Representa um Sprint no desenvolvimento ágil. É imutável para garantir
 * consistência e segurança em ambientes concorrentes.
 *
 * EXERCÍCIO 4: Tipos de dados usados
 * - UUID: identificador único
 * - LocalDate: datas de início/fim
 * - LocalDateTime: timestamps de criação/atualização
 * - List<Task>: relação 1-N com tarefas
 */
public final class Sprint {

  private final UUID id;
  private final String name;
  private final LocalDate startDate;
  private final LocalDate endDate;
  private final LocalDateTime createdAt;
  private final LocalDateTime updatedAt;
  private final List<Task> tasks;

  private Sprint(UUID id, String name, LocalDate startDate, LocalDate endDate,
      LocalDateTime createdAt, LocalDateTime updatedAt, List<Task> tasks) {
    this.id = id;
    this.name = name;
    this.startDate = startDate;
    this.endDate = endDate;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.tasks = new ArrayList<>(tasks);
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public List<Task> getTasks() {
    return Collections.unmodifiableList(tasks);
  }

  /**
   * Retorna NOVO sprint com tarefa adicionada
   */
  public Sprint adicionarTarefa(Task task) {
    List<Task> newTasks = new ArrayList<>(this.tasks);
    newTasks.add(task);
    return new Sprint(this.id, this.name, this.startDate, this.endDate,
        this.createdAt, LocalDateTime.now(), newTasks);
  }

  /**
   * Retorna NOVO sprint sem a tarefa especificada
   */
  public Sprint removerTarefa(UUID taskId) {
    List<Task> newTasks = new ArrayList<>();
    for (Task task : this.tasks) {
      if (!task.getId().equals(taskId)) {
        newTasks.add(task);
      }
    }
    return new Sprint(this.id, this.name, this.startDate, this.endDate,
        this.createdAt, LocalDateTime.now(), newTasks);
  }

  /**
   * Verifica se o sprint está ativo na data atual
   */
  public boolean isActive() {
    LocalDate today = LocalDate.now();
    return !today.isBefore(startDate) && !today.isAfter(endDate);
  }

  public static class Builder {
    private UUID id = UUID.randomUUID();
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime createdAt = LocalDateTime.now();
    private List<Task> tasks = new ArrayList<>();

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder startDate(LocalDate startDate) {
      this.startDate = startDate;
      return this;
    }

    public Builder endDate(LocalDate endDate) {
      this.endDate = endDate;
      return this;
    }

    public Builder tasks(List<Task> tasks) {
      this.tasks = new ArrayList<>(tasks);
      return this;
    }

    public Sprint build() {
      if (name == null || name.trim().isEmpty()) {
        throw new IllegalArgumentException("Nome do sprint é obrigatório");
      }
      if (startDate == null) {
        throw new IllegalArgumentException("Data de início é obrigatória");
      }
      if (endDate == null) {
        throw new IllegalArgumentException("Data de fim é obrigatória");
      }
      if (endDate.isBefore(startDate)) {
        throw new IllegalArgumentException("Data de fim deve ser posterior à data de início");
      }
      return new Sprint(id, name, startDate, endDate, createdAt, createdAt, tasks);
    }
  }

  public static Builder builder() {
    return new Builder();
  }
}