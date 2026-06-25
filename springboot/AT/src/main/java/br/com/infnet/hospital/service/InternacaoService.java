package br.com.infnet.hospital.service;

import br.com.infnet.hospital.dto.InternacaoRequest;
import br.com.infnet.hospital.dto.InternacaoResponse;
import br.com.infnet.hospital.entity.Internacao;
import br.com.infnet.hospital.entity.Paciente;
import br.com.infnet.hospital.repository.InternacaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InternacaoService {

    private final InternacaoRepository internacaoRepository;
    private final PacienteService pacienteService;

    public InternacaoService(InternacaoRepository internacaoRepository, PacienteService pacienteService) {
        this.internacaoRepository = internacaoRepository;
        this.pacienteService = pacienteService;
    }

    @Transactional
    public InternacaoResponse cadastrar(InternacaoRequest request) {
        Paciente paciente = pacienteService.obterEntidade(request.pacienteId());

        Internacao internacao = new Internacao();
        internacao.setDataEntrada(request.dataEntrada());
        internacao.setDataAlta(request.dataAlta());
        internacao.setQuarto(request.quarto());
        internacao.setPaciente(paciente);

        return toResponse(internacaoRepository.save(internacao));
    }

    @Transactional(readOnly = true)
    public List<InternacaoResponse> listarPorPaciente(Long pacienteId) {
        return internacaoRepository.findByPacienteIdOrderByDataEntradaDesc(pacienteId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private InternacaoResponse toResponse(Internacao internacao) {
        return new InternacaoResponse(
                internacao.getId(),
                internacao.getDataEntrada(),
                internacao.getDataAlta(),
                internacao.getQuarto(),
                internacao.getPaciente().getId()
        );
    }
}
