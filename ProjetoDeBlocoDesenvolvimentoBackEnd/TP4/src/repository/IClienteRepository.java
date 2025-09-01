package src.repository;

import java.util.Optional;

import src.model.Cliente;

public interface IClienteRepository {
    Optional<Cliente> buscarPorEmail(String email);
}