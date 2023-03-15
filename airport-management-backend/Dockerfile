# syntax=docker/dockerfile:1
FROM openjdk:17-alpine

COPY ./target/*.jar /application/app.jar

WORKDIR /application

CMD ["java", "-jar", "app.jar"]