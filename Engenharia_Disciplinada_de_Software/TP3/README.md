# CRUD System - Manual de Execução

## Visão Geral
Sistema CRUD completo com backend Java (Javalin) e frontend React, incluindo testes com cenários de falha e fuzz testing.

## Estrutura do Projeto
```
TP3/
├── backend/                 # Backend Java com Javalin
│   ├── src/main/java/       # Código fonte
│   │   └── com/crud/
│   │       ├── config/      # Configuração da aplicação
│   │       ├── controller/  # Controladores REST
│   │       ├── exception/  # Exceções customizadas
│   │       ├── model/       # Modelos de dados
│   │       ├── repository/  # Repositório de dados
│   │       └── service/    # Lógica de negócio
│   └── src/test/           # Testes
│       └── com/crud/
│           ├── unit/        # Testes unitários
│           ├── integration/ # Testes de integração
│           ├── fuzz/        # Testes de fuzz
│           └── selenium/    # Testes Selenium
└── frontend/               # Frontend React
    ├── public/
    └── src/
        ├── components/     # Componentes React
        ├── App.js          # Componente principal
        └── App.css         # Estilos
```

## Executando o Backend

### Compilação
```bash
cd backend
mvn clean compile
```

### Execução
```bash
cd backend
mvn exec:java -Dexec.mainClass="com.crud.Main"
```

O servidor será iniciado em: http://localhost:8080

### Endpoints da API
- `GET /api/items` - Listar todos os itens
- `GET /api/items/{id}` - Obter item por ID
- `POST /api/items` - Criar novo item
- `PUT /api/items/{id}` - Atualizar item
- `DELETE /api/items/{id}` - Excluir item
- `GET /api/health` - Verificação de saúde

## Executando o Frontend

### Instalação
```bash
cd frontend
npm install
```

### Execução
```bash
cd frontend
npm start
```

O frontend será iniciado em: http://localhost:3000

## Executando os Testes

### Todos os Testes
```bash
cd backend
mvn test
```

### Relatório de Cobertura
Após executar os testes, abra:
```
backend/target/site/jacoco/index.html
```