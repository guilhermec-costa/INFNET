package br.com.infnet.hospital.service;

import br.com.infnet.hospital.dto.PacienteRequest;
import br.com.infnet.hospital.dto.PacienteResponse;
import br.com.infnet.hospital.entity.Paciente;
import br.com.infnet.hospital.exception.ResourceNotFoundException;
import br.com.infnet.hospital.repository.PacienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PacienteServiceTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @InjectMocks
    private PacienteService pacienteService;

    private PacienteRequest request;

    @BeforeEach
    void setUp() {
        request = new PacienteRequest(
                "Carlos Souza",
                "98765432100",
                LocalDate.of(1992, 7, 15),
                "21999998888"
        );
    }

    @Test
    void deveCadastrarPaciente() {
        Paciente salvo = new Paciente();
        salvo.setId(1L);
        salvo.setNome(request.nome());
        salvo.setCpf(request.cpf());
        salvo.setDataNascimento(request.dataNascimento());
        salvo.setTelefone(request.telefone());

        when(pacienteRepository.findByCpf(request.cpf())).thenReturn(Optional.empty());
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(salvo);

        PacienteResponse response = pacienteService.cadastrar(request);

        assertEquals(1L, response.id());
        assertEquals("Carlos Souza", response.nome());
        verify(pacienteRepository).save(any(Paciente.class));
    }

    @Test
    void deveBuscarPacientePorId() {
        Paciente paciente = new Paciente();
        paciente.setId(2L);
        paciente.setNome("Maria Teste");
        paciente.setCpf("11122233344");
        paciente.setDataNascimento(LocalDate.of(1988, 2, 1));
        paciente.setTelefone("21911112222");

        when(pacienteRepository.findById(2L)).thenReturn(Optional.of(paciente));

        PacienteResponse response = pacienteService.buscarPorId(2L);

        assertEquals("Maria Teste", response.nome());
        assertEquals("11122233344", response.cpf());
    }

    @Test
    void deveExcluirPaciente() {
        when(pacienteRepository.existsById(3L)).thenReturn(true);

        pacienteService.remover(3L);

        verify(pacienteRepository).deleteById(3L);
    }

    @Test
    void deveLancarExcecaoQuandoPacienteNaoExiste() {
        when(pacienteRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> pacienteService.buscarPorId(99L));
        verify(pacienteRepository, never()).save(any(Paciente.class));
    }
}
