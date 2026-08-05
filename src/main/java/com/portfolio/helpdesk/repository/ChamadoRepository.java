package com.portfolio.helpdesk.repository;

import com.portfolio.helpdesk.model.Chamado;
import com.portfolio.helpdesk.model.Prioridade;
import com.portfolio.helpdesk.model.StatusChamado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChamadoRepository extends JpaRepository<Chamado, Long> {

    List<Chamado> findBySolicitanteId(Long solicitanteId);

    List<Chamado> findByAtendenteId(Long atendenteId);

    /**
     * Filtro combinado: cada parametro e opcional (passe null para ignorar o filtro).
     * Usado na listagem principal de chamados, respeitando o que cada papel pode ver
     * (essa restricao de visibilidade fica a cargo do Service, nao do repository).
     */
    @Query("""
            SELECT c FROM Chamado c
            WHERE (:status IS NULL OR c.status = :status)
              AND (:prioridade IS NULL OR c.prioridade = :prioridade)
              AND (:atendenteId IS NULL OR c.atendente.id = :atendenteId)
              AND (:solicitanteId IS NULL OR c.solicitante.id = :solicitanteId)
            ORDER BY c.criadoEm DESC
            """)
    List<Chamado> buscarComFiltros(
            @Param("status") StatusChamado status,
            @Param("prioridade") Prioridade prioridade,
            @Param("atendenteId") Long atendenteId,
            @Param("solicitanteId") Long solicitanteId
    );
}
