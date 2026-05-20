package br.com.logistica.fretes.domain;

import br.com.logistica.sharedkernel.domain.FreteId;

import java.util.Optional;

public interface FreteRepository {

    void salvar(Frete frete);

    Optional<Frete> buscarPorId(FreteId freteId);
}
