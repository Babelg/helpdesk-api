package com.portfolio.helpdesk.repository;

import com.portfolio.helpdesk.model.Papel;
import com.portfolio.helpdesk.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmailIgnoreCase(String email);
    List<Usuario> findByPapel(Papel papel);
}
