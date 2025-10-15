# Multi-stage build for Spring Boot application
FROM openjdk:17-jdk-alpine AS build

# Set working directory
WORKDIR /app

# Copy Maven files
COPY pom.xml ./
COPY src ./src/

# Build the application
RUN ./mvnw clean package -DskipTests

# Production stage
FROM openjdk:17-jre-alpine

# Set working directory
WORKDIR /app

# Create logs directory
RUN mkdir -p /app/logs

# Copy the built JAR file
COPY --from=build /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Set JVM options for containerized environment
ENV JAVA_OPTS="-Xmx512m -Xms256m -Djava.security.egd=file:/dev/./urandom"

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
