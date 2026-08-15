FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY pom.xml .
COPY discovery-server/pom.xml discovery-server/pom.xml
COPY api-gateway/pom.xml api-gateway/pom.xml
COPY reservation-service/pom.xml reservation-service/pom.xml
COPY notification-service/pom.xml notification-service/pom.xml
RUN mvn -pl discovery-server -am dependency:go-offline
COPY discovery-server/src discovery-server/src
RUN mvn -pl discovery-server -am package -DskipTests
FROM eclipse-temurin:21-jre-alpine
COPY --from=build /workspace/discovery-server/target/discovery-server-1.0.0.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
