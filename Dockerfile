# Angular
FROM node:22.0.0-slim AS frontend
WORKDIR /app/
COPY Frontend/package*.json Frontend/angular.json ./
RUN npm install && npm install -g @angular/cli
COPY Frontend/ ./
RUN ng build --configuration production

# Backend Build
FROM maven:3.9.9-eclipse-temurin-21 AS BUILD
WORKDIR /app
COPY Backend/ ./
COPY --from=frontend /app/dist/ejem1/browser /app/src/main/resources/static
RUN mvn clean package -DskipTests

# Runtime
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=BUILD /app/target/*.jar app.jar
EXPOSE 8443
CMD ["java", "-jar", "app.jar"]
