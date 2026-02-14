# DevCalc API

REST API com operações matemáticas simples, desenvolvida em Java com framework Javalin.

## Ferramenta de Build

Maven

## Endpoints

| Operacao | URL | Exemplo |
|----------|-----|---------|
| Soma | GET /add?a={x}&b={y} | /add?a=10&b=5 → 15 |
| Subtracao | GET /subtract?a={x}&b={y} | /subtract?a=10&b=5 → 5 |
| Multiplicacao | GET /multiply?a={x}&b={y} | /multiply?a=10&b=5 → 50 |
| Divisao | GET /divide?a={x}&b={y} | /divide?a=10&b=5 → 2.0 |

## Executar Localmente

```bash
mvn clean install
mvn exec:java -Dexec.mainClass="com.devcalc.App"
```

A API estara disponivel em http://localhost:7000

## Executar Testes

```bash
mvn test
```

## Workflows

- **hello.yml**: Pipeline basico que exibe mensagem ao detectar push na main
- **ci.yml**: Pipeline de CI/CD com jobs de build, test, package e deploy
