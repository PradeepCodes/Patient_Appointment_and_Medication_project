# 1) Build stage: compile & package into fat-jar
FROM maven:3.9.0-jdk-17 AS builder
WORKDIR /workspace

# Cache dependencies
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# Build application
COPY src src
RUN ./mvnw package -DskipTests -B

# 2) Runtime stage: minimal JRE
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /workspace/target/*.jar app.jar

# Expose port (match your Spring Boot server.port)
EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
