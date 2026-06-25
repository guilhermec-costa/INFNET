package br.com.infnet.hospital.service;

import br.com.infnet.hospital.dto.PacienteRequest;
import br.com.infnet.hospital.dto.PacienteResponse;
import br.com.infnet.hospital.entity.Paciente;
import br.com.infnet.hospital.exception.BusinessException;
import br.com.infnet.hospital.exception.ResourceNotFoundException;
import br.com.infnet.hospital.repository.PacienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Transactional
    public PacienteResponse cadastrar(PacienteRequest request) {
        pacienteRepository.findByCpf(request.cpf())
                .ifPresent(paciente -> {
                    throw new BusinessException("Já existe paciente cadastrado com o CPF informado");
                });

        Paciente paciente = new Paciente();
        paciente.setNome(request.nome());
        paciente.setCpf(request.cpf());
        paciente.setDataNascimento(request.dataNascimento());
        paciente.setTelefone(request.telefone());

        return toResponse(pacienteRepository.save(paciente));
    }

    @Transactional(readOnly = true)
    public PacienteResponse buscarPorId(Long id) {
        return toResponse(obterEntidade(id));
    }

    @Transactional(readOnly = true)
    public List<PacienteResponse> listar() {
        return pacienteRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public PacienteResponse atualizar(Long id, PacienteRequest request) {
        Paciente paciente = obterEntidade(id);
        pacienteRepository.findByCpf(request.cpf())
                .filter(existente -> !existente.getId().equals(id))
                .ifPresent(existente -> {
                    throw new BusinessException("Já existe paciente cadastrado com o CPF informado");
                });

        paciente.setNome(request.nome());
        paciente.setCpf(request.cpf());
        paciente.setDataNascimento(request.dataNascimento());
        paciente.setTelefone(request.telefone());
        return toResponse(pacienteRepository.save(paciente));
    }

    @Transactional
    public void remover(Long id) {
        if (!pacienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Paciente não encontrado com id " + id);
        }
        pacienteRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Paciente obterEntidade(Long id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado com id " + id));
    }

    private PacienteResponse toResponse(Paciente paciente) {
        return new PacienteResponse(
                paciente.getId(),
                paciente.getNome(),
                paciente.getCpf(),
                paciente.getDataNascimento(),
                paciente.getTelefone()
        );
    }
}
