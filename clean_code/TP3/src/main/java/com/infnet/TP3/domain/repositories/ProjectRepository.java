package com.infnet.TP3.domain.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.infnet.TP3.domain.entities.Project;

/**
 * EXERCÍCIO 5: Repositório (Repository Pattern)
 *
 * Define o contrato de persistência para Project, sem impor como os dados
 * são armazenados.
 *
 * Benefícios:
 * - Inversão de dependência (negócio depende da abstração, não da
 * implementação)
 * - Fácil de testar (mocks/implementações em memória)
 * - Trocamos a tecnologia de persistência sem mudar a lógica de negócio
 * - SRP: interface focada apenas em operações de persistência
 * - Baixo acoplamento com infraestrutura
 *
 * Trade-offs:
 * - Mais código/abstração
 * - Pode parecer overkill em apps muito simples
 */

public interface ProjectRepository {

  /**
   * Salva um projeto (insert ou update)
   * 
   * @param project projeto a ser salvo
   * @return projeto salvo
   */
  Project save(Project project);

  /**
   * Busca projeto por ID
   * 
   * @param id identificador do projeto
   * @return Optional com o projeto se encontrado
   */
  Optional<Project> findById(UUID id);

  /**
   * Busca todos os projetos
   * 
   * @return lista de todos os projetos
   */
  List<Project> findAll();

  /**
   * Busca projetos por nome (busca parcial)
   * 
   * @param name nome ou parte do nome
   * @return lista de projetos que correspondem
   */
  List<Project> findByNameContaining(String name);

  /**
   * Remove um projeto
   * 
   * @param id identificador do projeto
   * @return true se removido, false se não encontrado
   */
  boolean deleteById(UUID id);

  /**
   * Verifica se existe projeto com determinado ID
   * 
   * @param id identificador
   * @return true se existe
   */
  boolean existsById(UUID id);
}
