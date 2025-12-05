package com.infnet.TP3.infra.repositories;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.infnet.TP3.domain.entities.Project;
import com.infnet.TP3.domain.repositories.ProjectRepository;

/**
 * EXERCÍCIO 5: Implementação concreta de ProjectRepository
 *
 * Implementação em memória usando ConcurrentHashMap.
 * Focada apenas na persistência — sem lógica de negócio.
 *
 * Vantagens:
 * - Fácil substituir por versões em PostgreSQL, MongoDB, arquivos, etc.
 * - Mantém compatibilidade por implementar a mesma interface
 * - Thread-safe graças ao ConcurrentHashMap e objetos imutáveis
 */
public class InMemoryProjectRepository implements ProjectRepository {

  private final Map<UUID, Project> storage = new ConcurrentHashMap<>();

  @Override
  public Project save(Project project) {
    if (project == null) {
      throw new IllegalArgumentException("Project não pode ser nulo");
    }
    storage.put(project.getId(), project);
    return project;
  }

  @Override
  public Optional<Project> findById(UUID id) {
    return Optional.ofNullable(storage.get(id));
  }

  @Override
  public List<Project> findAll() {
    return new ArrayList<>(storage.values());
  }

  @Override
  public List<Project> findByNameContaining(String name) {
    if (name == null) {
      return List.of();
    }

    String searchTerm = name.toLowerCase();
    return storage.values().stream()
        .filter(p -> p.getName().toLowerCase().contains(searchTerm))
        .collect(Collectors.toList());
  }

  @Override
  public boolean deleteById(UUID id) {
    return storage.remove(id) != null;
  }

  @Override
  public boolean existsById(UUID id) {
    return storage.containsKey(id);
  }

  /**
   * Método auxiliar para testes - limpa o repositório
   */
  public void clear() {
    storage.clear();
  }
}