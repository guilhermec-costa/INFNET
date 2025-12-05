package com.infnet.TP3.domain.entities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Testes unitários demonstrando:
 * - Imutabilidade das entidades
 * - Ausência de efeitos colaterais
 * - Validações funcionando
 * - Thread-safety implícita
 */
class ProjectTest {
    
    @Test
    @DisplayName("EXERCÍCIO 1: Project deve ser criado com valores válidos")
    void devecriarProjectComValoresValidos() {
        Project project = Project.builder()
            .name("Projeto Teste")
            .description("Descrição do projeto")
            .build();
        
        assertNotNull(project);
        assertEquals("Projeto Teste", project.getName());
        assertNotNull(project.getId());
        assertNotNull(project.getCreatedAt());
        assertEquals(0, project.getSprints().size());
    }
    
    @Test
    @DisplayName("EXERCÍCIO 1: Project não deve permitir nome vazio")
    void naoDevePermitirNomeVazio() {
        assertThrows(IllegalArgumentException.class, () -> {
            Project.builder()
                .name("")
                .description("Descrição")
                .build();
        });
    }
    
    @Test
    @DisplayName("EXERCÍCIO 1: Adicionar sprint deve retornar NOVO project")
    void adicionarSprintDeveRetornarNovoProject() {
        Project original = Project.builder()
            .name("Projeto Original")
            .description("Descrição")
            .build();
        
        Sprint sprint = Sprint.builder()
            .name("Sprint 1")
            .startDate(LocalDate.now())
            .endDate(LocalDate.now().plusDays(14))
            .build();
        
        Project modificado = original.adicionarSprint(sprint);
        
        assertNotSame(original, modificado, 
            "Deve retornar NOVO objeto, não modificar o original");
        
        assertEquals(0, original.getSprints().size(), 
            "Original não deve ser modificado");
        
        assertEquals(1, modificado.getSprints().size(),
            "Novo objeto deve ter o sprint adicionado");
    }
    
    @Test
    @DisplayName("EXERCÍCIO 1: Remover sprint deve retornar NOVO project")
    void removerSprintDeveRetornarNovoProject() {
        Sprint sprint1 = Sprint.builder()
            .name("Sprint 1")
            .startDate(LocalDate.now())
            .endDate(LocalDate.now().plusDays(14))
            .build();
        
        Sprint sprint2 = Sprint.builder()
            .name("Sprint 2")
            .startDate(LocalDate.now().plusDays(15))
            .endDate(LocalDate.now().plusDays(28))
            .build();
        
        Project comSprints = Project.builder()
            .name("Projeto")
            .description("Desc")
            .build()
            .adicionarSprint(sprint1)
            .adicionarSprint(sprint2);
        
        assertEquals(2, comSprints.getSprints().size());
        
        Project semSprint1 = comSprints.removerSprint(sprint1.getId());
        
        assertEquals(2, comSprints.getSprints().size(),
            "Original não modificado");
        
        assertEquals(1, semSprint1.getSprints().size(),
            "Novo objeto sem o sprint removido");
    }
    
    @Test
    @DisplayName("EXERCÍCIO 1: Imutabilidade - lista de sprints não pode ser alterada externamente")
    void listaDeSpritsDeveSerImutavel() {
        Project project = Project.builder()
            .name("Projeto")
            .description("Desc")
            .build();
        
        assertThrows(UnsupportedOperationException.class, () -> {
            project.getSprints().add(Sprint.builder()
                .name("Sprint Hack")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(7))
                .build());
        }, "Lista retornada deve ser imutável");
    }
    
    @Test
    @DisplayName("EXERCÍCIO 3: Concorrência - múltiplas threads podem ler simultaneamente")
    void deveSerThreadSafe() throws InterruptedException {
        Project project = Project.builder()
            .name("Projeto Compartilhado")
            .description("Desc")
            .build();
        
        Sprint sprint = Sprint.builder()
            .name("Sprint Compartilhado")
            .startDate(LocalDate.now())
            .endDate(LocalDate.now().plusDays(14))
            .build();
        
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                Project temp = project.adicionarSprint(sprint);
                assertNotNull(temp);
            }
        });
        
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                UUID id = project.getId();
                assertNotNull(id);
            }
        });
        
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        
        assertEquals(0, project.getSprints().size(),
            "Objeto original não foi afetado por threads concorrentes");
    }
}