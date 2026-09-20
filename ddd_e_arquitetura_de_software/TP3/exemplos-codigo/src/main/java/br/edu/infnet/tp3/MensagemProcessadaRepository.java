package br.edu.infnet.tp3;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MensagemProcessadaRepository extends JpaRepository<MensagemProcessada, UUID> {
}
