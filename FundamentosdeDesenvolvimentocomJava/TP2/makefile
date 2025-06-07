.PHONY: build run clean rebuild

build:
	@echo "Building TP2 java project"
	@rm -rf build
	@mkdir -p build
	@find . -name "*.java" | xargs javac -d build
	@echo "TP2 java project build finished"

run:
	@make build && \
	echo "Executing TP2 java project" && \
	java -cp build Exercicios

clean:
	@echo "Cleaning project"
	@rm -rf build
	@echo "TP2 java project cleaned"

rebuild: clean build
