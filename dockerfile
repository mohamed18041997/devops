FROM openjdk:11-jdk-slim

WORKDIR /app

COPY target/*.jar /app

EXPOSE 8080

CMD ["java", "-jar", "eventsProject-1.0.0.jar"]