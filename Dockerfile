# Use a Java 17 runtime base image
FROM eclipse-temurin:17-jdk-jammy

# Set working directory inside container
WORKDIR /app

# Copy the packaged jar file from your local 'target' directory to the container
COPY target/telegramchat-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080 (default Spring Boot port)
EXPOSE 8080

# Command to run your Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
