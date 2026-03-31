FROM eclipse-temurin:22-jre-alpine
LABEL authors="kaval"
WORKDIR /app
COPY target/MoonTask.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]