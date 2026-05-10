# Relatório do Trabalho - Serviço Web REST com Spring Boot

## 1. Objetivo do projeto

O objetivo deste trabalho foi desenvolver um serviço web REST sem estado para executar operações matemáticas básicas. A aplicação disponibiliza cinco endpoints distintos para adição, subtração, multiplicação, divisão e exponenciação, todos acessíveis pelos métodos `GET` e `POST`, com comportamento equivalente.

## 2. Escolha da ferramenta de construção

Foi escolhido o **Maven** como ferramenta de construção do projeto.

### Justificativa da escolha

O Maven foi escolhido pelos seguintes motivos:

- possui estrutura padronizada de projeto, facilitando a organização e a correção do trabalho
- é amplamente utilizado em projetos Spring Boot, com integração nativa muito madura
- simplifica o gerenciamento de dependências e plugins por meio do arquivo `pom.xml`
- favorece a manutenção e o entendimento do projeto por outros desenvolvedores e avaliadores.

## 3. Iniciação do projeto

### 3.1 Criação com Spring Initializr via interface web

O projeto pode ser iniciado pela interface web do Spring Initializr em:

`https://start.spring.io`

Configuração utilizada:

- Project: `Maven`
- Language: `Java`
- Spring Boot: `3.x`
- Group: `br.com.infnet`
- Artifact: `tp1-math-api`
- Name: `tp1-math-api`
- Packaging: `Jar`
- Java: `21`
- Dependencies:
  - `Spring Web`
  - `Validation`

Depois disso, basta gerar o projeto, descompactar o arquivo e abrir na IDE.

### 3.2 Criação com Spring Boot CLI

Uma forma alternativa de demonstrar a inicialização é via Spring Boot CLI:

```bash
spring init \
--build=maven \
--java-version=21 \
--dependencies=web,validation \
--groupId=br.com.infnet \
--artifactId=tp1-math-api \
tp1-math-api
```

### 3.3 Quando usar cada método

**Spring Initializr Web** é preferível quando:

- queremos uma criação visual e rápida
- estamos em ambiente acadêmico ou introdutório
- precisamos selecionar dependências de forma guiada.

**Spring Boot CLI** é preferível quando:

- queremos automatizar a criação do projeto
- precisamos repetir a criação em vários ambientes
- estamos trabalhando com terminal, scripts ou pipelines.

## 4. Gerenciamento de dependências

O gerenciamento de dependências foi realizado com o Maven usando o `spring-boot-starter-parent`, que centraliza versões compatíveis e reduz configurações manuais.

Dependências principais utilizadas:

- `spring-boot-starter-web`: fornece suporte à API REST, servidor embarcado e serialização JSON
- `spring-boot-starter-validation`: habilita a validação de entrada para as requisições POST
- `spring-boot-starter-test`: fornece infraestrutura de testes com JUnit e MockMvc.

### Abordagem adotada

- uso de starters oficiais do Spring Boot
- redução de versões manuais no `pom.xml`
- aproveitamento do gerenciamento de dependências do parent do Spring Boot
- isolamento de dependências de teste com `scope test`.

### Benefícios no ciclo de vida do projeto

- menor risco de incompatibilidade entre bibliotecas
- manutenção simplificada
- facilidade para evoluir a aplicação
- padronização do ambiente de desenvolvimento e avaliação.

## 5. Utilização de autoconfiguração

O recurso de autoconfiguração do Spring Boot foi utilizado por meio da anotação `@SpringBootApplication`, presente na classe principal da aplicação.

Com isso, o framework configurou automaticamente:

- o servidor web embarcado
- o mapeamento REST
- a serialização e desserialização JSON
- o contexto de injeção de dependência
- a configuração básica de validação.

Essa abordagem minimizou código repetitivo, eliminando a necessidade de configurações XML ou classes extensas de infraestrutura.

## 6. Desenvolvimento dos serviços REST

### 6.1 Organização do código

O projeto foi organizado nas seguintes camadas:

- `controller`: recebe as requisições HTTP e expõe os endpoints
- `service`: concentra as regras matemáticas
- `dto`: define estruturas de entrada, saída e erro
- `exception`: centraliza o tratamento de erros.

### 6.2 Rotas implementadas

Base da API:

`/api/math`

Rotas:

- `/add`
- `/subtract`
- `/multiply`
- `/divide`
- `/power`

Cada rota possui suporte a:

- `GET` com parâmetros de consulta `a` e `b`
- `POST` com corpo JSON contendo `a` e `b`.

### 6.3 Uso de `@RequestMapping`

O enunciado pedia explicitamente o uso de `@RequestMapping`. Por isso, as rotas foram implementadas com essa anotação, especificando `path` e `method` para os métodos `GET` e `POST`.

Exemplo conceitual:

```java
@RequestMapping(path = "/add", method = {RequestMethod.GET, RequestMethod.POST})
```

Na implementação final, cada método HTTP foi mapeado individualmente para manter clareza, facilitar os testes e deixar explícito o suporte aos dois métodos.

### 6.4 Comportamento stateless

O serviço é sem estado porque:

- não armazena sessão do usuário
- não depende de dados persistidos entre requisições
- processa cada chamada apenas com base nos parâmetros recebidos.

## 7. Tratamento de erros

Foi implementado um tratamento global de exceções com `@RestControllerAdvice`.

Casos tratados:

- tentativa de divisão por zero
- parâmetros obrigatórios ausentes
- corpo JSON inválido
- erros inesperados.

Isso melhora a robustez da API e padroniza as respostas de erro em JSON.

## 8. Exemplos de chamadas e respostas

### 8.1 Exemplo GET - adição

Requisição:

```bash
curl "http://localhost:8080/api/math/add?a=10&b=5"
```

Resposta:

```json
{
  "operation": "addition",
  "a": 10,
  "b": 5,
  "result": 15
}
```

### 8.2 Exemplo POST - divisão

Requisição:

```bash
curl -X POST "http://localhost:8080/api/math/divide" \
  -H "Content-Type: application/json" \
  -d '{"a":20,"b":4}'
```

Resposta:

```json
{
  "operation": "division",
  "a": 20,
  "b": 4,
  "result": 5
}
```

### 8.3 Exemplo de erro - divisão por zero

Requisição:

```bash
curl "http://localhost:8080/api/math/divide?a=10&b=0"
```

Resposta:

```json
{
  "timestamp": "2026-05-10T00:00:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Nao é permitido dividir por zero.",
  "path": "/api/math/divide"
}
```

## 9. Estrutura do repositório

Arquivos principais do projeto:

- `pom.xml`
- `src/main/java/...`
- `src/test/java/...`
- `README.md`
- `RELATORIO.md`

Essa organização facilita a leitura, a execução e a avaliação do trabalho.