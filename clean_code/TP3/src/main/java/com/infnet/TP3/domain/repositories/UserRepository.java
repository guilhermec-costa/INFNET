package com.infnet.TP3.domain.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.infnet.TP3.domain.entities.User;

/**
 * EXERCÍCIO 5: UserRepository
 *
 * Interface de repositório para User, seguindo o mesmo padrão de
 * ProjectRepository para manter consistência arquitetural.
 */
public interface UserRepository {

  User save(User user);

  Optional<User> findById(UUID id);

  Optional<User> findByEmail(String email);

  List<User> findAll();

  List<User> findByRole(String role);

  boolean deleteById(UUID id);

  boolean existsByEmail(String email);
}