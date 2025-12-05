package com.infnet.TP3.domain.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * EXERCÍCIO 1: Entidade Project (imutável)
 *
 * Representa um projeto ágil e é IMUTÁVEL para garantir segurança e
 * previsibilidade.
 *
 * Benefícios da imutabilidade:
 * - Thread-safe por natureza (sem race conditions)
 * - Sem efeitos colaterais (estado nunca muda)
 * - Dados sempre consistentes
 * - Seguro para cache
 * - Facilita histórico/undo (cada mudança gera novo objeto)
 * - Mais fácil de testar
 *
 * Trade-offs:
 * - Mais uso de memória
 * - Pode ser menos eficiente em muitas modificações sequenciais
 */
public final class Project {

  private final UUID id;
  private final String name;
  private final String description;
  private final LocalDateTime createdAt;
  private final LocalDateTime updatedAt;
  private final List<Sprint> sprints;

  /**
   * Construtor privado - use o Builder para criar instâncias
   */
  private Project(UUID id, String name, String description,
      LocalDateTime createdAt, LocalDateTime updatedAt, List<Sprint> sprints) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    // cópia defensiva para imutabilidade
    this.sprints = new ArrayList<>(sprints);
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  /**
   * Retorna cópia imutável da lista de sprints
   */
  public List<Sprint> getSprints() {
    return Collections.unmodifiableList(sprints);
  }

  /**
   * Ao invés de modificar, retorna um NOVO projeto com o sprint adicionado.
   * O projeto original permanece inalterado.
   */
  public Project adicionarSprint(Sprint sprint) {
    List<Sprint> newSprints = new ArrayList<>(this.sprints);
    newSprints.add(sprint);
    return new Project(this.id, this.name, this.description,
        this.createdAt, LocalDateTime.now(), newSprints);
  }

  /**
   * Retorna um NOVO projeto sem o sprint especificado
   */
  public Project removerSprint(UUID sprintId) {
    List<Sprint> newSprints = new ArrayList<>();
    for (Sprint sprint : this.sprints) {
      if (!sprint.getId().equals(sprintId)) {
        newSprints.add(sprint);
      }
    }
    return new Project(this.id, this.name, this.description,
        this.createdAt, LocalDateTime.now(), newSprints);
  }

  public List<Sprint> listarSprints() {
    return this.sprints;
  }

  /**
   * Retorna NOVO projeto com nome e descrição atualizados
   */
  public Project atualizar(String newName, String newDescription) {
    return new Project(this.id, newName, newDescription,
        this.createdAt, LocalDateTime.now(), this.sprints);
  }

  /**
   * Builder Pattern para construção fluente e clara
   */
  public static class Builder {
    private UUID id = UUID.randomUUID();
    private String name;
    private String description;
    private LocalDateTime createdAt = LocalDateTime.now();
    private List<Sprint> sprints = new ArrayList<>();

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Builder sprints(List<Sprint> sprints) {
      this.sprints = new ArrayList<>(sprints);
      return this;
    }

    public Project build() {
      if (name == null || name.trim().isEmpty()) {
        throw new IllegalArgumentException("Nome do projeto é obrigatório");
      }
      return new Project(id, name, description, createdAt, createdAt, sprints);
    }
  }

  public static Builder builder() {
    return new Builder();
  }
}