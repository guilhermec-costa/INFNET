package br.com.infnet.hospital.repository;

import br.com.infnet.hospital.entity.Internacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InternacaoRepository extends JpaRepository<Internacao, Long> {

    List<Internacao> findByPacienteIdOrderByDataEntradaDesc(Long pacienteId);
}
