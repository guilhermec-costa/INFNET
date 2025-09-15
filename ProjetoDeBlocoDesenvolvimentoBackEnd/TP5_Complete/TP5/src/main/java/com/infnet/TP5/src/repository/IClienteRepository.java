package com.infnet.TP5.src.repository;

import java.util.Optional;

import com.infnet.TP5.src.model.Cliente;

public interface IClienteRepository {
    Optional<Cliente> buscarPorEmail(String email);
    Optional<Cliente> buscarPorId(long clientId);
}