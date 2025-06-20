# Use a minimal base image for running the application
FROM openjdk:17-slim

# Set the working directory
WORKDIR /app

# Copy the packaged application from the builder stage
COPY /target/*.jar app.jar

EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
