package br.com.infnet.hospital.service;

import br.com.infnet.hospital.dto.MedicoConsultaResumoResponse;
import br.com.infnet.hospital.dto.MedicoRequest;
import br.com.infnet.hospital.dto.MedicoResponse;
import br.com.infnet.hospital.entity.Medico;
import br.com.infnet.hospital.exception.BusinessException;
import br.com.infnet.hospital.exception.ResourceNotFoundException;
import br.com.infnet.hospital.repository.MedicoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MedicoService {

    private final MedicoRepository medicoRepository;

    public MedicoService(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    @Transactional
    public MedicoResponse cadastrar(MedicoRequest request) {
        medicoRepository.findByCrm(request.crm())
                .ifPresent(medico -> {
                    throw new BusinessException("Já existe médico cadastrado com o CRM informado");
                });

        Medico medico = new Medico();
        medico.setNome(request.nome());
        medico.setCrm(request.crm());
        medico.setEspecialidade(request.especialidade());
        return toResponse(medicoRepository.save(medico));
    }

    @Transactional(readOnly = true)
    public List<MedicoResponse> listar() {
        return medicoRepository.findAllByOrderByNomeAsc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public MedicoResponse atualizar(Long id, MedicoRequest request) {
        Medico medico = obterEntidade(id);
        medicoRepository.findByCrm(request.crm())
                .filter(existente -> !existente.getId().equals(id))
                .ifPresent(existente -> {
                    throw new BusinessException("Já existe médico cadastrado com o CRM informado");
                });
        medico.setNome(request.nome());
        medico.setCrm(request.crm());
        medico.setEspecialidade(request.especialidade());
        return toResponse(medicoRepository.save(medico));
    }

    @Transactional(readOnly = true)
    public List<MedicoConsultaResumoResponse> listarPorQuantidadeConsultas() {
        return medicoRepository.findMedicosComMaisConsultas();
    }

    @Transactional(readOnly = true)
    public Medico obterEntidade(Long id) {
        return medicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Médico não encontrado com id " + id));
    }

    private MedicoResponse toResponse(Medico medico) {
        return new MedicoResponse(
                medico.getId(),
                medico.getNome(),
                medico.getCrm(),
                medico.getEspecialidade()
        );
    }
}
