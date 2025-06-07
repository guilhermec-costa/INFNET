.PHONY: build run clean rebuild

build:
	@echo "Building TP1 java project"
	@rm -rf build
	@mkdir -p build
	@find . -name "*.java" | xargs javac -d build
	@echo "TP1 java project build finished"

run:
	@make build && \
	echo "Executing TP1 java project" && \
	java -cp build Main

clean:
	@echo "Cleaning project"
	@rm -rf build
	@echo "TP1 java project cleaned"

rebuild: clean build
