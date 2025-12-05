package com.infnet.TP3.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * EXERCÍCIOS 3 e 4: User (objeto imutável com validação)
 *
 * Representa um colaborador do sistema.
 *
 * Tipos de dados:
 * - UUID: identificador único
 * - String: nome, email, cargo
 * - LocalDateTime: timestamps
 *
 * Validações:
 * - Email em formato válido
 * - Nome não vazio
 * - Cargo obrigatório
 */

public final class User {

  private final UUID id;
  private final String name;
  private final String email;
  private final String role;
  private final LocalDateTime createdAt;
  private final LocalDateTime updatedAt;

  private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

  private User(UUID id, String name, String email, String role,
      LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.role = role;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getRole() {
    return role;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  /**
   * Retorna NOVO usuário com email atualizado
   */
  public User atualizarEmail(String newEmail) {
    if (!isValidEmail(newEmail)) {
      throw new IllegalArgumentException("Email inválido: " + newEmail);
    }
    return new User(this.id, this.name, newEmail, this.role,
        this.createdAt, LocalDateTime.now());
  }

  /**
   * Retorna NOVO usuário com cargo atualizado
   */
  public User definirCargo(String newRole) {
    if (newRole == null || newRole.trim().isEmpty()) {
      throw new IllegalArgumentException("Cargo não pode ser vazio");
    }
    return new User(this.id, this.name, this.email, newRole,
        this.createdAt, LocalDateTime.now());
  }

  /**
   * EXERCÍCIO 3: Validação de email
   * Este método assegura que apenas emails válidos sejam aceitos
   */
  private static boolean isValidEmail(String email) {
    return email != null && EMAIL_PATTERN.matcher(email).matches();
  }

  /**
   * EXERCÍCIO 3: Builder com validação
   * 
   * O Builder garante que:
   * - Nome não seja vazio
   * - Email seja válido
   * - Cargo não seja vazio
   * 
   * BENEFÍCIOS:
   * - Impossível criar um User inválido
   * - Validações centralizadas
   * - Erros detectados no momento da criação
   */
  public static class Builder {
    private UUID id = UUID.randomUUID();
    private String name;
    private String email;
    private String role;
    private LocalDateTime createdAt = LocalDateTime.now();

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder email(String email) {
      this.email = email;
      return this;
    }

    public Builder role(String role) {
      this.role = role;
      return this;
    }

    public User build() {
      if (name == null || name.trim().isEmpty()) {
        throw new IllegalArgumentException("Nome é obrigatório");
      }
      if (!isValidEmail(email)) {
        throw new IllegalArgumentException("Email inválido: " + email);
      }
      if (role == null || role.trim().isEmpty()) {
        throw new IllegalArgumentException("Cargo é obrigatório");
      }

      return new User(id, name.trim(), email.toLowerCase().trim(),
          role.trim(), createdAt, createdAt);
    }
  }

  public static Builder builder() {
    return new Builder();
  }

  @Override
  public String toString() {
    return String.format("User[name='%s', email='%s', role='%s']",
        name, email, role);
  }
}