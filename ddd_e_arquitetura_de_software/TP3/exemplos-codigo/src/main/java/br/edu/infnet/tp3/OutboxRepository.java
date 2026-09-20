package br.edu.infnet.tp3;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OutboxRepository extends JpaRepository<EventoOutbox, UUID> {
    @Query("select evento from EventoOutbox evento where evento.publicado = false")
    List<EventoOutbox> buscarPendentes();
}
