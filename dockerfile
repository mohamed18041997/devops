FROM openjdk:11-jdk-slim

WORKDIR /app

COPY target/*.jar /app

EXPOSE 8080

CMD ["java", "-jar", "DevOps_Project-2.1.jar"]