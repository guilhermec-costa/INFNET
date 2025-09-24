package com.infnet.AT.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.infnet.AT.models.Mensalista;

public class MensalistaService {
    private static List<Mensalista> mensalistas = new ArrayList<>();
    
    static {
        mensalistas.add(new Mensalista("001", "João Silva", "joao@email.com", "(11) 99999-9999"));
        mensalistas.add(new Mensalista("002", "Maria Santos", "maria@email.com", "(11) 88888-8888"));
    }

    public List<Mensalista> listarTodos() {
        return new ArrayList<>(mensalistas);
    }

    public Optional<Mensalista> buscarPorMatricula(String matricula) {
        return mensalistas.stream()
                .filter(m -> m.getMatricula().equals(matricula))
                .findFirst();
    }

    public Mensalista criar(Mensalista mensalista) {
        if (mensalista.getMatricula() == null || mensalista.getMatricula().trim().isEmpty()) {
            throw new IllegalArgumentException("Matrícula é obrigatória");
        }
        
        if (mensalista.getNome() == null || mensalista.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }

        if (mensalista.getEmail() == null || mensalista.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email é obrigatório");
        }

        if (buscarPorMatricula(mensalista.getMatricula()).isPresent()) {
            throw new IllegalArgumentException("Já existe um mensalista com esta matrícula");
        }

        mensalistas.add(mensalista);
        return mensalista;
    }

    public void limparTodos() {
        mensalistas.clear();
    }
}