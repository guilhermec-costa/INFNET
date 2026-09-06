# Trabalho Prático — Do Docker ao Kubernetes

**Aluno:** Guilherme China  
**Projeto:** Eventos Microservices  
**Tecnologias:** Java 21, Spring Boot 3.5, Docker, Docker Compose e Kubernetes

## 1. Planejamento da aplicação

O projeto **Eventos Microservices** é uma aplicação simplificada para consulta de eventos e criação de reservas. O objetivo é separar responsabilidades que podem evoluir e ser implantadas de forma independente.

| Microsserviço | Responsabilidade | Endpoints |
|---|---|---|
| `product-service` | Mantém o catálogo de eventos disponíveis (tratados como produtos). Os dados ficam em memória para manter o escopo do trabalho. | `GET /products`, `GET /products/{id}`, `POST /products` |
| `order-service` | Cria e consulta reservas (pedidos). Antes de gravar uma reserva, consulta o catálogo para confirmar a existência do evento. | `GET /orders`, `GET /orders/{id}`, `POST /orders` |

O `order-service` não acessa dados internos do `product-service`: a integração é feita exclusivamente por HTTP. Assim, cada serviço tem processo, porta, imagem e ciclo de deploy próprios.

## 2. Máquina virtual × container

| Característica | Máquina Virtual | Container |
|---|---|---|
| Sistema operacional | Cada VM possui um sistema operacional convidado completo sobre um hypervisor. | Compartilha o kernel do sistema operacional hospedeiro; leva somente aplicação e dependências. |
| Consumo de recursos | Maior consumo de CPU, memória e disco, pois há um SO por VM. | Menor consumo; as imagens Java carregam apenas o JRE e o JAR necessário. |
| Inicialização | Geralmente demora de dezenas de segundos a minutos para iniciar o SO. | Normalmente leva segundos, pois inicia apenas o processo da aplicação. |
| Isolamento | Forte isolamento por virtualização de hardware. | Isolamento por processos, namespaces e cgroups do sistema operacional. |

Para este projeto, uma alternativa com VMs seria criar uma VM para cada serviço, instalar Java e copiar cada JAR. Com containers, o `product-service` e o `order-service` são empacotados nas suas imagens e executados na mesma rede Docker.

Containers tendem a consumir menos recursos. Eles favorecem microsserviços porque isolam dependências, mantêm o mesmo artefato em desenvolvimento e produção (portabilidade), permitem aumentar somente o serviço necessário (escalabilidade) e possibilitam deploy independente. Por exemplo, uma mudança em reservas gera uma nova imagem do `order-service` sem substituir o catálogo.

## 3 e 4. Microsserviços Spring Boot

Os dois projetos são módulos Maven independentes. Para gerar e executar localmente sem Docker:

```bash
mvn clean package
java -jar product-service/target/product-service-1.0.0.jar
# Em outro terminal:
java -jar order-service/target/order-service-1.0.0.jar
```

O serviço de catálogo fica em `http://localhost:8081` e o de reservas em `http://localhost:8082`. Exemplos:

```bash
curl http://localhost:8081/products
curl http://localhost:8081/products/1
curl -X POST http://localhost:8081/products -H 'Content-Type: application/json' -d '{"name":"Peça de teatro","description":"Sessão noturna","price":70.00}'
curl -X POST http://localhost:8082/orders -H 'Content-Type: application/json' -d '{"productId":1,"quantity":2}'
curl http://localhost:8082/orders/1
```

O último `POST` só é aceito se o produto existir. Isso prova a regra de integração sem tornar os serviços um único processo.

## 5 e 6. Docker e seus componentes

Há um `Dockerfile` em cada microsserviço. Ambos usam build em dois estágios: Maven/Java 21 para gerar o JAR e JRE 21 Alpine para executá-lo.

| Componente | Função no projeto |
|---|---|
| Dockerfile | Receita declarativa da imagem. `product-service/Dockerfile`, por exemplo, copia o JAR e define o comando Java. |
| Image | Artefato imutável criado da receita: `eventos/product-service:1.0.0` e `eventos/order-service:1.0.0`. |
| Container | Instância em execução de uma imagem, como os containers `product-service` e `order-service`. |
| Docker Engine | Serviço que constrói as imagens, cria redes e executa os containers no computador. |
| Port Mapping | Publica uma porta do container para a máquina. Ex.: `-p 8081:8081` permite acessar o catálogo pelo navegador/local. |
| Network | Rede virtual para containers se encontrarem por nome, sem expor a comunicação interna ao host. |

Uma **imagem** é o modelo somente para leitura, com aplicação e dependências. Um **container** é uma execução concreta desse modelo, com estado de execução próprio. Podem existir vários containers baseados na mesma imagem.

### Construção e execução manual

```bash
docker network create eventos-network
docker build -t eventos/product-service:1.0.0 ./product-service
docker build -t eventos/order-service:1.0.0 ./order-service
docker run -d --name product-service --network eventos-network -p 8081:8081 eventos/product-service:1.0.0
docker run -d --name order-service --network eventos-network -e PRODUCT_SERVICE_URL=http://product-service:8081 -p 8082:8082 eventos/order-service:1.0.0
```

O detalhe importante é `http://product-service:8081`: dentro da rede Docker, o DNS interno resolve o nome do container. `localhost` apontaria para o próprio container `order-service`, portanto não seria a solução correta.

Para verificar a rede e a comunicação:

