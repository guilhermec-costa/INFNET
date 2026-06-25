package br.com.infnet.hospital.service;

import br.com.infnet.hospital.dto.ConsultaRequest;
import br.com.infnet.hospital.dto.ConsultaResponse;
import br.com.infnet.hospital.entity.Consulta;
import br.com.infnet.hospital.entity.Medico;
import br.com.infnet.hospital.entity.Paciente;
import br.com.infnet.hospital.repository.ConsultaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final PacienteService pacienteService;
    private final MedicoService medicoService;

    public ConsultaService(ConsultaRepository consultaRepository, PacienteService pacienteService, MedicoService medicoService) {
        this.consultaRepository = consultaRepository;
        this.pacienteService = pacienteService;
        this.medicoService = medicoService;
    }

    @Transactional
    public ConsultaResponse cadastrar(ConsultaRequest request) {
        Paciente paciente = pacienteService.obterEntidade(request.pacienteId());
        Medico medico = medicoService.obterEntidade(request.medicoId());

        Consulta consulta = new Consulta();
        consulta.setDataConsulta(request.dataConsulta());
        consulta.setObservacoes(request.observacoes());
        consulta.setPaciente(paciente);
        consulta.setMedico(medico);

        return toResponse(consultaRepository.save(consulta));
    }

    private ConsultaResponse toResponse(Consulta consulta) {
        return new ConsultaResponse(
                consulta.getId(),
                consulta.getDataConsulta(),
                consulta.getObservacoes(),
                consulta.getPaciente().getId(),
                consulta.getPaciente().getNome(),
                consulta.getMedico().getId(),
                consulta.getMedico().getNome()
        );
    }
}
