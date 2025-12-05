package com.infnet.TP3;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

import com.infnet.TP3.application.services.ProjectServiceImpl;
import com.infnet.TP3.domain.entities.Project;
import com.infnet.TP3.domain.entities.Sprint;
import com.infnet.TP3.domain.entities.Task;
import com.infnet.TP3.domain.entities.User;
import com.infnet.TP3.domain.enums.TaskStatus;
import com.infnet.TP3.domain.repositories.ProjectRepository;
import com.infnet.TP3.domain.repositories.UserRepository;
import com.infnet.TP3.domain.services.ProjectService;
import com.infnet.TP3.domain.valueObjects.Money;
import com.infnet.TP3.domain.valueObjects.TaskItem;
import com.infnet.TP3.infra.repositories.InMemoryProjectRepository;
import com.infnet.TP3.infra.repositories.InMemoryUserRepository;

/**
 * DEMONSTRAÇÃO COMPLETA DO SISTEMA
 * 
 * Esta classe demonstra:
 * - Criação de entidades imutáveis
 * - Uso de injeção de dependências
 * - Operações sem efeitos colaterais
 * - Value Objects com validação
 * - Arquitetura em camadas
 */
public class Main {

  public static void main(String[] args) {
    System.out.println("=== SISTEMA DE GESTÃO DE PROJETOS ÁGIL ===\n");

    // EXERCÍCIO 5: Injeção de Dependências
    ProjectRepository projectRepository = new InMemoryProjectRepository();
    UserRepository userRepository = new InMemoryUserRepository();
    ProjectService projectService = new ProjectServiceImpl(projectRepository);

    demonstrarCriacaoUsuarios(userRepository);
    demonstrarCriacaoProjeto(projectService);
    demonstrarImutabilidade();
    demonstrarValueObjects();
    demonstrarSemEfeitosColaterais();

    System.out.println("\n=== FIM DA DEMONSTRAÇÃO ===");
  }

  private static void demonstrarCriacaoUsuarios(UserRepository userRepository) {
    System.out.println("--- DEMONSTRAÇÃO 1: Criação de Usuários ---");

    // EXERCÍCIO 3 e 4: Criação com validação
    User developer = User.builder()
        .name("João Silva")
        .email("joao.silva@email.com")
        .role("Desenvolvedor")
        .build();

    User scrumMaster = User.builder()
        .name("Maria Santos")
        .email("maria.santos@email.com")
        .role("Scrum Master")
        .build();

    userRepository.save(developer);
    userRepository.save(scrumMaster);

    System.out.println("✓ Usuários criados:");
    System.out.println("  - " + developer);
    System.out.println("  - " + scrumMaster);

    try {
      User invalid = User.builder()
          .name("Pedro")
          .email("email-invalido") // Email sem @
          .role("Tester")
          .build();
    } catch (IllegalArgumentException e) {
      System.out.println("✓ Validação funcionou: " + e.getMessage());
    }

    System.out.println();
  }

  private static void demonstrarCriacaoProjeto(ProjectService projectService) {
    System.out.println("--- DEMONSTRAÇÃO 2: Criação de Projeto com Sprint ---");

    // EXERCÍCIO 5: Uso do Service
    Project project = projectService.createProject(
        "Sistema de E-commerce",
        "Plataforma completa de vendas online");

    System.out.println("✓ Projeto criado: " + project.getName());
    System.out.println("  ID: " + project.getId());
    System.out.println("  Criado em: " + project.getCreatedAt());

    User developer = User.builder()
        .name("João Silva")
        .email("joao@email.com")
        .role("Dev")
        .build();

    Task task1 = Task.builder()
        .title("Implementar carrinho de compras")
        .description("Criar funcionalidade de carrinho")
        .status(TaskStatus.TODO)
        .assignee(developer)
        .build();

    Sprint sprint = Sprint.builder()
        .name("Sprint 1 - Fundação")
        .startDate(LocalDate.now())
        .endDate(LocalDate.now().plusDays(14))
        .build();

    Project updatedProject = projectService.addSprintToProject(
        project.getId(),
        sprint.adicionarTarefa(task1));

    System.out.println("✓ Sprint adicionado ao projeto");
    System.out.println("  Sprint: " + sprint.getName());
    System.out.println("  Tarefas: " + sprint.getTasks().size());
    System.out.println();
  }

