# Microsserviço de Notificações

Serviço independente responsável pelo histórico de avisos enviados aos leitores. Ele não acessa o banco da aplicação de biblioteca: recebe os dados necessários por HTTP e grava somente a sua própria base H2.

## Executar

### Pelo Docker Compose do TP3

Na pasta pai (`TP3`):

```bash
docker compose up --build notificacoes-service
```

Nesse modo, ele fica disponível em `http://localhost:8181`.

### Localmente

```bash
mvn spring-boot:run
```

O serviço inicia em `http://localhost:8081`. O arquivo `application.properties` usa `spring.config.import` com Config Server opcional, deixando o serviço pronto para configuração centralizada por Spring Cloud sem exigir um servidor adicional no ambiente local.

## Endpoints

| Método | Endpoint | Finalidade |
| --- | --- | --- |
| `POST` | `/api/notificacoes` | Registra um novo aviso para um leitor. |
| `GET` | `/api/notificacoes/leitor/{leitorId}` | Lista os avisos do leitor, do mais recente ao mais antigo. |

Exemplo de criação:

```json
{
  "leitorId": 1,
  "leitorNome": "Ana Costa",
  "tipo": "EMPRESTIMO_REGISTRADO",
  "titulo": "Empréstimo registrado",
  "mensagem": "O livro deve ser devolvido até 2026-08-30."
}
```

## Testes

```bash
mvn test
```

Os testes cobrem o repositório dedicado, incluindo a ordenação e o isolamento por leitor, e o serviço de criação de notificações.
