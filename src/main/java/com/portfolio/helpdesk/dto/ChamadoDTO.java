package com.portfolio.helpdesk.dto;

import com.portfolio.helpdesk.model.Prioridade;
import com.portfolio.helpdesk.model.StatusChamado;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class ChamadoDTO {

    public record CreateRequest(
            @NotBlank(message = "O título é obrigatório")
            String titulo,

            @NotBlank(message = "A descrição é obrigatória")
            String descricao,

            @NotNull(message = "A categoria é obrigatória")
            Long categoriaId,

            @NotNull(message = "O solicitante é obrigatório")
            Long solicitanteId,

            @NotNull(message = "A prioridade é obrigatória")
            Prioridade prioridade
    ) {}

    public record AtribuicaoRequest(
            @NotNull(message = "O atendente é obrigatório")
            Long atendenteId
    ) {}

    public record StatusUpdateRequest(
            @NotNull(message = "O novo status é obrigatório")
            StatusChamado novoStatus
    ) {}

    public record Response(
            Long id,
            String titulo,
            String descricao,
            CategoriaDTO.Response categoria,
            UsuarioDTO.Response solicitante,
            UsuarioDTO.Response atendente,
            Prioridade prioridade,
            StatusChamado status,
            LocalDateTime criadoEm,
            LocalDateTime atribuidoEm,
            LocalDateTime resolvidoEm,
            LocalDateTime fechadoEm
    ) {}
}
