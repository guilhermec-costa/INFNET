# TP1 - API REST de Operações Matemáticas

Projeto Spring Boot desenvolvido para disponibilizar cinco operações matemáticas básicas por meio de endpoints REST sem estado:

- adição
- subtração
- multiplicação
- divisão
- exponenciação

Cada endpoint aceita requisições `GET` e `POST` com o mesmo comportamento.

## Tecnologias

- Java 21
- Spring Boot 3.3.5
- Maven 3
- JUnit 5 / MockMvc

## Como executar

```bash
mvn spring-boot:run
```

A aplicação sobe por padrão em `http://localhost:8080`.

## Endpoints

### GET

```text
GET /api/math/add?a=10&b=5
GET /api/math/subtract?a=10&b=3
GET /api/math/multiply?a=4&b=6
GET /api/math/divide?a=20&b=4
GET /api/math/power?a=2&b=3
```

### POST

```text
POST /api/math/add
POST /api/math/subtract
POST /api/math/multiply
POST /api/math/divide
POST /api/math/power
```

Exemplo de corpo da requisição em JSON:

```json
{
  "a": 10,
  "b": 5
}
```

## Exemplo de resposta

```json
{
  "operation": "addition",
  "a": 10,
  "b": 5,
  "result": 15
}
```

## Testes

```bash
mvn test
```

## Relatório

O relatório detalhado da atividade está em [RELATORIO.md](RELATORIO.md).
