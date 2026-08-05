-- V1: criação do schema inicial do Help Desk

CREATE TABLE usuario (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    papel VARCHAR(20) NOT NULL CHECK (papel IN ('SOLICITANTE', 'ATENDENTE', 'ADMIN')),
    criado_em TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE categoria (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(80) NOT NULL UNIQUE,
    descricao VARCHAR(255)
);

CREATE TABLE chamado (
    id BIGSERIAL PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    descricao TEXT NOT NULL,
    categoria_id BIGINT NOT NULL REFERENCES categoria(id),
    solicitante_id BIGINT NOT NULL REFERENCES usuario(id),
    atendente_id BIGINT REFERENCES usuario(id),
    prioridade VARCHAR(10) NOT NULL CHECK (prioridade IN ('BAIXA', 'MEDIA', 'ALTA', 'URGENTE')),
    status VARCHAR(20) NOT NULL CHECK (status IN ('ABERTO', 'EM_ANDAMENTO', 'RESOLVIDO', 'FECHADO', 'REABERTO')),
    criado_em TIMESTAMP NOT NULL DEFAULT now(),
    atribuido_em TIMESTAMP,
    resolvido_em TIMESTAMP,
    fechado_em TIMESTAMP
);

CREATE TABLE comentario (
    id BIGSERIAL PRIMARY KEY,
    chamado_id BIGINT NOT NULL REFERENCES chamado(id) ON DELETE CASCADE,
    autor_id BIGINT NOT NULL REFERENCES usuario(id),
    texto TEXT NOT NULL,
    criado_em TIMESTAMP NOT NULL DEFAULT now()
);

CREATE INDEX idx_chamado_status ON chamado(status);
CREATE INDEX idx_chamado_atendente ON chamado(atendente_id);
CREATE INDEX idx_chamado_solicitante ON chamado(solicitante_id);
CREATE INDEX idx_comentario_chamado ON comentario(chamado_id);
