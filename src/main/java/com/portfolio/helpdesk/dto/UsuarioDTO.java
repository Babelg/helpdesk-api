package com.portfolio.helpdesk.dto;

import com.portfolio.helpdesk.model.Papel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class UsuarioDTO {

    public record Request(
            @NotBlank(message = "O nome é obrigatório")
            String nome,

            @NotBlank(message = "O email é obrigatório")
            @Email(message = "Email inválido")
            String email,

            @NotNull(message = "O papel é obrigatório")
            Papel papel
    ) {}

    public record Response(
            Long id,
            String nome,
            String email,
            Papel papel,
            LocalDateTime criadoEm
    ) {}
}
