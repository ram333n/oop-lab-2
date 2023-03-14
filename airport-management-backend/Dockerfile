# syntax=docker/dockerfile:1
FROM openjdk:17-alpine

ENV DB_USERNAME=prokochuk \
    DB_PASSWORD=localdb \
    DB_URL=jdbc:postgresql://localhost:5432/airport-management-db

RUN mkdir -p "/home/app"

COPY ./target/airport-management-0.0.1-SNAPSHOT.jar /home/app

WORKDIR /home/app

CMD ["java", "-jar", "airport-management-0.0.1-SNAPSHOT.jar"]
