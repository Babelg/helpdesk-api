##  Autor
Guilherme Babel Machado — [[LinkedIn]](https://www.linkedin.com/in/guilherme-babel-machado-25a4531ba/)


# Help Desk API

API REST para gestão de chamados internos de uma empresa (TI, RH, Facilities), construída como projeto de portfólio para demonstrar arquitetura em camadas, regras de negócio (máquina de estados) e boas práticas com Spring Boot.

##  Funcionalidades

- CRUD completo de **Categorias**, **Usuários** e **Chamados**
- Atribuição de atendente a um chamado
- Máquina de estados para o ciclo de vida do chamado: `ABERTO → EM_ANDAMENTO → RESOLVIDO → FECHADO`, com possibilidade de `REABERTO`
- Comentários/histórico de interação dentro de um chamado
- Filtros combináveis na listagem de chamados (status, prioridade, atendente, solicitante)
- Tratamento de erros padronizado (`@RestControllerAdvice`)
- Documentação automática via Swagger/OpenAPI

##  Stack

- Java 17
- Spring Boot 3.3 (Web, Data JPA, Validation)
- PostgreSQL
- Flyway (versionamento de schema)
- springdoc-openapi (Swagger UI)
- Lombok
- Maven

##  Arquitetura

```
com.portfolio.helpdesk
├── controller     → endpoints REST
├── service        → regras de negócio
├── repository     → interfaces Spring Data JPA
├── model          → entidades JPA e enums
├── dto            → records de request/response (nunca expõe entidade direto)
├── mapper         → conversão entidade ↔ DTO
├── exception      → exceções customizadas + handler global
└── config         → configuração do OpenAPI/Swagger
```

##  Como rodar

### Pré-requisitos
- JDK 17+
- Docker e Docker Compose
- Maven (ou usar o wrapper, se adicionado no IDE)

### Passos

1. Suba o banco de dados PostgreSQL:
```bash
docker-compose up -d
```

2. Rode a aplicação (pelo IntelliJ, ou via terminal):
```bash
mvn spring-boot:run
```

3. As migrações do Flyway rodam automaticamente na inicialização, criando o schema e inserindo dados de exemplo.

4. Acesse a documentação interativa:
```
http://localhost:8080/swagger-ui.html
```

##  Modelo de domínio

- **Usuário**: `SOLICITANTE`, `ATENDENTE` ou `ADMIN`
- **Categoria**: TI, RH, Facilities, etc.
- **Chamado**: título, descrição, categoria, solicitante, atendente (opcional), prioridade e status
- **Comentário**: histórico de interação vinculado a um chamado

##  Máquina de estados do chamado

```
ABERTO ──────────► EM_ANDAMENTO ──────────► RESOLVIDO ──────────► FECHADO
                         ▲                       │
                         └────────── REABERTO ◄──┘
```

Regras aplicadas no `ChamadoService`:
- Só é possível avançar seguindo as transições permitidas (não dá pra pular de `ABERTO` direto pra `FECHADO`)
- Um chamado só pode entrar em `EM_ANDAMENTO` se já tiver um atendente atribuído
- Só usuários com papel `ATENDENTE` ou `ADMIN` podem ser atribuídos como atendentes de um chamado

##  Principais endpoints

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/api/categorias` | Cria categoria |
| GET | `/api/categorias` | Lista categorias |
| POST | `/api/usuarios` | Cria usuário |
| GET | `/api/usuarios` | Lista usuários |
| POST | `/api/chamados` | Abre um chamado |
| GET | `/api/chamados?status=&prioridade=&atendenteId=&solicitanteId=` | Lista chamados com filtros |
| PATCH | `/api/chamados/{id}/atribuir` | Atribui um atendente |
| PATCH | `/api/chamados/{id}/status` | Altera o status (valida a transição) |
| POST | `/api/chamados/{id}/comentarios` | Adiciona comentário |
| GET | `/api/chamados/{id}/comentarios` | Lista comentários do chamado |


