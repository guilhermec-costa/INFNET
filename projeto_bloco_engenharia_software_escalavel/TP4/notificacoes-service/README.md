# Microsserviço de Notificações

Consumidor Spring Boot dos eventos de empréstimo e devolução publicados no RabbitMQ. O serviço mantém sua própria base H2 e expõe somente consulta REST.

## Consumo de eventos

A fila durável `notificacoes.emprestimos.v1` recebe as routing keys `biblioteca.emprestimo.*.v1`. O listener usa `@RabbitListener`, tenta cada mensagem até três vezes e encaminha falhas persistentes para `notificacoes.emprestimos.dlq.v1`.

O campo `eventId` tem restrição única. Antes de persistir, o serviço verifica esse identificador, garantindo idempotência diante de reentregas.

## Endpoint

| Método | Endpoint | Finalidade |
| --- | --- | --- |
| GET | `/api/notificacoes/leitor/{leitorId}` | Lista avisos do leitor em ordem decrescente. |

A escrita por POST foi removida: notificações agora são criadas exclusivamente a partir de eventos.

## Execução e testes

Use `docker compose up --build` na raiz do TP4. Para testes isolados:

```bash
mvn test
```
