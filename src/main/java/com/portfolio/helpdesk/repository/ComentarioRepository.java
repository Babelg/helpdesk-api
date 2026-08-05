package com.portfolio.helpdesk.repository;

import com.portfolio.helpdesk.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByChamadoIdOrderByCriadoEmAsc(Long chamadoId);
}
