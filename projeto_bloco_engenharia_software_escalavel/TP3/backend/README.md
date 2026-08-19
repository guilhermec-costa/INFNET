# Backend

API monolítica de biblioteca construída com Spring Boot, com camada de persistência baseada em JPA, Spring Data e auditoria de histórico.

## Funcionalidades

- CRUD de livros
- CRUD de leitores
- Registro de empréstimo
- Registro de devolução
- Listagem de livros disponíveis
- Consulta de histórico de alterações de livros, leitores e empréstimos

## Estrutura

- `controller`: endpoints REST
- `service`: regras de negócio e validações de integridade
- `repository`: persistência com Spring Data JPA e consulta de revisões
- `model`: entidades do domínio e metadados de auditoria
- `dto`: contratos de entrada, saída e histórico
- `config`: configuração JPA/auditoria e carga inicial

## Camada de Persistência

### Modelagem de dados

O domínio foi modelado em três agregados principais:

- `Livro`: título, autor, ISBN único, status do exemplar e metadados de auditoria.
- `Leitor`: nome, email único e metadados de auditoria.
- `Empréstimo`: relacionamento entre livro e leitor, datas do ciclo de empréstimo, flag `ativo` e metadados de auditoria.

Decisões de modelagem adotadas para atender consultas e isolamento de domínio:

- `ISBN` e `email` possuem restrições únicas para proteger a integridade.
- `Emprestimo` referencia `Livro` e `Leitor` via `@ManyToOne`, preservando o domínio e evitando duplicação de dados.
- Foram criados índices para status de livros, email/nome de leitores e consultas de empréstimos ativos por livro, leitor e data prevista.
- Todas as entidades herdam de `AuditableEntity`, que registra `createdAt`, `updatedAt` e `version` para auditoria e controle de concorrência otimista.

### Persistência real

A aplicação utiliza H2 em arquivo:

```properties
spring.datasource.url=jdbc:h2:file:./data/biblioteca;MODE=LEGACY;AUTO_SERVER=TRUE
```

Isso permite manter os dados entre reinicializações da aplicação, diferente do banco em memória usado antes.

### Auditoria e histórico de dados

O histórico foi implementado com Hibernate Envers:

- `@Audited` nas entidades `Livro`, `Leitor` e `Emprestimo`
- tabelas `_aud` geradas automaticamente para revisões
- consulta de histórico via `RevisionRepository`

Endpoints disponiveis:

- `GET /api/livros/{id}/historico`
- `GET /api/leitores/{id}/historico`
- `GET /api/emprestimos/{id}/historico`

Cada resposta retorna:

- número da revisão
- tipo da operação (`INSERT`, `UPDATE`, `DELETE`)
- data/hora da revisão
- snapshot dos dados naquela revisão

## Repositórios Spring Data

Os repositórios abstraem o acesso a dados e concentram consultas orientadas ao domínio.

### `LivroRepository`

Consultas principais:

- `findByStatusOrderByTituloAsc(StatusLivro status)`
- `findByTituloContainingIgnoreCaseOrAutorContainingIgnoreCaseOrderByTituloAsc(...)`
- `existsByIsbnIgnoreCase(String isbn)`
- `existsByIsbnIgnoreCaseAndIdNot(String isbn, Long id)`
- `findRevisions(Long id)`

Exemplo de uso:

```java
List<Livro> disponíveis = livroRepository.findByStatusOrderByTituloAsc(StatusLivro.DISPONIVEL);
boolean isbnDuplicado = livroRepository.existsByIsbnIgnoreCase("9780132350884");
```

### `LeitorRepository`

Consultas principais:

- `existsByEmailIgnoreCase(String email)`
- `existsByEmailIgnoreCaseAndIdNot(String email, Long id)`
- `findRevisions(Long id)`

Exemplo de uso:

```java
boolean emailEmUso = leitorRepository.existsByEmailIgnoreCase("ana@biblioteca.local");
```

### `EmprestimoRepository`

Consultas principais:

- `findByAtivoTrueOrderByDataPrevistaDevolucaoAsc()`
- `existsByLeitorIdAndAtivoTrue(Long leitorId)`
- `existsByLivroIdAndAtivoTrue(Long livroId)`
- `findRevisions(Long id)`

Exemplo de uso:

```java
List<Emprestimo> ativos = emprestimoRepository.findByAtivoTrueOrderByDataPrevistaDevolucaoAsc();
boolean livroEmprestado = emprestimoRepository.existsByLivroIdAndAtivoTrue(livroId);
```

## Regras de integridade implementadas

- não permite cadastrar dois livros com o mesmo ISBN
- não permite cadastrar dois leitores com o mesmo email
- não permite excluir livro com empréstimo em aberto
- não permite excluir leitor com empréstimo ativo
- não permite registrar novo empréstimo para livro indisponível
- usa `@Version` para concorrência otimista

## Executando

```bash
mvn spring-boot:run
```

A API sobe em `http://localhost:8080`.

## Endpoints principais

- `GET /api/livros`
- `GET /api/livros/disponiveis`
- `GET /api/livros/{id}/historico`
- `POST /api/livros`
- `PUT /api/livros/{id}`
- `DELETE /api/livros/{id}`
- `GET /api/leitores`
- `GET /api/leitores/{id}/historico`
- `POST /api/leitores`
- `PUT /api/leitores/{id}`
- `DELETE /api/leitores/{id}`
- `GET /api/emprestimos`
- `GET /api/emprestimos/ativos`
- `GET /api/emprestimos/{id}/historico`
- `POST /api/emprestimos`
- `POST /api/emprestimos/{id}/devolucao`

## Testes

Os testes automatizados da camada de persistência ficam em `src/test/java/com/example/biblioteca/repository`.

Cobertura atual:

- persistência das entidades com auditoria de criação/atualização
- consultas de domínio dos repositórios
- verificação de integridade por ISBN, email e empréstimos ativos
- carregamento eficiente de relacionamentos em empréstimos
- registro e consulta de histórico com Envers

Execução:

```bash
mvn test
```
