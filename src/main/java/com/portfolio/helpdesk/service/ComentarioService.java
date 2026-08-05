package com.portfolio.helpdesk.service;

import com.portfolio.helpdesk.dto.ComentarioDTO;
import com.portfolio.helpdesk.mapper.ComentarioMapper;
import com.portfolio.helpdesk.model.Chamado;
import com.portfolio.helpdesk.model.Comentario;
import com.portfolio.helpdesk.model.Usuario;
import com.portfolio.helpdesk.repository.ComentarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final ComentarioMapper comentarioMapper;
    private final ChamadoService chamadoService;
    private final UsuarioService usuarioService;

    public ComentarioService(ComentarioRepository comentarioRepository,
                              ComentarioMapper comentarioMapper,
                              ChamadoService chamadoService,
                              UsuarioService usuarioService) {
        this.comentarioRepository = comentarioRepository;
        this.comentarioMapper = comentarioMapper;
        this.chamadoService = chamadoService;
        this.usuarioService = usuarioService;
    }

    public ComentarioDTO.Response adicionar(Long chamadoId, ComentarioDTO.Request dto) {
        Chamado chamado = chamadoService.buscarEntidadePorId(chamadoId);
        Usuario autor = usuarioService.buscarEntidadePorId(dto.autorId());

        Comentario comentario = new Comentario();
        comentario.setChamado(chamado);
        comentario.setAutor(autor);
        comentario.setTexto(dto.texto());

        return comentarioMapper.toResponse(comentarioRepository.save(comentario));
    }

    @Transactional(readOnly = true)
    public List<ComentarioDTO.Response> listarPorChamado(Long chamadoId) {
        // garante que o chamado existe antes de listar (lança 404 se nao existir)
        chamadoService.buscarEntidadePorId(chamadoId);

        return comentarioRepository.findByChamadoIdOrderByCriadoEmAsc(chamadoId).stream()
                .map(comentarioMapper::toResponse)
                .toList();
    }
}
