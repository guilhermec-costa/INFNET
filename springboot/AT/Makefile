MAVEN_REPO ?= /tmp/tp2-m2-repo

.PHONY: start start-dev postgres test package

start:
	docker compose up -d postgres
	mvn -Dmaven.repo.local=$(MAVEN_REPO) spring-boot:run

start-dev:
	mvn -Dmaven.repo.local=$(MAVEN_REPO) spring-boot:run -Dspring-boot.run.profiles=dev

postgres:
	docker compose up -d postgres

test:
	mvn -Dmaven.repo.local=$(MAVEN_REPO) test

package:
	mvn -Dmaven.repo.local=$(MAVEN_REPO) package -DskipTests
