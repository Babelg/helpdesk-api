-- V2: dados de exemplo para facilitar testes manuais via Swagger/Postman

INSERT INTO categoria (nome, descricao) VALUES
    ('TI', 'Problemas de hardware, software e acessos'),
    ('RH', 'Solicitações relacionadas a benefícios e folha de pagamento'),
    ('Facilities', 'Manutenção predial, limpeza e infraestrutura física');

INSERT INTO usuario (nome, email, papel) VALUES
    ('Ana Souza', 'ana.souza@empresa.com', 'SOLICITANTE'),
    ('Bruno Lima', 'bruno.lima@empresa.com', 'ATENDENTE'),
    ('Carla Mendes', 'carla.mendes@empresa.com', 'ADMIN');