```bash
docker network inspect eventos-network
curl -X POST http://localhost:8082/orders -H 'Content-Type: application/json' -d '{"productId":1,"quantity":2}'
docker logs order-service
```

Um `201 Created` do último `POST` evidencia que o serviço de reservas consultou o produto antes de criar o pedido.

### Evidência obtida com Docker Compose

Na validação do projeto, `docker compose ps` apresentou os dois serviços em execução: `product-service` publicado em `8081` e `order-service` em `8082`. As chamadas realizadas retornaram:

```json
GET /products
[{"id":1,"name":"Show de Rock","description":"Ingresso para pista","price":120.00}, ...]

POST /orders  {"productId":1,"quantity":2}
{"id":1,"productId":1,"quantity":2,"status":"CRIADO"}
```

Como o pedido foi aceito usando o identificador do produto disponível apenas no outro serviço, o resultado confirma a comunicação entre os containers pela rede `eventos-network`.

## 7, 8 e 9. Comunicação e Docker Compose

Depois de testar os containers criados manualmente, eles podem ser removidos e o ambiente inteiro é iniciado pelo Compose:

```bash
docker rm -f product-service order-service
docker compose up --build -d
docker compose ps
curl http://localhost:8081/products
curl -X POST http://localhost:8082/orders -H 'Content-Type: application/json' -d '{"productId":2,"quantity":1}'
docker compose down
```

O arquivo [`docker-compose.yml`](docker-compose.yml) configura os dois serviços, as portas `8081` e `8082`, a rede bridge `eventos-network` e a variável `PRODUCT_SERVICE_URL`. O Compose usa o nome `product-service` como DNS na rede compartilhada.

## 10. Equivalência Docker → Kubernetes

| Docker | Kubernetes | Aplicação neste projeto |
|---|---|---|
| Container | Pod | Cada réplica executa um Pod contendo o container Spring Boot. |
| Network | Rede do cluster + Service | O Service oferece DNS estável e acesso aos Pods, que podem mudar de IP. |
| Port Mapping | Service (ClusterIP, NodePort ou port-forward) | Services expõem as portas internas 8081 e 8082; `port-forward` é usado para acesso local. |
| Serviço da aplicação | Service | `product-service` e `order-service` são nomes DNS usados entre aplicações. |
| Múltiplos containers/Compose | Deployment | Deployments declaram imagem, réplicas, portas e ambiente desejados. |

Não é necessário reescrever o código porque Docker e Kubernetes executam a mesma imagem OCI e o mesmo JAR. A adaptação é de infraestrutura: em vez do nome de um container Docker, o `order-service` recebe o nome DNS do Service Kubernetes (`http://product-service:8081`) por variável de ambiente.

## 11, 12 e 13. Kubernetes, descoberta e balanceamento

Os manifests estão em [`kubernetes/`](kubernetes). Eles criam Deployment e Service para ambos os microsserviços. O `product-service` já é configurado com três réplicas para demonstrar o balanceamento.

Antes de aplicar em um cluster local (Minikube, Kind ou Docker Desktop), as imagens precisam estar acessíveis pelo cluster. Em um cluster que não compartilha as imagens locais, publique-as em um registry e altere o campo `image` dos manifests.

```bash
kubectl apply -f kubernetes/product-service.yaml
kubectl apply -f kubernetes/order-service.yaml
kubectl get deployments,pods,services
kubectl get pods -l app=product-service
kubectl port-forward service/order-service 8082:8082
# Em outro terminal:
curl -X POST http://localhost:8082/orders -H 'Content-Type: application/json' -d '{"productId":1,"quantity":1}'
```

O `order-service` usa `http://product-service:8081`, que o DNS do Kubernetes resolve para o Service, e não para IP fixo. O Service seleciona todos os Pods que tenham o rótulo `app: product-service` e encaminha as requisições a endpoints prontos.

Para observar as três réplicas e os endpoints balanceados:

```bash
kubectl get pods -l app=product-service -o wide
kubectl get endpoints product-service
kubectl describe service product-service
```

O **Service** é o ponto de acesso estável e de descoberta para um conjunto de Pods. Vários Pods do mesmo microsserviço permitem atender mais requisições e aumentam a disponibilidade. Se um Pod falhar, ele deixa de estar pronto e deixa de receber tráfego; o Deployment tenta recriá-lo e o Service continua enviando requisições aos Pods saudáveis. Assim, o Service distribui as chamadas entre os endpoints disponíveis, sem que o consumidor precise conhecer os Pods individuais.

## 14. Avaliação e reflexão

A parte mais fácil foi construir os endpoints REST em memória, porque eles possuem responsabilidades pequenas e independentes. A parte que exigiu mais atenção foi a comunicação entre serviços: o endereço correto muda conforme a execução local, Docker e Kubernetes; por isso a URL foi externalizada na variável `PRODUCT_SERVICE_URL`.

O principal aprendizado foi que container não é máquina virtual: ele empacota a aplicação de forma leve e reproduzível. Docker simplifica a execução dos serviços, enquanto Kubernetes mantém o estado desejado, oferece descoberta de serviço e distribui tráfego entre réplicas.

Minha autoavaliação é **9/10**. Os requisitos de endpoints, containerização, comunicação por DNS, Compose, Deployments, Services e réplicas foram contemplados. A redução de um ponto reflete que o exemplo utiliza armazenamento em memória, escolha adequada ao escopo didático, mas sem persistência de dados para produção.
