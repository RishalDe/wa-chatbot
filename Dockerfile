# Stage 1: Build the jar
FROM maven:3.9.4-eclipse-temurin-17 as build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Stage 2: Run the app
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY --from=build /app/target/telegramchat-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
