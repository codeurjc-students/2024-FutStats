# Frontend
FROM node:22.9.0 AS frontend

WORKDIR /frontend

# Copy build and config files
COPY ./Frontend/package.json /frontend/

RUN npm install && npm install -g @angular/cli

# Copy src files
COPY ./Frontend/src /frontend/src

RUN npm start

COPY /frontend/dist/frontend/browser /frontend/dist/frontend/browser

# Maven (frontend to backend)
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

ENV RUNNING_IN_DOCKER=true

COPY ./Backend/pom.xml /app/
COPY ./Backend/src /app/src

RUN mvn clean install -DskipTests -X -P Docker
