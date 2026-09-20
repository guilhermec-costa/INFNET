package br.edu.infnet.tp3;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class EventoOutbox {
    @Id
    private UUID id;
    private String topico;
    private String chave;
    private boolean publicado;

    protected EventoOutbox() {
    }

    public String topico() {
        return topico;
    }

    public String chave() {
        return chave;
    }

    public void marcarComoPublicado() {
        this.publicado = true;
    }
}
