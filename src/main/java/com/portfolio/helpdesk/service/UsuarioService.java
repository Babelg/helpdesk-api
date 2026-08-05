package com.portfolio.helpdesk.service;

import com.portfolio.helpdesk.dto.UsuarioDTO;
import com.portfolio.helpdesk.exception.RegraDeNegocioException;
import com.portfolio.helpdesk.exception.ResourceNotFoundException;
import com.portfolio.helpdesk.mapper.UsuarioMapper;
import com.portfolio.helpdesk.model.Usuario;
import com.portfolio.helpdesk.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    public UsuarioDTO.Response criar(UsuarioDTO.Request dto) {
        usuarioRepository.findByEmailIgnoreCase(dto.email()).ifPresent(u -> {
            throw new RegraDeNegocioException("Já existe um usuário cadastrado com o email: " + dto.email());
        });

        Usuario usuario = usuarioMapper.toEntity(dto);
        return usuarioMapper.toResponse(usuarioRepository.save(usuario));
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO.Response> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(usuarioMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public UsuarioDTO.Response buscarPorId(Long id) {
        return usuarioMapper.toResponse(buscarEntidadePorId(id));
    }

    public UsuarioDTO.Response atualizar(Long id, UsuarioDTO.Request dto) {
        Usuario usuario = buscarEntidadePorId(id);

        usuarioRepository.findByEmailIgnoreCase(dto.email())
                .filter(outro -> !outro.getId().equals(id))
                .ifPresent(u -> {
                    throw new RegraDeNegocioException("Já existe outro usuário cadastrado com o email: " + dto.email());
                });

        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setPapel(dto.papel());
        return usuarioMapper.toResponse(usuarioRepository.save(usuario));
    }

    public void excluir(Long id) {
        Usuario usuario = buscarEntidadePorId(id);
        usuarioRepository.delete(usuario);
    }

    public Usuario buscarEntidadePorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id: " + id));
    }
}
