# Roteiro de demonstração — TP4

Duração sugerida: 5 a 7 minutos.

## 1. Introdução

“Na versão anterior, o backend chamava o serviço de notificações por HTTP durante o empréstimo. Isso criava acoplamento temporal. Refatorei a escrita para RabbitMQ, com Transactional Outbox, retry, DLQ e consumidor idempotente.”

## 2. Subir e apresentar a arquitetura

```bash
docker compose up --build
docker compose ps
```

Abra:

- aplicação: http://localhost:4273
- RabbitMQ: http://localhost:15672, usuário e senha `biblioteca`

Mostre as filas `notificacoes.emprestimos.v1` e `notificacoes.emprestimos.dlq.v1`, ambas duráveis.

## 3. Caminho feliz

Na interface:

1. cadastre um leitor;
2. cadastre um livro;
3. registre o empréstimo;
4. abra “Ver notificações” e aguarde até 2 segundos;
5. registre a devolução;
6. abra novamente as notificações.

Explique que foram usados dois tipos de evento versionados:

- `biblioteca.emprestimo.registrado.v1`;
- `biblioteca.emprestimo.devolvido.v1`.

No painel do RabbitMQ, mostre o gráfico de mensagens entregues e confirmadas.

## 4. Cenário de resiliência

Pare somente o broker:

```bash
docker compose stop rabbitmq
```

Com a aplicação ainda aberta, cadastre outro livro e registre um empréstimo. O empréstimo deve funcionar, pois a transação salva o evento na Outbox. A notificação ainda não aparecerá.

Recupere o broker:

```bash
docker compose start rabbitmq
```

Após alguns segundos, atualize as notificações. O evento pendente terá sido publicado e consumido automaticamente.

## 5. Fechamento

“Com essa mudança, o produtor não depende da disponibilidade do consumidor. A fila permite escala horizontal, a Outbox evita perda entre banco e broker, o publisher confirm confirma a publicação, a idempotência tolera reentregas e a DLQ isola falhas persistentes. O custo é a consistência eventual e maior complexidade operacional.”
