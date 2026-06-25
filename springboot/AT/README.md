## Executar

```bash
make start
```

Para subir sem PostgreSQL:

```bash
make start-dev
```

## Comandos úteis

```bash
make postgres
make test
make package
```

## Credenciais padrão

- API: `admin` / `admin123`
- Actuator: `actuator` / `actuator123`

## Endpoints

- `POST /pacientes`
- `GET /pacientes`
- `GET /pacientes/{id}`
- `PUT /pacientes/{id}`
- `DELETE /pacientes/{id}`
- `POST /medicos`
- `GET /medicos`
- `PUT /medicos/{id}`
- `GET /medicos/ranking-consultas`
- `POST /consultas`
- `POST /internacoes`
- `GET /internacoes/paciente/{pacienteId}`
- `GET /actuator/health`