  private static void demonstrarImutabilidade() {
    System.out.println("--- DEMONSTRAÇÃO 3: Imutabilidade em Ação ---");

    // EXERCÍCIO 1: Criar tarefa original
    User user = User.builder()
        .name("Ana Costa")
        .email("ana@email.com")
        .role("Developer")
        .build();

    Task original = Task.builder()
        .title("Tarefa Original")
        .description("Descrição original")
        .status(TaskStatus.TODO)
        .assignee(user)
        .build();

    System.out.println("Tarefa original:");
    System.out.println("  Status: " + original.getStatus());
    System.out.println("  Título: " + original.getTitle());

    // EXERCÍCIO 2: "Modificar" status (retorna NOVA tarefa)
    Task modificada = original.alterarStatus(TaskStatus.IN_PROGRESS);

    System.out.println("\nApós 'modificação':");
    System.out.println("  Original ainda tem status: " + original.getStatus());
    System.out.println("  Nova tarefa tem status: " + modificada.getStatus());
    System.out.println("  ✓ Objeto original permaneceu intacto!");

    System.out.println("  São o mesmo objeto? " + (original == modificada));
    System.out.println();
  }

  private static void demonstrarValueObjects() {
    System.out.println("--- DEMONSTRAÇÃO 4: Value Objects (Money e TaskItem) ---");

    // EXERCÍCIO 4: Uso de BigDecimal para valores monetários
    Money preco1 = Money.brl("100.50");
    Money preco2 = Money.brl("50.25");

    System.out.println("Preço 1: " + preco1);
    System.out.println("Preço 2: " + preco2);

    Money total = preco1.add(preco2);
    System.out.println("Total: " + total);
    System.out.println("✓ Precisão decimal mantida (BigDecimal)");

    Money comDesconto = total.applyDiscount(BigDecimal.valueOf(10));
    System.out.println("Com 10% de desconto: " + comDesconto);
    System.out.println("Original ainda é: " + total);

    // EXERCÍCIO 3: TaskItem imutável
    TaskItem item = TaskItem.of("ITEM-001", 5, Money.brl("20.00"));
    System.out.println("\nTaskItem criado: " + item);
    System.out.println("Custo total: " + item.getTotalCost());

    TaskItem itemModificado = item.withQuantity(10);
    System.out.println("Após modificar quantidade:");
    System.out.println("  Original: " + item.getQuantity() + " unidades");
    System.out.println("  Novo: " + itemModificado.getQuantity() + " unidades");
    System.out.println("  ✓ Imutabilidade preservada!");

    System.out.println();
  }

  private static void demonstrarSemEfeitosColaterais() {
    System.out.println("--- DEMONSTRAÇÃO 5: Métodos sem Efeitos Colaterais ---");

    User usuario = User.builder()
        .name("Carlos Mendes")
        .email("carlos@email.com")
        .role("Analista")
        .build();

    System.out.println("Usuário original: " + usuario.getEmail());

    // EXERCÍCIO 2: Atualizar email retorna NOVO usuário
    User comNovoEmail = usuario.atualizarEmail("carlos.mendes@newemail.com");

    System.out.println("Após 'atualização':");
    System.out.println("  Original: " + usuario.getEmail());
    System.out.println("  Novo: " + comNovoEmail.getEmail());
    System.out.println("  ✓ Sem efeitos colaterais - objeto original intacto");

    System.out.println("\nCenário de concorrência simulado:");
    System.out.println("  Thread 1 lê: " + usuario.getEmail());
    User thread2Result = usuario.definirCargo("Senior Analista");
    System.out.println("  Thread 2 modifica cargo (novo objeto)");
    System.out.println("  Thread 1 ainda vê: " + usuario.getEmail() + " com cargo " + usuario.getRole());
    System.out.println("  ✓ Sem race conditions!");

    System.out.println();
  }
}