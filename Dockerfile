# Angular
FROM node:22.0.0-slim AS frontend
WORKDIR /app/
COPY Frontend/package*.json Frontend/angular.json .
RUN npm install && npm install -g @angular/cli
COPY Frontend/ .
RUN ng build --configuration production
 
# Etapa 1: Construcción
FROM maven:3.9.9-eclipse-temurin-21 AS BUILD
 
# Establecer el directorio de trabajo
WORKDIR /app
 
# Copiar los archivos del proyecto al contenedor
COPY Backend/ .
COPY --from=frontend /app/dist/ejem1/browser /app/src/main/resources/static
 
# Construir el JAR con Maven
RUN mvn clean package -DskipTests
 
# Etapa 2: Imagen para ejecución
FROM eclipse-temurin:21-jdk
 
# Establecer el directorio de trabajo
WORKDIR /app
 
# Copiar el JAR generado desde la etapa de construcción
COPY --from=BUILD /app/target/*.jar app.jar
 
# Exponer el puerto 8443
EXPOSE 8443
 
# Comando para ejecutar la aplicación
CMD ["java", "-jar", "app.jar"]
