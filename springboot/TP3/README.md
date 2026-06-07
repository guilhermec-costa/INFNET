## Como subir a aplicação

### Pré-requisitos

- Java 17+
- Maven 3.9+
- Docker e Docker Compose

### Executando com Docker Compose

```bash
docker compose up --build
```

A aplicação sobe por padrão em:

```text
http://localhost:8080
```

O MongoDB sobe em:

```text
mongodb://localhost:27017/academia
```

### Smoke test

Depois que a aplicação subir, execute:

```bash
bash scripts/smoke-test.sh
```

O script valida:

- `GET /actuator/health`
- `GET /api/alunos/ativos`
- `GET /api/alunos/ranking`
- `POST /api/alunos`
- `POST /api/treinos`
- `POST /api/avaliacoes-fisicas`
- `GET /api/avaliacoes-fisicas/aluno/1`

Para validar apenas endpoints `GET`, execute:

```bash
bash scripts/smoke-test-gets.sh
```

Esse script valida:

- `GET /actuator/health`
- `GET /api/alunos/ativos`
- `GET /api/alunos/ranking`
- `GET /api/avaliacoes-fisicas/aluno/1`

### Executando localmente sem Docker

Se preferir rodar localmente:

```bash
mvn spring-boot:run
```

### Recursos disponíveis

- H2 Console: `http://localhost:8080/h2-console`
- Actuator Health: `http://localhost:8080/actuator/health`

## Endpoints disponíveis

### Alunos

- `POST /api/alunos`
- `GET /api/alunos/ativos`
- `GET /api/alunos/ranking`

Exemplo de body para cadastro:

```json
{
  "nome": "Maria Souza",
  "email": "maria@academia.com",
  "dataNascimento": "1999-05-10",
  "ativo": true,
  "planoId": 1
}
```

### Treinos

- `POST /api/treinos`

Exemplo de body para cadastro:

```json
{
  "nomeTreino": "Treino C",
  "focoPrincipal": "Resistência",
  "instrutorId": 1
}
```

### Avaliações Físicas

- `POST /api/avaliacoes-fisicas`
- `GET /api/avaliacoes-fisicas/aluno/{alunoId}`

Exemplo de body para cadastro:

```json
{
  "alunoId": 1,
  "peso": 70.5,
  "altura": 1.75,
  "percentualGordura": 18.2,
  "anotacoesMedicas": "Sem restrições"
}
```

### Token de acesso

- `POST /api/acessos/tokens?alunoId={alunoId}`
- `GET /api/acessos/tokens/{token}/validar`
