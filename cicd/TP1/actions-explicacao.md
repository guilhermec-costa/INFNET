# GitHub Actions vs Workflows: Explicação

## Diferença entre Workflows e Actions

### Workflow
Um **workflow** é um arquivo YAML que define um conjunto de jobs automatizados no repositório. Ele é acionado por eventos (push, pull request, schedule, etc.) e pode conter múltiplos jobs que executam em paralelo ou sequencialmente.

- Localizado em `.github/workflows/*.yml`
- Definido por gatilhos (`on:`) e jobs
- Pode invocar outros workflows via `workflow_call`

### Action
Uma **action** é uma tarefa individual e reutilizável que pode ser usada dentro de um job. É o menor bloco de construção de um workflow.

- Executa um passo específico (ex: fazer checkout, configurar Java, rodar testes)
- Usada dentro de steps via `uses:`
- Pode ser criada localmente ou vinda do Marketplace

---

## Estrutura Interna de uma Action

Uma action pode ser criada de três formas:

1. **Action Docker** - Containeriza a aplicação
2. **Action JavaScript** - Executa código JS diretamente
3. **Action Composta (Composite)** - Combina múltiplos comandos/shells

Estrutura típica de uma action:
```
action/
├── action.yml       # Manifesto da action
├── entrypoint.sh    # Script de execução (para Docker)
├── index.js         # Código JavaScript
└── README.md        # Documentação
```

---

## O Arquivo action.yml

O `action.yml` é o arquivo de definição metadata de uma action. Ele especifica:

- **name**: Nome da action
- **description**: Descrição do que faz
- **inputs**: Parâmetros de entrada
- **outputs**: Valores que a action retorna
- **runs**: Como a action é executada

Exemplo de `action.yml`:
```yaml
name: 'Checkout'
description: 'Checa out o repositório'
inputs:
  repository:
    description: 'Repositório para clonar'
    required: true
runs:
  using: 'node16'
  main: 'index.js'
```

---

## Exemplo Prático: actions/checkout@v4

### Como é chamada no workflow

No arquivo `ci.yml`, a action é usada assim:

```yaml
- name: Checkout code
  uses: actions/checkout@v4
  with:
    fetch-depth: 1
```

### Parâmetros passados (via `with:`)

| Parâmetro | Descrição |
|-----------|-----------|
| `fetch-depth` | Profundidade do git fetch (1 = shallow clone) |
| `repository` | Repositório específico para clonar |
| `ref` | Branch ou tag específica |
| `token` | Token para operações autenticadas |

### Como funciona internamente

1. O arquivo `action.yml` define que a action usa Node.js 16
2. O `index.js` executa a lógica de checkout usando `@actions/core` e `@actions/toolkit`
3. Clona o repositório no runner com as opções especificadas
4. Retorna outputs como `checkout-path` para próximas steps

---

## Conclusão

- **Workflow** = Arquivo YAML completo que orquestra jobs
- **Action** = Bloco reutilizável que executa uma tarefa específica
- O `action.yml` é o contrato que define inputs, outputs e execução de uma action
