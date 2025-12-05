package com.infnet.TP3.application.services;

import java.util.List;
import java.util.UUID;

import com.infnet.TP3.domain.entities.Project;
import com.infnet.TP3.domain.entities.Sprint;
import com.infnet.TP3.domain.repositories.ProjectRepository;
import com.infnet.TP3.domain.services.ProjectService;

/**
 * EXERCÍCIO 5: Implementação do ProjectService com injeção de dependência
 *
 * Dependências são recebidas via construtor — não criadas internamente.
 *
 * Benefícios:
 * - Fácil testar (permite mocks)
 * - Flexível para trocar implementações
 * - Baixo acoplamento (depende de abstrações)
 * - Segue Inversão de Controle
 *
 * Responsabilidade única:
 * Implementar regras de negócio de projetos, sem lidar com persistência,
 * requisições ou apresentação.
 */

public class ProjectServiceImpl implements ProjectService {

  private final ProjectRepository projectRepository;

  /**
   * Injeção de dependência via construtor.
   *
   * Repository é recebido como parâmetro, não criado aqui.
   * Permite:
   * - Usar implementação real em produção
   * - Injetar mocks em testes
   * - Trocar implementações sem alterar esta classe
   */

  public ProjectServiceImpl(ProjectRepository projectRepository) {
    if (projectRepository == null) {
      throw new IllegalArgumentException("ProjectRepository não pode ser nulo");
    }
    this.projectRepository = projectRepository;
  }

  @Override
  public Project createProject(String name, String description) {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Nome do projeto é obrigatório");
    }

    Project project = Project.builder()
        .name(name)
        .description(description)
        .build();

    return projectRepository.save(project);
  }

  @Override
  public Project addSprintToProject(UUID projectId, Sprint sprint) {
    Project project = getProjectById(projectId);

    for (Sprint existingSprint : project.getSprints()) {
      if (sprintsOverlap(existingSprint, sprint)) {
        throw new IllegalArgumentException(
            "Sprint conflita com sprint existente: " + existingSprint.getName());
      }
    }

    Project updatedProject = project.adicionarSprint(sprint);

    return projectRepository.save(updatedProject);
  }

  @Override
  public Project removeSprintFromProject(UUID projectId, UUID sprintId) {
    Project project = getProjectById(projectId);

    boolean sprintExists = project.getSprints().stream()
        .anyMatch(s -> s.getId().equals(sprintId));

    if (!sprintExists) {
      throw new IllegalArgumentException("Sprint não encontrado no projeto");
    }

    Project updatedProject = project.removerSprint(sprintId);
    return projectRepository.save(updatedProject);
  }

  @Override
  public Project getProjectById(UUID projectId) {
    return projectRepository.findById(projectId)
        .orElseThrow(() -> new IllegalArgumentException(
            "Projeto não encontrado: " + projectId));
  }

  @Override
  public List<Project> getAllProjects() {
    return projectRepository.findAll();
  }

  @Override
  public List<Project> searchProjectsByName(String name) {
    if (name == null || name.trim().isEmpty()) {
      return List.of();
    }
    return projectRepository.findByNameContaining(name);
  }

  @Override
  public Project updateProject(UUID projectId, String newName, String newDescription) {
    Project project = getProjectById(projectId);

    if (newName == null || newName.trim().isEmpty()) {
      throw new IllegalArgumentException("Nome não pode ser vazio");
    }

    Project updatedProject = project.atualizar(newName, newDescription);
    return projectRepository.save(updatedProject);
  }

  @Override
  public void deleteProject(UUID projectId) {
    if (!projectRepository.existsById(projectId)) {
      throw new IllegalArgumentException("Projeto não encontrado: " + projectId);
    }

    projectRepository.deleteById(projectId);
  }

  /**
   * Método auxiliar sem efeitos colaterais.
   * Apenas calcula se há sobreposição; não modifica nenhum objeto.
   * É um método puro: mesmo input → mesmo output.
   */
  private boolean sprintsOverlap(Sprint sprint1, Sprint sprint2) {
    return !(sprint1.getEndDate().isBefore(sprint2.getStartDate()) ||
        sprint2.getEndDate().isBefore(sprint1.getStartDate()));
  }
}