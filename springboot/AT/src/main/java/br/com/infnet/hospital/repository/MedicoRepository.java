package br.com.infnet.hospital.repository;

import br.com.infnet.hospital.dto.MedicoConsultaResumoResponse;
import br.com.infnet.hospital.entity.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

    Optional<Medico> findByCrm(String crm);

    List<Medico> findAllByOrderByNomeAsc();

    @Query("""
            select new br.com.infnet.hospital.dto.MedicoConsultaResumoResponse(
                m.id,
                m.nome,
                m.especialidade,
                count(c.id)
            )
            from Medico m
            left join m.consultas c
            group by m.id, m.nome, m.especialidade
            order by count(c.id) desc, m.nome asc
            """)
    List<MedicoConsultaResumoResponse> findMedicosComMaisConsultas();
}
