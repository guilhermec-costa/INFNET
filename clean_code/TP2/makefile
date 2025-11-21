JAVAC = javac
JAVA = java

SRC_DIR = src
BIN_DIR = bin

JAVAC_FLAGS = -d $(BIN_DIR) -sourcepath $(SRC_DIR)

EXERCICIOS = CalculadoraNumeros \
             CalculadoraDesconto \
             Pedido \
             AjustePreco \
             ClienteClassificador \
             InterfacePersonalizacao \
             RelatorioService \
             ContaBancaria \
             FilaMensagens \
             Monitoramento \
             PedidoService \
             NotificacaoService

.PHONY: all
all: setup compile

.PHONY: setup
setup:
	@echo "==================================="
	@echo "Criando estrutura de diretórios..."
	@echo "==================================="
	@mkdir -p $(BIN_DIR)
	@echo "✓ Diretórios criados"
	@echo ""

.PHONY: compile
compile: setup
	@echo "==================================="
	@echo "Compilando exercícios..."
	@echo "==================================="
	@$(JAVAC) $(JAVAC_FLAGS) $(SRC_DIR)/CalculadoraNumeros.java
	@echo "✓ Exercício 1 compilado"
	@$(JAVAC) $(JAVAC_FLAGS) $(SRC_DIR)/CalculadoraDesconto.java
	@echo "✓ Exercício 2 compilado"
	@$(JAVAC) $(JAVAC_FLAGS) $(SRC_DIR)/Pedido.java
	@echo "✓ Exercício 3 compilado"
	@$(JAVAC) $(JAVAC_FLAGS) $(SRC_DIR)/AjustePreco.java
	@echo "✓ Exercício 4 compilado"
	@$(JAVAC) $(JAVAC_FLAGS) $(SRC_DIR)/ClienteClassificador.java
	@echo "✓ Exercício 5 compilado"
	@$(JAVAC) $(JAVAC_FLAGS) $(SRC_DIR)/InterfacePersonalizacao.java
	@echo "✓ Exercício 6 compilado"
	@$(JAVAC) $(JAVAC_FLAGS) $(SRC_DIR)/RelatorioService.java
	@echo "✓ Exercício 7 compilado"
	@$(JAVAC) $(JAVAC_FLAGS) $(SRC_DIR)/ContaBancaria.java
	@echo "✓ Exercício 8 compilado"
	@$(JAVAC) $(JAVAC_FLAGS) $(SRC_DIR)/FilaMensagens.java
	@echo "✓ Exercício 9 compilado"
	@$(JAVAC) $(JAVAC_FLAGS) $(SRC_DIR)/Monitoramento.java
	@echo "✓ Exercício 10 compilado"
	@$(JAVAC) $(JAVAC_FLAGS) $(SRC_DIR)/PedidoService.java
	@echo "✓ Exercício 11 compilado"
	@$(JAVAC) $(JAVAC_FLAGS) $(SRC_DIR)/NotificacaoService.java
	@echo "✓ Exercício 12 compilado"
	@echo ""
	@echo "==================================="
	@echo "✓ Todos os exercícios compilados"
	@echo "==================================="
	@echo ""

# Executar todos os exercícios
.PHONY: run
run: all
	@echo ""
	@echo "###################################################################"
	@echo "#                    EXECUTANDO EXERCÍCIO 1                       #"
	@echo "###################################################################"
	@$(JAVA) -cp $(BIN_DIR) CalculadoraNumeros
	@echo ""
	@echo "###################################################################"
	@echo "#                    EXECUTANDO EXERCÍCIO 2                       #"
	@echo "###################################################################"
	@$(JAVA) -cp $(BIN_DIR) CalculadoraDesconto
	@echo ""
	@echo "###################################################################"
	@echo "#                    EXECUTANDO EXERCÍCIO 3                       #"
	@echo "###################################################################"
	@$(JAVA) -cp $(BIN_DIR) Pedido
	@echo ""
	@echo "###################################################################"
	@echo "#                    EXECUTANDO EXERCÍCIO 4                       #"
	@echo "###################################################################"
	@$(JAVA) -cp $(BIN_DIR) AjustePreco
	@echo ""
	@echo "###################################################################"
	@echo "#                    EXECUTANDO EXERCÍCIO 5                       #"
	@echo "###################################################################"
	@$(JAVA) -cp $(BIN_DIR) ClienteClassificador
	@echo ""
	@echo "###################################################################"
	@echo "#                    EXECUTANDO EXERCÍCIO 6                       #"
	@echo "###################################################################"
	@$(JAVA) -cp $(BIN_DIR) InterfacePersonalizacao
	@echo ""
	@echo "###################################################################"
	@echo "#                    EXECUTANDO EXERCÍCIO 7                       #"
	@echo "###################################################################"
	@$(JAVA) -cp $(BIN_DIR) RelatorioService
	@echo ""
	@echo "###################################################################"
	@echo "#                    EXECUTANDO EXERCÍCIO 8                       #"
	@echo "###################################################################"
	@$(JAVA) -cp $(BIN_DIR) ContaBancaria
	@echo ""
	@echo "###################################################################"
	@echo "#                    EXECUTANDO EXERCÍCIO 9                       #"
	@echo "###################################################################"
	@$(JAVA) -cp $(BIN_DIR) FilaMensagens
	@echo ""
	@echo "###################################################################"
	@echo "#                    EXECUTANDO EXERCÍCIO 10                      #"
	@echo "###################################################################"
	@$(JAVA) -cp $(BIN_DIR) Monitoramento
	@echo ""
	@echo "###################################################################"
	@echo "#                    EXECUTANDO EXERCÍCIO 11                      #"
	@echo "###################################################################"
	@$(JAVA) -cp $(BIN_DIR) PedidoService
	@echo ""
	@echo "###################################################################"
	@echo "#                    EXECUTANDO EXERCÍCIO 12                      #"
	@echo "###################################################################"
	@$(JAVA) -cp $(BIN_DIR) NotificacaoService
	@echo ""
	@echo "==================================="
	@echo "✓ Todos os exercícios executados"
	@echo "==================================="

.PHONY: ex1
ex1: all
	@$(JAVA) -cp $(BIN_DIR) CalculadoraNumeros

.PHONY: ex2
ex2: all
	@$(JAVA) -cp $(BIN_DIR) CalculadoraDesconto

.PHONY: ex3
ex3: all
	@$(JAVA) -cp $(BIN_DIR) Pedido

.PHONY: ex4
ex4: all
	@$(JAVA) -cp $(BIN_DIR) AjustePreco

.PHONY: ex5
ex5: all
	@$(JAVA) -cp $(BIN_DIR) ClienteClassificador

.PHONY: ex6
ex6: all
	@$(JAVA) -cp $(BIN_DIR) InterfacePersonalizacao

.PHONY: ex7
ex7: all
	@$(JAVA) -cp $(BIN_DIR) RelatorioService

.PHONY: ex8
ex8: all
	@$(JAVA) -cp $(BIN_DIR) ContaBancaria

.PHONY: ex9
ex9: all
	@$(JAVA) -cp $(BIN_DIR) FilaMensagens

.PHONY: ex10
ex10: all
	@$(JAVA) -cp $(BIN_DIR) Monitoramento

.PHONY: ex11
ex11: all
	@$(JAVA) -cp $(BIN_DIR) PedidoService

.PHONY: ex12
ex12: all
	@$(JAVA) -cp $(BIN_DIR) NotificacaoService

.PHONY: clean
clean:
	@echo "Removendo arquivos compilados..."
	@rm -rf $(BIN_DIR)
	@echo "✓ Limpeza concluída"