# BuildPipeline Refactoring Kata - Java

## Visão Geral do Projeto

Este projeto faz parte do **BuildPipeline Refactoring Kata**, originalmente criado por [Tom Oram](https://github.com/tomphp). O objetivo principal é refatorar um sistema de pipeline de build legítimo para melhorar sua clareza, manutenibilidade e testabilidade.

### Funcionalidade Original

O sistema `Pipeline` é responsável por:
1. Executar testes unitários de um projeto
2. Fazer deploy se os testes passarem
3. Enviar um email resumindo o resultado (se configurado)

### Problemas Identificados no Código Original

O código original apresentava vários problemas de qualidade:

1. **Método longo com múltiplas responsabilidades** - O método `run()` fazia tudo (testes, deploy, email)
2. **Duplicação de lógica** - `"success".equals(...)` aparecia múltiplas vezes
3. **Strings mágicas** - Mensagens hardcoded distribuídas pelo código
4. **Condicionais aninhados** - Lógica de email com 3 níveis de if-else
5. **Ausência de testes** - O arquivo de teste continha apenas um TODO

---

## Refatorações Realizadas

### 1. Testes Automatizados

**Problema:** O arquivo `PipelineTest.java` não continha testes reais.

**Solução:** Implementei testes unitários completos usando JUnit 5 e Mockito:

- `shouldRunTestsAndDeploySuccessfully` - Testa cenário de sucesso
- `shouldSendFailureEmailWhenTestsFail` - Testa falha nos testes
- `shouldSendFailureEmailWhenDeploymentFails` - Testa falha no deploy
- `shouldNotSendEmailWhenEmailIsDisabled` - Testa configuração de email
- `shouldLogCorrectMessagesWhenTestsPass` - Verifica logs de sucesso
- `shouldLogCorrectMessagesWhenTestsFail` - Verifica logs de falha
- `shouldLogNoTestsWhenProjectHasNoTests` - Testa projeto sem testes

### 2. Constantes para Strings Mágicas

**Problema:** Strings como `"success"`, `"Tests failed"`, etc., estavam hardcoded.

**Solução:** Criei  a classe `BuildResult` com constantes:

```java
public static final String SUCCESS = "success";
public static final String MESSAGE_TESTS_PASSED = "Tests passed";
public static final String EMAIL_DEPLOYMENT_COMPLETED = "Deployment completed successfully";
// ... outras constantes
```

### 3. Extração de Métodos

O método `run()` foi dividido em métodos menores e coesos:

| Método | Responsabilidade |
|--------|------------------|
| `executeTests(Project)` | Executa testes e retorna boolean |
| `deployIfTestsPass(Project, boolean)` | Faz deploy se testes passaram |
| `sendSummaryEmailIfEnabled(boolean, boolean)` | Envia email se habilitado |
| `determineEmailMessage(boolean, boolean)` | Determina a mensagem do email |

### 4. Simplificação de Condicionais

**Antes:** 3 níveis de if-else aninhados para determinar a mensagem de email:
```java
if (testsPassed) {
    if (deploySuccessful) {
        emailer.send("Deployment completed successfully");
    } else {
        emailer.send("Deployment failed");
    }
} else {
    emailer.send("Tests failed");
}
```

**Depois:** Lógica linear e clara:
```java
private String determineEmailMessage(boolean testsPassed, boolean deploySuccessful) {
    if (!testsPassed) return BuildResult.EMAIL_TESTS_FAILED;
    if (deploySuccessful) return BuildResult.EMAIL_DEPLOYMENT_COMPLETED;
    return BuildResult.EMAIL_DEPLOYMENT_FAILED;
}
```

### 5. Atualização de Dependências

Atualizei as dependências para compatibilidade com Java 21:
- Gradle 6.2.2 → 8.5
- JUnit 5.6.0 → 5.10.0
- Mockito 2.+ → 5.+

---

## Executando os Testes

```bash
cd java
./gradlew test
```

---

## Estrutura Final

```
java/
├── src/
│   ├── main/java/org/sammancoaching/
│   │   ├── Pipeline.java              # Classe principal refatorada
│   │   └── dependencies/
│   │       ├── BuildResult.java
│   │       ├── Config.java
│   │       ├── Emailer.java
│   │       ├── Logger.java
│   │       ├── Project.java
│   │       ├── TestStatus.java
│   │       └── DeploymentEnvironment.java
│   └── test/java/org/sammancoaching/
│       ├── PipelineTest.java          # Testes completos
│       └── CapturingLogger.java       # Logger para testes
├── build.gradle
└── gradle/wrapper/
```

---

## Benefícios da Refatoração

| Aspecto | Antes | Depois |
|---------|-------|--------|
| **Legibilidade** | Método único de 46 linhas | 5 métodos menores e nomeados |
| **Testabilidade** | Sem testes | 8 testes cobrindo cenários principais |
| **Manutenibilidade** | Strings mágicas | Constantes centralizadas |
| **Acoplamento** | Lógica misturada | Responsabilidades separadas |
| **Complexidade ciclomática** | Alta (3 níveis aninhados) | Baixa (lógica linear) |

---