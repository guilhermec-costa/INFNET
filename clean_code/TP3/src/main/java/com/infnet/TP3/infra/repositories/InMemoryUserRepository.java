package com.infnet.TP3.infra.repositories;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.infnet.TP3.domain.entities.User;
import com.infnet.TP3.domain.repositories.UserRepository;

/**
 * EXERCÍCIO 5: Implementação em memória do UserRepository
 * 
 * Segue o mesmo padrão de InMemoryProjectRepository.
 */
public class InMemoryUserRepository implements UserRepository {

  private final Map<UUID, User> storage = new ConcurrentHashMap<>();

  @Override
  public User save(User user) {
    if (user == null) {
      throw new IllegalArgumentException("User não pode ser nulo");
    }
    storage.put(user.getId(), user);
    return user;
  }

  @Override
  public Optional<User> findById(UUID id) {
    return Optional.ofNullable(storage.get(id));
  }

  @Override
  public Optional<User> findByEmail(String email) {
    if (email == null) {
      return Optional.empty();
    }

    return storage.values().stream()
        .filter(u -> u.getEmail().equalsIgnoreCase(email))
        .findFirst();
  }

  @Override
  public List<User> findAll() {
    return new ArrayList<>(storage.values());
  }

  @Override
  public List<User> findByRole(String role) {
    if (role == null) {
      return List.of();
    }

    return storage.values().stream()
        .filter(u -> u.getRole().equalsIgnoreCase(role))
        .collect(Collectors.toList());
  }

  @Override
  public boolean deleteById(UUID id) {
    return storage.remove(id) != null;
  }

  @Override
  public boolean existsByEmail(String email) {
    if (email == null) {
      return false;
    }

    return storage.values().stream()
        .anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
  }

  public void clear() {
    storage.clear();
  }
}