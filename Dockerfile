# Base image
FROM openjdk:21-jdk-slim AS build
WORKDIR /app

# Add Maven binary
RUN apt-get update && \
    apt-get install -y wget && \
    wget https://downloads.apache.org/maven/maven-3/3.9.9/binaries/apache-maven-3.9.9-bin.tar.gz && \
    tar xzvf apache-maven-3.9.9-bin.tar.gz && \
    mv apache-maven-3.9.9 /usr/local/apache-maven && \
    ln -s /usr/local/apache-maven/bin/mvn /usr/bin/mvn && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/* apache-maven-3.9.9-bin.tar.gz

RUN mvn -v

# Copy the compiled JAR file into the container
COPY pom.xml /app
COPY src /app/src

# Build Maven project
RUN mvn clean package

# Create the runtime image
FROM openjdk:21-jdk-slim
WORKDIR /app

# Copy the JAR from the build stage
COPY --from=build /app/target/device-api-*.jar app.jar

# Expose the port your app runs on
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]