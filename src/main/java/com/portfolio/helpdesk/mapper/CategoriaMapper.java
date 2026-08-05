package com.portfolio.helpdesk.mapper;

import com.portfolio.helpdesk.dto.CategoriaDTO;
import com.portfolio.helpdesk.model.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {

    public Categoria toEntity(CategoriaDTO.Request dto) {
        Categoria categoria = new Categoria();
        categoria.setNome(dto.nome());
        categoria.setDescricao(dto.descricao());
        return categoria;
    }

    public CategoriaDTO.Response toResponse(Categoria categoria) {
        return new CategoriaDTO.Response(
                categoria.getId(),
                categoria.getNome(),
                categoria.getDescricao()
        );
    }
}
