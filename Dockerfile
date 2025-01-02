# Use OpenJDK 20 image to build the app
FROM openjdk:20-jdk-slim AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy Gradle wrapper and configuration files
COPY gradlew settings.gradle /app/
COPY gradle /app/gradle
COPY build.gradle /app/

# Copy the source code into the container
COPY src /app/src

# Make the gradlew script executable
RUN chmod +x gradlew

# Verify Gradle is installed and has execute permission
RUN ./gradlew -v

# Run Gradle to build the application (this step only builds the app)
RUN ./gradlew build --no-daemon

# Second stage to run the application
FROM openjdk:20-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file from the builder image
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose the application port (default Spring Boot port)
EXPOSE 8080

# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
