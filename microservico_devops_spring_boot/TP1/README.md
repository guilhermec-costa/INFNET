# Plataforma de Reservas de Salas

A aplicação permite criar reservas de salas e registrar uma confirmação para o solicitante.

## Integrante

| Nome | Turma | Responsabilidades |
|---|---|---|
| Guilherme de Morais China Costa | GRLEDS01C2-N2-LC | `reservation-service`, `notification-service`, API Gateway, Discovery Server, bancos, documentação e execução |

## Arquitetura e serviços

| Serviço | Responsabilidade | Porta | Banco |
|---|---|---:|---|
| `discovery-server` | Registro e descoberta dos serviços (Eureka) | 8761 | — |
| `api-gateway` | Ponto único de entrada e roteamento | 8080 | — |
| `reservation-service` | Criação e consulta de reservas | 8081 | PostgreSQL: `reservation_db` |
| `notification-service` | Histórico de notificações de confirmação | 8082 | MongoDB: `notification_db` |

Separei os dados desde o início. O serviço de reservas não lê nem grava no banco de notificações, e o serviço de notificações também não acessa as tabelas de reservas. Os dois bancos sobem na mesma composição apenas para facilitar o teste local.

Usei MongoDB para as notificações porque, mais para frente, uma mensagem por e-mail pode ter campos diferentes de uma notificação push ou SMS. Para as reservas, preferi PostgreSQL, já que os dados têm uma estrutura mais fixa. A justificativa completa está em [docs/PROPOSTA.md](docs/PROPOSTA.md).

## Escalabilidade e distribuição

Cada serviço pode subir separado, com sua própria porta e configuração. Eles se registram no Eureka; por isso, o Gateway e as chamadas internas usam o nome do serviço, e não um IP fixo. Se fosse necessário rodar mais de uma instância, o Spring Cloud LoadBalancer poderia distribuir as chamadas entre elas. A explicação completa está em [docs/PROPOSTA.md](docs/PROPOSTA.md).

## Tecnologias

Java 21, Spring Boot, Spring Cloud (Eureka, Gateway e LoadBalancer), Resilience4j, Maven, PostgreSQL, MongoDB, Docker Compose e Make.

## Como executar

Pré-requisitos: Docker com Docker Compose e Make. Para rodar/compilar fora de containers, use Java 21 e Maven 3.9+.

```bash
make up
make status
```

Na primeira execução, a compilação das imagens pode levar alguns minutos. Para encerrar:

```bash
make down
```

Outros comandos disponíveis:

```bash
make build     # compila os quatro módulos
make test      # executa testes Maven
make logs      # acompanha logs dos containers
make package   # gera guilherme_china_DR3_TP1.zip
```

## Discovery Server

Abra `http://localhost:8761`. Após a inicialização, a página do Eureka deve mostrar `API-GATEWAY`, `RESERVATION-SERVICE` e `NOTIFICATION-SERVICE` registrados.

## API Gateway

O gateway é a única porta necessária para uso externo:

| Rota externa | Destino |
|---|---|
| `/api/reservations/**` | `reservation-service` |
| `/api/notifications/**` | `notification-service` |

## Exemplos de requisições

Crie uma reserva através do Gateway. A operação salva no PostgreSQL e solicita a gravação da confirmação no `notification-service`.

```bash
curl -i -X POST http://localhost:8080/api/reservations \
  -H 'Content-Type: application/json' \
  -d '{"roomName":"Sala Atlântico","requesterEmail":"guilherme@example.com","startsAt":"2026-09-01T10:00:00","endsAt":"2026-09-01T11:00:00"}'
```

```bash
curl http://localhost:8080/api/reservations
curl http://localhost:8080/api/notifications
```

## Resiliência entre microserviços

Depois de confirmar uma reserva, o `reservation-service` pede ao `notification-service` que registre uma notificação. Coloquei timeout de 2 segundos para conexão, 3 segundos para leitura e Circuit Breaker com Resilience4j. Assim, se o serviço de notificações estiver fora do ar, a reserva ainda é criada e o fallback registra a falha em vez de derrubar a operação inteira.

Para simular:

```bash
docker compose stop notification-service
# execute novamente o POST de reserva acima: ele ainda retorna 201 Created
docker compose start notification-service
```

## Documento da proposta

A documentação complementar está em [docs/PROPOSTA.md](docs/PROPOSTA.md), com problema, usuários, responsabilidades, bancos, diagrama textual e justificativa de MongoDB. Os prints e exemplos de uso estão em [docs/EVIDENCIAS.md](docs/EVIDENCIAS.md).
