.DEFAULT_GOAL := help

.PHONY: help build test up down logs status clean package

help:
	@echo "make build    - compila todos os módulos Maven"
	@echo "make test     - executa os testes"
	@echo "make up       - sobe toda a arquitetura via Docker Compose"
	@echo "make down     - para os containers"
	@echo "make logs     - acompanha os logs"
	@echo "make status   - mostra o status dos containers"
	@echo "make package  - gera o arquivo ZIP para entrega"

build:
	mvn -Dmaven.repo.local=.m2/repository clean package -DskipTests

test:
	mvn -Dmaven.repo.local=.m2/repository test

up:
	docker compose up --build -d

down:
	docker compose down

logs:
	docker compose logs -f

status:
	docker compose ps

clean:
	mvn -Dmaven.repo.local=.m2/repository clean

package:
	rm -f guilherme_china_DR3_TP1.zip
	zip -r guilherme_china_DR3_TP1.zip . -x '*/target/*' '.git/*' '.codex/*' '.agents/*' '.m2/*' 'guilherme_china_DR3_TP1.zip'
