package com.example.biblioteca.service;

import com.example.biblioteca.dto.LivroRequest;
import com.example.biblioteca.model.Livro;
import com.example.biblioteca.model.StatusLivro;
import com.example.biblioteca.repository.LivroRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class LivroService {

    private final LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    public List<Livro> listarDisponiveis() {
        return livroRepository.findByStatus(StatusLivro.DISPONIVEL);
    }

    public Livro buscarPorId(Long id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro nao encontrado."));
    }

    public Livro criar(LivroRequest request) {
        Livro livro = new Livro();
        livro.setTitulo(request.titulo());
        livro.setAutor(request.autor());
        livro.setIsbn(request.isbn());
        livro.setStatus(StatusLivro.DISPONIVEL);
        return livroRepository.save(livro);
    }

    public Livro atualizar(Long id, LivroRequest request) {
        Livro livro = buscarPorId(id);
        livro.setTitulo(request.titulo());
        livro.setAutor(request.autor());
        livro.setIsbn(request.isbn());
        return livroRepository.save(livro);
    }

    public void excluir(Long id) {
        Livro livro = buscarPorId(id);
        if (livro.getStatus() == StatusLivro.EMPRESTADO) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nao e possivel excluir um livro emprestado.");
        }
        livroRepository.delete(livro);
    }

    public Livro salvar(Livro livro) {
        return livroRepository.save(livro);
    }
}
