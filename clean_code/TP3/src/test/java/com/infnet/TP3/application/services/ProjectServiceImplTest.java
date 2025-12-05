package com.infnet.TP3.application.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.infnet.TP3.domain.entities.Project;
import com.infnet.TP3.domain.entities.Sprint;
import com.infnet.TP3.domain.repositories.ProjectRepository;
import com.infnet.TP3.infra.repositories.InMemoryProjectRepository;

import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

/**
 * EXERCÍCIO 5: Testes demonstrando Injeção de Dependências
 * 
 * É possível testar o ProjectService facilmente porque ele recebe
 * dependências via construtor. Pode-se injetar implementação em memória
 * para testes, sem necessidade de banco de dados.
 */
class ProjectServiceTest {

  private ProjectRepository repository;
  private ProjectServiceImpl service;

  @BeforeEach
  void setUp() {
    repository = new InMemoryProjectRepository();
    service = new ProjectServiceImpl(repository);
  }

  @Test
  @DisplayName("EXERCÍCIO 5: Service deve criar projeto via repository")
  void devecriarProjetoViaRepository() {
    Project project = service.createProject(
        "Novo Projeto",
        "Descrição do projeto");

    assertNotNull(project);
    assertEquals("Novo Projeto", project.getName());

    Project recuperado = service.getProjectById(project.getId());
    assertNotNull(recuperado);
    assertEquals(project.getId(), recuperado.getId());
  }

  @Test
  @DisplayName("EXERCÍCIO 5: Service deve validar nome obrigatório")
  void deveValidarNomeObrigatorio() {
    assertThrows(IllegalArgumentException.class, () -> {
      service.createProject("", "Descrição");
    });

    assertThrows(IllegalArgumentException.class, () -> {
      service.createProject(null, "Descrição");
    });
  }

  @Test
  @DisplayName("EXERCÍCIO 5: Service deve validar sobreposição de sprints")
  void deveValidarSobreposicaoSprints() {
    Project project = service.createProject("Projeto", "Desc");

    Sprint sprint1 = Sprint.builder()
        .name("Sprint 1")
        .startDate(LocalDate.now())
        .endDate(LocalDate.now().plusDays(14))
        .build();

    Sprint sprint2 = Sprint.builder()
        .name("Sprint 2")
        .startDate(LocalDate.now().plusDays(7)) // Sobrepõe sprint1
        .endDate(LocalDate.now().plusDays(21))
        .build();

    service.addSprintToProject(project.getId(), sprint1);

    assertThrows(IllegalArgumentException.class, () -> {
      service.addSprintToProject(project.getId(), sprint2);
    }, "Não deve permitir sprints sobrepostos");
  }

  @Test
  @DisplayName("EXERCÍCIO 5: Service deve permitir sprints sequenciais")
  void devePermitirSprintsSequenciais() {
    Project project = service.createProject("Projeto", "Desc");

    Sprint sprint1 = Sprint.builder()
        .name("Sprint 1")
        .startDate(LocalDate.now())
        .endDate(LocalDate.now().plusDays(14))
        .build();

    Sprint sprint2 = Sprint.builder()
        .name("Sprint 2")
        .startDate(LocalDate.now().plusDays(15)) // Após sprint1
        .endDate(LocalDate.now().plusDays(28))
        .build();

    Project comSprint1 = service.addSprintToProject(project.getId(), sprint1);
    Project comSprint2 = service.addSprintToProject(comSprint1.getId(), sprint2);

    assertEquals(2, comSprint2.getSprints().size());
  }

  @Test
  @DisplayName("EXERCÍCIO 5: Injeção de dependência facilita testes")
  void injecaoDependenciaFacilitaTestes() {
    ProjectRepository inMemoryRepo = new InMemoryProjectRepository();
    ProjectServiceImpl serviceComInMemory = new ProjectServiceImpl(inMemoryRepo);

    assertNotNull(serviceComInMemory);
  }

  @Test
  @DisplayName("EXERCÍCIO 5: Service não deve aceitar repository nulo")
  void naoDeveAceitarRepositoryNulo() {
    assertThrows(IllegalArgumentException.class, () -> {
      new ProjectServiceImpl(null);
    }, "Service deve validar dependências no construtor");
  }
}