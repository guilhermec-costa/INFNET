# Backend principal

API Spring Boot responsável por catálogo, leitores e circulação.

No TP4, empréstimos e devoluções gravam eventos na tabela `event_outbox` na mesma transação dos dados de negócio. O `OutboxPublisher` publica os eventos no TopicExchange `biblioteca.eventos.v1` com confirmação do RabbitMQ. O OpenFeign permanece somente para a consulta GET de notificações.

## Componentes de eventos

- `EmprestimoEvent`: contrato versionado;
- `OutboxEvent`: registro persistente do evento;
- `OutboxService`: grava o evento na transação;
- `OutboxPublisher`: publica pendências e aguarda publisher confirm;
- `RabbitMqConfig`: declara o TopicExchange durável.

Routing keys:

- `biblioteca.emprestimo.registrado.v1`;
- `biblioteca.emprestimo.devolvido.v1`.

## Execução e testes

O modo recomendado é pelo Compose da raiz. Para testes isolados:

```bash
mvn test
```

Localmente, RabbitMQ deve estar disponível em `localhost:5672` com usuário e senha `biblioteca`. As variáveis `RABBITMQ_HOST`, `RABBITMQ_PORT`, `RABBITMQ_USER` e `RABBITMQ_PASSWORD` permitem sobrescrever esses valores.
