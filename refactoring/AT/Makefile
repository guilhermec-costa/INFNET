.PHONY: all build test clean parte1 parte2 parte3 parte4 parte5 parte6 run-all help

all: build

build:
	mvn compile -q

test:
	mvn test

clean:
	mvn clean

parte1:
	@echo "=== Parte 1: Refatoração Código Simples ==="
	mvn exec:java -Dexec.mainClass="parte1.ClassificationService" -q

parte1-test:
	@echo "=== Parte 1: Executando Testes ==="
	mvn test -Dtest=ClassificationServiceTest

parte2:
	@echo "=== Parte 2: Identificando Bad Smells ==="
	mvn exec:java -Dexec.mainClass="parte2.BadSmellsAnalysis" -q

parte3:
	@echo "=== Parte 3: Refatorando para Legibilidade ==="
	mvn exec:java -Dexec.mainClass="parte3.PricingService" -q

parte4:
	@echo "=== Parte 4: Refatorando para Modularidade ==="
	mvn exec:java -Dexec.mainClass="parte4.User" -q

parte5:
	@echo "=== Parte 5: Polimorfismo em Notificações ==="
	mvn exec:java -Dexec.mainClass="parte5.NotificationService" -q

parte6:
	@echo "=== Parte 6: Hierarquia de Documentos ==="
	mvn exec:java -Dexec.mainClass="parte6.DocumentDemo" -q

run-all: parte1 parte2 parte3 parte4 parte5 parte6

help:
	@echo "Makefile - Tarefa de Refatoração (6 partes)"
	@echo ""
	@echo "Comandos disponíveis:"
	@echo "  make build        - Compila o projeto"
	@echo "  make test        - Executa todos os testes"
	@echo "  make clean       - Limpa arquivos compilados"
	@echo ""
	@echo "Execução das partes:"
	@echo "  make parte1      - Executa Parte 1: Refatoração código simples"
	@echo "  make parte1-test - Executa testes da Parte 1"
	@echo "  make parte2      - Executa Parte 2: Identificando Bad Smells"
	@echo "  make parte3      - Executa Parte 3: Refatorando para legibilidade"
	@echo "  make parte4      - Executa Parte 4: Refatorando para modularidade"
	@echo "  make parte5      - Executa Parte 5: Polimorfismo em notificações"
	@echo "  make parte6      - Executa Parte 6: Hierarquia de documentos"
	@echo ""
	@echo "  make run-all     - Executa todas as partes"
	@echo "  make help        - Mostra esta ajuda"
