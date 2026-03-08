# Use Eclipse Temurin JRE (lightweight, actively maintained)
FROM eclipse-temurin:21-jre-alpine

# The application's .jar file
ARG JAR_FILE=target/*.jar

# Make ports available (8080 HTTP, 9090 gRPC)
EXPOSE 8080 9090

# Run as non-root user
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

# Add the application's .jar to the container
COPY ${JAR_FILE} app.jar

# Health check via Spring Actuator
HEALTHCHECK --interval=30s --timeout=5s --start-period=30s --retries=3 \
  CMD wget -qO- http://localhost:8080/actuator/health || exit 1

# Run the jar file
ENTRYPOINT ["java","-jar","/app.jar"]
