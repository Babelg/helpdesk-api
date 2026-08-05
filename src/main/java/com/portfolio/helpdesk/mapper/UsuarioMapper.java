package com.portfolio.helpdesk.mapper;

import com.portfolio.helpdesk.dto.UsuarioDTO;
import com.portfolio.helpdesk.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toEntity(UsuarioDTO.Request dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setPapel(dto.papel());
        return usuario;
    }

    public UsuarioDTO.Response toResponse(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        return new UsuarioDTO.Response(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPapel(),
                usuario.getCriadoEm()
        );
    }
}
