# syntax=docker/dockerfile:1

FROM openjdk:11

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY src ./src

EXPOSE 7999
