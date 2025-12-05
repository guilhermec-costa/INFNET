package com.infnet.TP3.domain.entities;

import org.junit.jupiter.api.Test;

import com.infnet.TP3.domain.enums.TaskStatus;

import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes para Task demonstrando:
 * - Imutabilidade
 * - Validações de transição de status
 * - Ausência de efeitos colaterais
 */
class TaskTest {

  @Test
  @DisplayName("EXERCÍCIO 2: alterarStatus deve retornar NOVA task")
  void alterarStatusDeveRetornarNovaTask() {
    User user = User.builder()
        .name("João")
        .email("joao@email.com")
        .role("Dev")
        .build();

    Task original = Task.builder()
        .title("Tarefa Teste")
        .description("Descrição")
        .status(TaskStatus.TODO)
        .assignee(user)
        .build();

    Task modificada = original.alterarStatus(TaskStatus.IN_PROGRESS);

    assertNotSame(original, modificada);

    assertEquals(TaskStatus.TODO, original.getStatus(),
        "Status original não deve mudar");

    assertEquals(TaskStatus.IN_PROGRESS, modificada.getStatus(),
        "Nova task deve ter novo status");
  }

  @Test
  @DisplayName("EXERCÍCIO 2: atribuirResponsavel não deve modificar original")
  void atribuirResponsavelNaoDeveModificarOriginal() {
    Task semResponsavel = Task.builder()
        .title("Tarefa")
        .description("Desc")
        .status(TaskStatus.TODO)
        .build();

    User novoResponsavel = User.builder()
        .name("Maria")
        .email("maria@email.com")
        .role("Dev")
        .build();

    Task comResponsavel = semResponsavel.atribuirResponsavel(novoResponsavel);

    assertNull(semResponsavel.getAssignee(),
        "Original deve permanecer sem responsável");

    assertNotNull(comResponsavel.getAssignee());
    assertEquals("Maria", comResponsavel.getAssignee().getName());
  }

  @Test
  @DisplayName("EXERCÍCIO 2: Validação de transição de status")
  void deveValidarTransicaoDeStatus() {
    Task task = Task.builder()
        .title("Tarefa")
        .description("Desc")
        .status(TaskStatus.DONE)
        .build();

    assertThrows(IllegalStateException.class, () -> {
      task.alterarStatus(TaskStatus.IN_PROGRESS);
    }, "Tarefa DONE não pode mudar de status");
  }

  @Test
  @DisplayName("EXERCÍCIO 3: Builder deve validar título obrigatório")
  void builderDeveValidarTituloObrigatorio() {
    assertThrows(IllegalArgumentException.class, () -> {
      Task.builder()
          .title("") // Título vazio
          .description("Desc")
          .build();
    });

    assertThrows(IllegalArgumentException.class, () -> {
      Task.builder()
          .title(null) // Título nulo
          .description("Desc")
          .build();
    });
  }

  @Test
  @DisplayName("EXERCÍCIO 3: Task sem responsável deve ser permitida")
  void taskSemResponsavelDeveSerPermitida() {
    Task task = Task.builder()
        .title("Tarefa")
        .description("Desc")
        .build();

    assertNotNull(task);
    assertNull(task.getAssignee());
  }
}