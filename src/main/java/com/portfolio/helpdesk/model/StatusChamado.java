package com.portfolio.helpdesk.model;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

/**
 * Representa o status do chamado e as transicoes permitidas entre eles.
 *
 * Fluxo:
 *   ABERTO -> EM_ANDAMENTO -> RESOLVIDO -> FECHADO
 *   RESOLVIDO -> REABERTO -> EM_ANDAMENTO (reabertura dentro do prazo)
 */
public enum StatusChamado {
    ABERTO,
    EM_ANDAMENTO,
    RESOLVIDO,
    FECHADO,
    REABERTO;

    private static final Map<StatusChamado, Set<StatusChamado>> TRANSICOES_PERMITIDAS = new EnumMap<>(StatusChamado.class);

    static {
        TRANSICOES_PERMITIDAS.put(ABERTO, EnumSet.of(EM_ANDAMENTO));
        TRANSICOES_PERMITIDAS.put(EM_ANDAMENTO, EnumSet.of(RESOLVIDO));
        TRANSICOES_PERMITIDAS.put(RESOLVIDO, EnumSet.of(FECHADO, REABERTO));
        TRANSICOES_PERMITIDAS.put(REABERTO, EnumSet.of(EM_ANDAMENTO));
        TRANSICOES_PERMITIDAS.put(FECHADO, EnumSet.noneOf(StatusChamado.class));
    }

    public boolean podeTransicionarPara(StatusChamado novoStatus) {
        return TRANSICOES_PERMITIDAS.getOrDefault(this, EnumSet.noneOf(StatusChamado.class))
                .contains(novoStatus);
    }
}
