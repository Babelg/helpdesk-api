package com.portfolio.helpdesk.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class ComentarioDTO {

    public record Request(
            @NotNull(message = "O autor é obrigatório")
            Long autorId,

            @NotBlank(message = "O texto do comentário é obrigatório")
            String texto
    ) {}

    public record Response(
            Long id,
            UsuarioDTO.Response autor,
            String texto,
            LocalDateTime criadoEm
    ) {}
}
