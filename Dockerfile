# ===========================================
FROM maven:3.9.9-eclipse-temurin-21 AS builder

# USER root
 
COPY app /app

WORKDIR /app

RUN mvn clean package

ENTRYPOINT [ "java" , "-jar", "/app/app/target/openapi-spring-1.0.0.jar"]

# ===========================================
FROM eclipse-temurin:21-alpine 

WORKDIR /app

COPY --from=builder /app/target/openapi-spring-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
