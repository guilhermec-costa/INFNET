package com.academia.poliglota.repository;

import com.academia.poliglota.dto.AlunoRankingResponse;
import com.academia.poliglota.model.Aluno;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    List<Aluno> findByAtivoTrue();

    @Query("""
            select new com.academia.poliglota.dto.AlunoRankingResponse(
                a.id,
                a.nome,
                count(at.id)
            )
            from Aluno a
            left join a.alunoTreinos at on at.statusConclusao = true
            group by a.id, a.nome
            order by count(at.id) desc, a.nome asc
            """)
    List<AlunoRankingResponse> buscarRankingPorConclusoes();
}
