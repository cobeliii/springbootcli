FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /spring-cli

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /spring-cli

COPY --from=build /spring-cli/target/*.jar /spring-cli/spring-cli.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/spring-cli/spring-cli.jar"]