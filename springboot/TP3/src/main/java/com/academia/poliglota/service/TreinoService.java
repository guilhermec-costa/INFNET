package com.academia.poliglota.service;

import com.academia.poliglota.dto.TreinoRequest;
import com.academia.poliglota.model.Instrutor;
import com.academia.poliglota.model.Treino;
import com.academia.poliglota.repository.InstrutorRepository;
import com.academia.poliglota.repository.TreinoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TreinoService {

    private final TreinoRepository treinoRepository;
    private final InstrutorRepository instrutorRepository;

    public TreinoService(TreinoRepository treinoRepository, InstrutorRepository instrutorRepository) {
        this.treinoRepository = treinoRepository;
        this.instrutorRepository = instrutorRepository;
    }

    public Treino cadastrar(TreinoRequest request) {
        Instrutor instrutor = instrutorRepository.findById(request.instrutorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Instrutor não encontrado"));

        Treino treino = new Treino();
        treino.setNomeTreino(request.nomeTreino());
        treino.setFocoPrincipal(request.focoPrincipal());
        treino.setInstrutor(instrutor);

        return treinoRepository.save(treino);
    }
}
