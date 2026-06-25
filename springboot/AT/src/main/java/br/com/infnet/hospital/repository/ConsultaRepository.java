package br.com.infnet.hospital.repository;

import br.com.infnet.hospital.entity.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    List<Consulta> findByPacienteIdOrderByDataConsultaDesc(Long pacienteId);
}
