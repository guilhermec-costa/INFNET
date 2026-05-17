package com.guilhermechina.onibusapi.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.guilhermechina.onibusapi.dtos.PassagemRequestDTO;
import com.guilhermechina.onibusapi.dtos.PassagemResponseDTO;
import com.guilhermechina.onibusapi.models.Passagem;

@Service
public class PassagemService {

    private List<Passagem> passagens = new ArrayList<>();
    private Long idCounter = 1L;

    public PassagemService() {
        passagens.add(new Passagem(idCounter++, "Ana Souza", 1, "Rio de Janeiro", "Sao Paulo",
                LocalDate.of(2026, 5, 20), "CONFIRMADA"));
        passagens.add(new Passagem(idCounter++, "Bruno Lima", 2, "Belo Horizonte", "Vitoria",
                LocalDate.of(2026, 5, 22), "PENDENTE"));
        passagens.add(new Passagem(idCounter++, "Carla Mendes", 3, "Curitiba", "Florianopolis",
                LocalDate.of(2026, 5, 25), "CONFIRMADA"));
    }

    public List<PassagemResponseDTO> listarTodas() {
        return passagens.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public PassagemResponseDTO criar(PassagemRequestDTO requestDTO) {
        validarAssentoDisponivel(requestDTO.getAssento(), null);

        Passagem passagem = toPassagem(requestDTO);
        passagem.setId(idCounter++);
        passagens.add(passagem);

        return toResponseDTO(passagem);
    }

    public PassagemResponseDTO buscarPorId(Long id) {
        return toResponseDTO(buscarEntidadePorId(id));
    }

    public PassagemResponseDTO atualizar(Long id, PassagemRequestDTO requestDTO) {
        Passagem passagem = buscarEntidadePorId(id);
        validarAssentoDisponivel(requestDTO.getAssento(), id);

        passagem.setPassageiro(requestDTO.getPassageiro());
        passagem.setAssento(requestDTO.getAssento());
        passagem.setOrigem(requestDTO.getOrigem());
        passagem.setDestino(requestDTO.getDestino());
        passagem.setData(requestDTO.getData());
        passagem.setStatus(requestDTO.getStatus());

        return toResponseDTO(passagem);
    }

    public void deletar(Long id) {
        buscarEntidadePorId(id);
        passagens.removeIf(p -> p.getId().equals(id));
    }

    public List<PassagemResponseDTO> buscarPorDestino(String destino) {
        return passagens.stream()
                .filter(passagem -> passagem.getDestino() != null
                        && passagem.getDestino().equalsIgnoreCase(destino))
                .map(this::toResponseDTO)
                .toList();
    }

    private Passagem buscarEntidadePorId(Long id) {
        return passagens.stream()
                .filter(passagem -> passagem.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Passagem não encontrada."));
    }

    private void validarAssentoDisponivel(Integer assento, Long idIgnorado) {
        boolean assentoExiste = passagens.stream()
                .anyMatch(passagem -> passagem.getAssento().equals(assento)
                        && (idIgnorado == null || !passagem.getId().equals(idIgnorado)));

        if (assentoExiste) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Assento já reservado.");
        }
    }

    private Passagem toPassagem(PassagemRequestDTO requestDTO) {
        return new Passagem(
                null,
                requestDTO.getPassageiro(),
                requestDTO.getAssento(),
                requestDTO.getOrigem(),
                requestDTO.getDestino(),
                requestDTO.getData(),
                requestDTO.getStatus());
    }

    private PassagemResponseDTO toResponseDTO(Passagem passagem) {
        return new PassagemResponseDTO(
                passagem.getId(),
                passagem.getPassageiro(),
                passagem.getAssento(),
                passagem.getOrigem(),
                passagem.getDestino(),
                passagem.getData(),
                passagem.getStatus());
    }
}
