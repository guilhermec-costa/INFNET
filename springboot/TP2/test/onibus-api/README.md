## Para subir a aplicação com Maven:

```bash
mvn spring-boot:run
```

Para subir a aplicação com o Maven Wrapper:

```bash
./mvnw spring-boot:run
```

A aplicacao será iniciada em:

```text
http://localhost:8080
```

## Para rodar todos os testes automatizados:

```bash
mvn test
```

Ou com o Maven Wrapper:

```bash
./mvnw test
```

## Endpoints principais

- `GET /passagens`
- `GET /passagens/{id}`
- `POST /passagens`
- `PUT /passagens/{id}`
- `DELETE /passagens/{id}`
- `GET /passagens/busca?destino={algumDestino}`
