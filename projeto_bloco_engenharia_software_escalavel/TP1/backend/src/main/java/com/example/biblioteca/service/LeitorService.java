package com.example.biblioteca.service;

import com.example.biblioteca.dto.LeitorRequest;
import com.example.biblioteca.model.Leitor;
import com.example.biblioteca.repository.EmprestimoRepository;
import com.example.biblioteca.repository.LeitorRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class LeitorService {

    private final LeitorRepository leitorRepository;
    private final EmprestimoRepository emprestimoRepository;

    public LeitorService(LeitorRepository leitorRepository, EmprestimoRepository emprestimoRepository) {
        this.leitorRepository = leitorRepository;
        this.emprestimoRepository = emprestimoRepository;
    }

    public List<Leitor> listarTodos() {
        return leitorRepository.findAll();
    }

    public Leitor buscarPorId(Long id) {
        return leitorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Leitor nao encontrado."));
    }

    public Leitor criar(LeitorRequest request) {
        Leitor leitor = new Leitor();
        leitor.setNome(request.nome());
        leitor.setEmail(request.email());
        return leitorRepository.save(leitor);
    }

    public Leitor atualizar(Long id, LeitorRequest request) {
        Leitor leitor = buscarPorId(id);
        leitor.setNome(request.nome());
        leitor.setEmail(request.email());
        return leitorRepository.save(leitor);
    }

    public void excluir(Long id) {
        if (emprestimoRepository.existsByLeitorIdAndAtivoTrue(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nao e possivel excluir leitor com emprestimo ativo.");
        }
        leitorRepository.delete(buscarPorId(id));
    }
}
