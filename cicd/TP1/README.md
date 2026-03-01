# DevCalc API

[![CI Pipeline](https://github.com/GuiC0506/infnet/actions/workflows/ci.yml/badge.svg)](https://github.com/GuiC0506/infnet/actions/workflows/ci.yml)

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
- **lint-and-test.yml**: Workflow reutilizável para lint e testes

## Execução Manual

O pipeline `ci.yml` pode ser executado manualmente a partir da interface do GitHub, na aba Actions:
1. Acesse o workflow "CI Pipeline"
2. Clique em "Run workflow"
3. Selecione os parâmetros:
   - `run_tests`: Executar testes (default: true)
   - `run_lint`: Executar lint/checkstyle (default: true)
4. Clique em "Run workflow"

## Debug de Pipeline

### Identificação de Erros

Ao ocorrer uma falha no pipeline, utilize a aba **Actions** do GitHub para diagnosticar:

1. **Visualização dos Jobs**: Clique no workflow executado para ver todos os jobs
2. **Logs de Execução**: Clique em cada job para expandir os logs detalhados
3. **Filtragem por Status**: Use os filtros para encontrar executions failed
4. **Step a Step**: Cada step mostra o output completo no console

### Exemplo de Correção

Um erro foi propositalmente introduzido alterando o comando de build de `mvn clean install` para `mvn comando-invalido`. 

**Identificação**: O job `build` falhou com erro: `Unknown lifecycle phase comando-invalido`

**Correção**: O comando foi restaurado para `mvn clean install`

**Ferramentas utilizadas**:
- Aba Actions do GitHub para visualização dos logs
- Filtro de jobs com falha
- Console de output de cada step

## Observações sobre Modos de Execução

### Push Automático
- Disparado automaticamente ao fazer push para a branch main
- Executa todos os jobs definidos no workflow
- Parâmetros `run_tests` e `run_lint` usam valores padrão (true)
- Ideal para validação contínua do código

### Execução Manual (workflow_dispatch)
- Disparado manualmente pelo usuário na interface do GitHub
- Permite configurar parâmetros antes da execução
- Possibilita rodar apenas lint ou apenas testes
- Útil para debug ou verificações específicas

A principal diferença é que a execução manual oferece flexibilidade para escolher o que executar, enquanto o push automático garante que todas as verificações sejam feitas a cada alteração.
