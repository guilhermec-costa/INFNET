package br.com.infnet.hospital.repository;

import br.com.infnet.hospital.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    Optional<Paciente> findByCpf(String cpf);

    List<Paciente> findByNomeContainingIgnoreCaseOrderByNomeAsc(String nome);
}
