# Entrega 1 — Plataforma de Reservas de Salas

## Identificação

| Item | Informação |
|---|---|
| Aluno | Guilherme de Morais China Costa |
| Turma | GRLEDS01C2-N2-LC |
| Modalidade | Individual |
| Organização, documentação, infraestrutura e serviços | Guilherme de Morais China Costa |
| Repositório | https://github.com/guilhermec-costa/INFNET |

## Problema e proposta

Eu escolhi o tema de reservas de salas porque é um problema fácil de entender e permite separar duas responsabilidades que não precisam ficar no mesmo serviço. Em vez de controlar reservas por mensagens soltas, a aplicação cria a reserva, permite consultá-la depois e guarda o histórico da confirmação enviada ao solicitante. Os usuários principais seriam colaboradores que reservam salas e administradores que precisam acompanhar os registros.

As reservas têm regras próprias e precisam de dados consistentes, como sala, horário e solicitante. Já as notificações podem mudar de formato conforme o canal e podem crescer em volume sem que isso precise afetar o cadastro de reservas. Por esse motivo, faz sentido mantê-las em serviços diferentes, sem criar serviços demais para um domínio ainda pequeno.

## Arquitetura

```text
Cliente
  | HTTP :8080
API Gateway --descoberta--> Eureka Discovery Server :8761
  | /api/reservations/**                  |
  +--> reservation-service :8081 <--------+
  |       PostgreSQL / reservation_db
  |              |
  |       Circuit Breaker + timeout
  |              v
  +--> notification-service :8082
          MongoDB / notification_db
```

## Serviços e dados

| Componente | Responsabilidade e endpoints | Persistência | Por que é separado |
|---|---|---|---|
| `reservation-service` | Cria e lista reservas. `POST`/`GET /api/reservations` | PostgreSQL, database lógico `reservation_db` | Mantém os dados da reserva consistentes e separados das mensagens. |
| `notification-service` | Registra notificações. `POST`/`GET /api/notifications` | MongoDB, database `notification_db` | Guarda mensagens em formato de documento, que pode variar por canal. |
| `discovery-server` | Registro e descoberta dinâmica dos serviços | Não se aplica | Evita manter endereço e porta fixos nas chamadas entre serviços. |
| `api-gateway` | Entrada única e roteamento externo | Não se aplica | Evita que quem usa a aplicação precise conhecer as portas internas. |

Cada microserviço é dono de seus dados e usa banco lógico próprio; não há tabelas/coleções compartilhadas.

## Banco não relacional

Escolhi MongoDB como banco principal do `notification-service`. Uma notificação de e-mail pode ter assunto e corpo; uma notificação push pode ter outros campos. MongoDB aceita melhor essa variação sem exigir que eu altere uma tabela a cada novo tipo de mensagem. Ele também deixa o histórico de notificações independente do banco transacional de reservas. Redis poderia ser usado como cache no futuro, mas não é a justificativa do banco não relacional deste projeto.

## Discovery, Gateway e resiliência

Usei Eureka como Discovery Server. Quando os serviços sobem, eles aparecem em `http://localhost:8761`. O Gateway consulta o Eureka e encaminha as rotas `/api/reservations/**` e `/api/notifications/**` usando os nomes dos serviços, sem depender de IPs definidos manualmente.

Ao criar uma reserva, o `reservation-service` chama o `notification-service` para gravar uma confirmação. Configurei timeout de 2 segundos para conexão, 3 segundos para leitura e Circuit Breaker do Resilience4j. Se a notificação estiver lenta ou indisponível, o fallback é chamado e a reserva continua confirmada. Para testar, basta parar somente o `notification-service` com `docker compose stop notification-service` e criar uma reserva pelo Gateway: a resposta deve continuar sendo `201`.

## Escalabilidade e distribuição em nuvem

Eu deixei cada serviço como uma aplicação independente, com servidor embutido, porta própria e variáveis de ambiente para as configurações. Dessa forma, se o projeto precisasse crescer, seria possível subir uma nova instância de `reservation-service` ou `notification-service` sem mexer no código dos outros.

Quando uma instância sobe, ela se registra no Eureka. O Gateway e o `reservation-service` chamam os outros serviços pelo nome lógico, não por IP. Isso é importante porque, em um ambiente de nuvem, eu poderia ter mais de uma instância do serviço de notificações e deixar o Spring Cloud LoadBalancer escolher uma delas. Quem consome a API continuaria usando as mesmas rotas do Gateway (`/api/reservations/**` e `/api/notifications/**`), mesmo com mudanças internas.

Os bancos também ficam fora dos processos Java e são declarados no Docker Compose. PostgreSQL fica responsável pelo estado transacional das reservas; MongoDB, pelo histórico de mensagens. Como cada serviço é dono da sua base lógica, um não vira dependência direta do banco do outro. Além disso, o timeout e o Circuit Breaker evitam que uma indisponibilidade temporária nas notificações impeça a criação de reservas.

No meu ambiente local, o Docker Compose representa essa distribuição com containers em uma rede interna. Em uma próxima etapa, essa mesma separação poderia ser levada para uma plataforma como Kubernetes, sem mudar a divisão dos serviços.

## Evidências para a apresentação

As evidências de execução, incluindo os prints do Eureka com `API-GATEWAY`, `RESERVATION-SERVICE` e `NOTIFICATION-SERVICE` registrados, estão em [EVIDENCIAS.md](EVIDENCIAS.md). Elas demonstram os serviços integrados, o Discovery Server e as rotas externas.
