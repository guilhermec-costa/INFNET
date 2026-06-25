package br.com.infnet.hospital.config;

import br.com.infnet.hospital.entity.Medico;
import br.com.infnet.hospital.entity.Paciente;
import br.com.infnet.hospital.repository.MedicoRepository;
import br.com.infnet.hospital.repository.PacienteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;

@Configuration
@Profile("!test")
public class DataInitializer {

    @Bean
    CommandLineRunner loadData(MedicoRepository medicoRepository, PacienteRepository pacienteRepository) {
        return args -> {
            if (medicoRepository.count() == 0) {
                Medico cardiologista = new Medico();
                cardiologista.setNome("Cardiologista");
                cardiologista.setCrm("CRM-1001");
                cardiologista.setEspecialidade("Cardiologia");

                Medico ortopedista = new Medico();
                ortopedista.setNome("Ortopedista");
                ortopedista.setCrm("CRM-1002");
                ortopedista.setEspecialidade("Ortopedia");

                medicoRepository.save(cardiologista);
                medicoRepository.save(ortopedista);
            }

            if (pacienteRepository.count() == 0) {
                Paciente joao = new Paciente();
                joao.setNome("João Silva");
                joao.setCpf("12345678901");
                joao.setDataNascimento(LocalDate.of(1990, 5, 10));
                joao.setTelefone("21999990001");

                Paciente maria = new Paciente();
                maria.setNome("Maria Oliveira");
                maria.setCpf("12345678902");
                maria.setDataNascimento(LocalDate.of(1985, 8, 20));
                maria.setTelefone("21999990002");

                pacienteRepository.save(joao);
                pacienteRepository.save(maria);
            }
        };
    }
}
