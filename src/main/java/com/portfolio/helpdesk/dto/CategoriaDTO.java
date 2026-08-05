package com.portfolio.helpdesk.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoriaDTO {

    public record Request(
            @NotBlank(message = "O nome da categoria é obrigatório")
            @Size(max = 80, message = "O nome deve ter no máximo 80 caracteres")
            String nome,

            @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
            String descricao
    ) {}

    public record Response(
            Long id,
            String nome,
            String descricao
    ) {}
}
