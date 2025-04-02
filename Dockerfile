# Base image
FROM maven:3-amazoncorretto-21 AS build
WORKDIR /app

# Copy the compiled JAR file into the container
COPY pom.xml .
COPY src ./src

# Build Maven project
RUN mvn clean package

# Create the runtime image
FROM eclipse-temurin:21-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port your app runs on
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]