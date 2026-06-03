# Backend

API monolítica simples de biblioteca construída com Spring Boot.

## Funcionalidades

- CRUD de livros
- CRUD de leitores
- Registro de empréstimo
- Registro de devolução
- Listagem de livros disponíveis

## Estrutura

- `controller`: endpoints REST
- `service`: regras de negócio
- `repository`: persistência com Spring Data JPA
- `model`: entidades do domínio
- `dto`: contratos de entrada e saída

## Executando

```bash
mvn spring-boot:run
```

A API sobe em `http://localhost:8080`.

## Endpoints principais

- `GET /api/livros`
- `GET /api/livros/disponiveis`
- `POST /api/livros`
- `PUT /api/livros/{id}`
- `DELETE /api/livros/{id}`
- `GET /api/leitores`
- `POST /api/leitores`
- `PUT /api/leitores/{id}`
- `DELETE /api/leitores/{id}`
- `GET /api/emprestimos`
- `GET /api/emprestimos/ativos`
- `POST /api/emprestimos`
- `POST /api/emprestimos/{id}/devolucao`

## Banco de dados

O projeto usa H2 em memória para simplificar a primeira entrega.
