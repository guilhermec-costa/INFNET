package br.com.infnet.hospital.service;

import br.com.infnet.hospital.dto.MedicoRequest;
import br.com.infnet.hospital.dto.MedicoResponse;
import br.com.infnet.hospital.entity.Medico;
import br.com.infnet.hospital.repository.MedicoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MedicoServiceTest {

    @Mock
    private MedicoRepository medicoRepository;

    @InjectMocks
    private MedicoService medicoService;

    @Test
    void deveCadastrarMedico() {
        MedicoRequest request = new MedicoRequest("Dra. Paula Lima", "CRM-5001", "Dermatologia");

        Medico salvo = new Medico();
        salvo.setId(10L);
        salvo.setNome(request.nome());
        salvo.setCrm(request.crm());
        salvo.setEspecialidade(request.especialidade());

        when(medicoRepository.findByCrm(request.crm())).thenReturn(Optional.empty());
        when(medicoRepository.save(any(Medico.class))).thenReturn(salvo);

        MedicoResponse response = medicoService.cadastrar(request);

        assertEquals(10L, response.id());
        assertEquals("Dermatologia", response.especialidade());
        verify(medicoRepository).save(any(Medico.class));
    }
}
