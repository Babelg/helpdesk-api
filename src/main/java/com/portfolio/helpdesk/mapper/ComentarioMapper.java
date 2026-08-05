package com.portfolio.helpdesk.mapper;

import com.portfolio.helpdesk.dto.ComentarioDTO;
import com.portfolio.helpdesk.model.Comentario;
import org.springframework.stereotype.Component;

@Component
public class ComentarioMapper {

    private final UsuarioMapper usuarioMapper;

    public ComentarioMapper(UsuarioMapper usuarioMapper) {
        this.usuarioMapper = usuarioMapper;
    }

    public ComentarioDTO.Response toResponse(Comentario comentario) {
        return new ComentarioDTO.Response(
                comentario.getId(),
                usuarioMapper.toResponse(comentario.getAutor()),
                comentario.getTexto(),
                comentario.getCriadoEm()
        );
    }
}
