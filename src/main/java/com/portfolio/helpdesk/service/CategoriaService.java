package com.portfolio.helpdesk.service;

import com.portfolio.helpdesk.dto.CategoriaDTO;
import com.portfolio.helpdesk.exception.ResourceNotFoundException;
import com.portfolio.helpdesk.mapper.CategoriaMapper;
import com.portfolio.helpdesk.model.Categoria;
import com.portfolio.helpdesk.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    public CategoriaService(CategoriaRepository categoriaRepository, CategoriaMapper categoriaMapper) {
        this.categoriaRepository = categoriaRepository;
        this.categoriaMapper = categoriaMapper;
    }

    public CategoriaDTO.Response criar(CategoriaDTO.Request dto) {
        Categoria categoria = categoriaMapper.toEntity(dto);
        return categoriaMapper.toResponse(categoriaRepository.save(categoria));
    }

    @Transactional(readOnly = true)
    public List<CategoriaDTO.Response> listarTodas() {
        return categoriaRepository.findAll().stream()
                .map(categoriaMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public CategoriaDTO.Response buscarPorId(Long id) {
        return categoriaMapper.toResponse(buscarEntidadePorId(id));
    }

    public CategoriaDTO.Response atualizar(Long id, CategoriaDTO.Request dto) {
        Categoria categoria = buscarEntidadePorId(id);
        categoria.setNome(dto.nome());
        categoria.setDescricao(dto.descricao());
        return categoriaMapper.toResponse(categoriaRepository.save(categoria));
    }

    public void excluir(Long id) {
        Categoria categoria = buscarEntidadePorId(id);
        categoriaRepository.delete(categoria);
    }

    public Categoria buscarEntidadePorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com id: " + id));
    }
}
