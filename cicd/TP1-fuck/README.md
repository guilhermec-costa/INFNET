# Parte 1 — Avaliação Teórica

## 1) O que realmente nasce quando executamos docker build?

Quando executamos `docker build`, criamos uma **imagem Docker**, não um container.

### Por que o build não cria um container?
O `docker build` apenas processa o Dockerfile e cria uma imagem (template imutável). A imagem é armazenada no registro local ou remoto. Um container só é criado quando executamos `docker run` com base nessa imagem.

### O que são camadas (layers)?
Uma imagem Docker é composta por camadas de leitura (read-only). Cada instrução no Dockerfile (FROM, RUN, COPY, etc.) cria uma nova camada. As camadas são armazenadas em cache e compartilhadas entre imagens, o que torna o build rápido e as imagens eficientes em termos de armazenamento.

### O que significa "congelar decisões técnicas"?
Cada camada é imutável após ser criada. Isso significa que todas as decisões técnicas (sistema operacional, versões de dependências, configurações) ficam "congeladas" na imagem. Isso garante consistência entre ambientes, mas também significa que atualizar algo requer rebuild da imagem.

---

## 2) Diferença entre Imagem, Container, Volume e Rede Docker

| Conceito | Descrição |
|----------|-----------|
| **Imagem** | Template imutável, apenas leitura, usado para criar containers. Contém sistema de arquivos e dependências. |
| **Container** | Instância executável de uma imagem. É mutável (pode escrever/modificar arquivos durante execução). Tem seu próprio sistema de arquivos isolado. |
| **Volume** | Mecanismo para persistir dados fora do container. Sobrevivem à destruição do container. Usado para bancos de dados e dados importantes. |
| **Rede Docker** | Permite comunicação entre containers. Isolamento de rede entre grupos de containers. |

### Relacionamento no ciclo de vida:
1. Imagem é criada via `docker build`
2. Container é instanciado via `docker run` a partir de uma imagem
3. Volumes persistem dados independentemente dos containers
4. Redes permitem que containers comuniquem entre si

---

## 3) Por que scripts em /docker-entrypoint-initdb.d/ rodam apenas na primeira execução?

O PostgreSQL oficial usa o conceito de **volume de dados** interno. O diretório `/var/lib/postgresql/data` é onde o banco armazena seus dados.

### O papel do diretório de dados e volumes:
- Na primeira execução, se o diretório de dados (`/var/lib/postgresql/data`) estiver vazio, o PostgreSQL inicializa um novo banco
- Scripts em `/docker-entrypoint-initdb.d/` são executados **apenas durante esta inicialização** (quando os dados estão vazios)
- O container verifica se o diretório já possui dados. Se tiver, **pula** a inicialização
- O volume Docker (se montado em `/var/lib/postgresql/data`) persiste os dados entre execuções
- Por isso, ao usar um volume nomeado ou bind mount, os scripts só rodam uma vez

---

## 4) Por que usar localhost falha na configuração da API?

### Conceito: Isolamento de rede do Docker

Cada container Docker possui sua própria **namespace de rede**. Quando a API tenta se conectar ao banco usando `localhost` (ou `127.0.0.1`), ela está tentando se conectar a si mesma, não ao container do banco de dados.

### Solução:
- Usar o **nome do serviço/container** como hostname (ex: `db` ou `postgres`)
- Ambos os containers devem estar na **mesma rede Docker**
- O Docker DNS resolve o nome do serviço para o IP do container correspondente

Exemplo correto: `jdbc:postgresql://db:5432/postgres`
Exemplo incorreto: `jdbc:postgresql://localhost:5432/postgres`

---

# Parte 2 — Avaliação Prática

## QUESTÃO 1 — Banco Containerizado

### Dockerfile do Banco (postgres.Dockerfile)
```dockerfile
FROM postgres:17.8-alpine3.23

ENV POSTGRES_DB=postgres
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=admin

COPY dados/data.sql /docker-entrypoint-initdb.d/

VOLUME /var/lib/postgresql/data
```

### Comandos de Build
```bash
# Build da imagem do banco
docker build -t ricknmorty-db -f postgres.Dockerfile .
```

### Comandos de Execução
```bash
# Executar container em modo detachado (sem expor porta)
docker run -d --name ricknmorty-db -v postgres_data:/var/lib/postgresql/data ricknmorty-db
```

### Comandos de Validação
```bash
# Ver logs do container (mostra init na primeira execução)
docker logs ricknmorty-db

# Listar tabelas (executar dentro do container)
docker exec -it ricknmorty-db psql -U postgres -c "\dt"

# Executar SELECT
docker exec -it ricknmorty-db psql -U postgres -c "SELECT name, status FROM characters LIMIT 5;"
```

---

## QUESTÃO 2 — API + Banco na mesma rede Docker

### Dockerfile da API (api.Dockerfile)
```dockerfile
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app
COPY app/pom.xml .
COPY app/src ./src
RUN apk add --no-cache maven && \
    mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### docker-compose.yml
```yaml
version: '3.8'

services:
  db:
    build:
      context: .
      dockerfile: postgres.Dockerfile
    container_name: ricknmorty-db
    environment:
      POSTGRES_DB: postgres
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: admin
    volumes:
      - postgres_data:/var/lib/postgresql/data
    networks:
      - app-network
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U postgres"]
      interval: 5s
      timeout: 5s
      retries: 5

  api:
    build:
      context: .
      dockerfile: api.Dockerfile
    container_name: ricknmorty-api
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://db:5432/postgres
      SPRING_DATASOURCE_USERNAME: postgres
      SPRING_DATASOURCE_PASSWORD: admin
    ports:
      - "8080:8080"
    networks:
      - app-network
    depends_on:
      db:
        condition: service_healthy

networks:
  app-network:
    driver: bridge

volumes:
  postgres_data:
```

### Comando de Execução
```bash
# Build e execução com docker-compose
docker-compose up --build -d
```

### Evidência de Funcionamento
```bash
# Ver logs dos containers
docker-compose logs -f

# Testar API
curl http://localhost:8080/api/characters
```
