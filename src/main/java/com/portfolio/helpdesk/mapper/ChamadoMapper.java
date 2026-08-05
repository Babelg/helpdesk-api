package com.portfolio.helpdesk.mapper;

import com.portfolio.helpdesk.dto.ChamadoDTO;
import com.portfolio.helpdesk.model.Chamado;
import org.springframework.stereotype.Component;

@Component
public class ChamadoMapper {

    private final CategoriaMapper categoriaMapper;
    private final UsuarioMapper usuarioMapper;

    public ChamadoMapper(CategoriaMapper categoriaMapper, UsuarioMapper usuarioMapper) {
        this.categoriaMapper = categoriaMapper;
        this.usuarioMapper = usuarioMapper;
    }

    public ChamadoDTO.Response toResponse(Chamado chamado) {
        return new ChamadoDTO.Response(
                chamado.getId(),
                chamado.getTitulo(),
                chamado.getDescricao(),
                categoriaMapper.toResponse(chamado.getCategoria()),
                usuarioMapper.toResponse(chamado.getSolicitante()),
                usuarioMapper.toResponse(chamado.getAtendente()),
                chamado.getPrioridade(),
                chamado.getStatus(),
                chamado.getCriadoEm(),
                chamado.getAtribuidoEm(),
                chamado.getResolvidoEm(),
                chamado.getFechadoEm()
        );
    }
}
