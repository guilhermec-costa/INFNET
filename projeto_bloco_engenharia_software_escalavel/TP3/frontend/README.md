# Frontend

Interface React para o sistema de biblioteca.

## Funcionalidades

- Cadastro e listagem de livros
- Cadastro e listagem de leitores
- Registro de empréstimos
- Registro de devoluções
- Atualização visual dos livros disponíveis
- Consulta de histórico de livros, leitores e empréstimos
- Consulta de notificações de cada leitor

## Executando

```bash
npm install
npm run dev
```

O frontend sobe em `http://localhost:5173`.

## Integração

Na execução local, o Vite encaminha as chamadas para o backend em `http://localhost:8080`.

No Docker Compose do TP3, o frontend fica disponível em `http://localhost:4173` e acessa a API principal em `http://localhost:8180/api`.

Para abrir as notificações, acesse a área **Leitores** e use o botão **Ver notificações**. Os avisos exibidos são retornados pelo microsserviço de notificações por meio da API principal.
