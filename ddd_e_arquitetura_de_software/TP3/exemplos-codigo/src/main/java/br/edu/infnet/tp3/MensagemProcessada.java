package br.edu.infnet.tp3;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class MensagemProcessada {
    @Id
    private UUID id;

    protected MensagemProcessada() {
    }

    public MensagemProcessada(UUID id) {
        this.id = id;
    }
}
