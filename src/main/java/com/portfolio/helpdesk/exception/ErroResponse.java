package com.portfolio.helpdesk.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ErroResponse(
        LocalDateTime timestamp,
        int status,
        String erro,
        String mensagem,
        List<String> detalhes
) {
    public static ErroResponse of(int status, String erro, String mensagem) {
        return new ErroResponse(LocalDateTime.now(), status, erro, mensagem, null);
    }

    public static ErroResponse of(int status, String erro, String mensagem, List<String> detalhes) {
        return new ErroResponse(LocalDateTime.now(), status, erro, mensagem, detalhes);
    }
}
