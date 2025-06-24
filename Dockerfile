# Stage 1: Build with Maven + Java 17
FROM maven:3.9.2-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml and source files
COPY pom.xml .
COPY src ./src

# Build the project, skipping tests
RUN mvn clean package -DskipTests

# Stage 2: Run the app with Java 17 runtime
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Copy the jar built in the previous stage
COPY --from=build /app/target/telegramchat-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080 for Spring Boot
EXPOSE 8080

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
