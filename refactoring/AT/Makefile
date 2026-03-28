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