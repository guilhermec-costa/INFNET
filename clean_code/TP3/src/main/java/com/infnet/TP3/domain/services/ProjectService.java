package com.infnet.TP3.domain.services;

import java.util.List;
import java.util.UUID;

import com.infnet.TP3.domain.entities.Project;
import com.infnet.TP3.domain.entities.Sprint;

/**
 * EXERCÍCIO 5: Service (lógica de negócio)
 *
 * Define operações de negócio para Project. Diferente do repositório, o
 * serviço contém regras, validações e orquestra fluxos.
 *
 * Separação de responsabilidades:
 * - Repository: persistência
 * - Service: lógica de negócio
 *
 * Benefícios:
 * - Regras isoladas da camada de dados
 * - Fácil adicionar validações
 * - Suporta operações envolvendo vários repositórios
 * - Testável com mocks
 */

public interface ProjectService {

  /**
   * Cria um novo projeto com validações de negócio
   */
  Project createProject(String name, String description);

  /**
   * Adiciona um sprint ao projeto, validando datas e conflitos
   */
  Project addSprintToProject(UUID projectId, Sprint sprint);

  /**
   * Remove sprint do projeto, verificando se pode ser removido
   */
  Project removeSprintFromProject(UUID projectId, UUID sprintId);

  /**
   * Busca projeto por ID com tratamento de erro
   */
  Project getProjectById(UUID projectId);

  /**
   * Lista todos os projetos
   */
  List<Project> getAllProjects();

  /**
   * Busca projetos por nome
   */
  List<Project> searchProjectsByName(String name);

  /**
   * Atualiza informações do projeto
   */
  Project updateProject(UUID projectId, String newName, String newDescription);

  /**
   * Remove projeto (com validações - ex: não remover se tiver tarefas ativas)
   */
  void deleteProject(UUID projectId);
}