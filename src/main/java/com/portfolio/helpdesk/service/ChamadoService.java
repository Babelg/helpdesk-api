package com.portfolio.helpdesk.service;

import com.portfolio.helpdesk.dto.ChamadoDTO;
import com.portfolio.helpdesk.exception.RegraDeNegocioException;
import com.portfolio.helpdesk.exception.ResourceNotFoundException;
import com.portfolio.helpdesk.exception.TransicaoInvalidaException;
import com.portfolio.helpdesk.mapper.ChamadoMapper;
import com.portfolio.helpdesk.model.*;
import com.portfolio.helpdesk.repository.ChamadoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ChamadoService {

    private final ChamadoRepository chamadoRepository;
    private final ChamadoMapper chamadoMapper;
    private final CategoriaService categoriaService;
    private final UsuarioService usuarioService;

    public ChamadoService(ChamadoRepository chamadoRepository,
                           ChamadoMapper chamadoMapper,
                           CategoriaService categoriaService,
                           UsuarioService usuarioService) {
        this.chamadoRepository = chamadoRepository;
        this.chamadoMapper = chamadoMapper;
        this.categoriaService = categoriaService;
        this.usuarioService = usuarioService;
    }

    public ChamadoDTO.Response criar(ChamadoDTO.CreateRequest dto) {
        Categoria categoria = categoriaService.buscarEntidadePorId(dto.categoriaId());
        Usuario solicitante = usuarioService.buscarEntidadePorId(dto.solicitanteId());

        Chamado chamado = new Chamado();
        chamado.setTitulo(dto.titulo());
        chamado.setDescricao(dto.descricao());
        chamado.setCategoria(categoria);
        chamado.setSolicitante(solicitante);
        chamado.setPrioridade(dto.prioridade());
        chamado.setStatus(StatusChamado.ABERTO);

        return chamadoMapper.toResponse(chamadoRepository.save(chamado));
    }

    @Transactional(readOnly = true)
    public List<ChamadoDTO.Response> listarComFiltros(StatusChamado status, Prioridade prioridade,
                                                        Long atendenteId, Long solicitanteId) {
        return chamadoRepository.buscarComFiltros(status, prioridade, atendenteId, solicitanteId).stream()
                .map(chamadoMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ChamadoDTO.Response buscarPorId(Long id) {
        return chamadoMapper.toResponse(buscarEntidadePorId(id));
    }

    /**
     * Atribui um atendente ao chamado. Apenas usuarios com papel ATENDENTE ou ADMIN
     * podem ser designados para atender chamados.
     */
    public ChamadoDTO.Response atribuir(Long chamadoId, ChamadoDTO.AtribuicaoRequest dto) {
        Chamado chamado = buscarEntidadePorId(chamadoId);
        Usuario atendente = usuarioService.buscarEntidadePorId(dto.atendenteId());

        if (atendente.getPapel() == Papel.SOLICITANTE) {
            throw new RegraDeNegocioException(
                    "Usuário com papel SOLICITANTE não pode ser atribuído como atendente de um chamado");
        }

        chamado.setAtendente(atendente);
        chamado.setAtribuidoEm(LocalDateTime.now());

        return chamadoMapper.toResponse(chamadoRepository.save(chamado));
    }

    /**
     * Altera o status do chamado respeitando a maquina de estados definida em StatusChamado.
     * Chamado so pode ser atribuido a EM_ANDAMENTO se ja tiver um atendente definido.
     */
    public ChamadoDTO.Response alterarStatus(Long chamadoId, ChamadoDTO.StatusUpdateRequest dto) {
        Chamado chamado = buscarEntidadePorId(chamadoId);
        StatusChamado statusAtual = chamado.getStatus();
        StatusChamado novoStatus = dto.novoStatus();

        if (!statusAtual.podeTransicionarPara(novoStatus)) {
            throw new TransicaoInvalidaException(
                    "Não é possível mudar o chamado de %s para %s".formatted(statusAtual, novoStatus));
        }

        if (novoStatus == StatusChamado.EM_ANDAMENTO && chamado.getAtendente() == null) {
            throw new RegraDeNegocioException(
                    "O chamado precisa de um atendente atribuído antes de entrar em andamento");
        }

        chamado.setStatus(novoStatus);

        switch (novoStatus) {
            case RESOLVIDO -> chamado.setResolvidoEm(LocalDateTime.now());
            case FECHADO -> chamado.setFechadoEm(LocalDateTime.now());
            case REABERTO -> {
                chamado.setResolvidoEm(null);
                chamado.setFechadoEm(null);
            }
            default -> { /* nao precisa marcar data extra para ABERTO/EM_ANDAMENTO */ }
        }

        return chamadoMapper.toResponse(chamadoRepository.save(chamado));
    }

    public void excluir(Long id) {
        Chamado chamado = buscarEntidadePorId(id);
        chamadoRepository.delete(chamado);
    }

    protected Chamado buscarEntidadePorId(Long id) {
        return chamadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chamado não encontrado com id: " + id));
    }
}
