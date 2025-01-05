# Etapa 1: Construcción
FROM openjdk:21-jdk-slim AS BUILD

# Establecer el directorio de trabajo
WORKDIR /app

# Instalar Maven 4 manualmente
RUN apt-get update && \
    apt-get install -y wget unzip && \
    wget https://downloads.apache.org/maven/maven-4/4.0.0-alpha-7/binaries/apache-maven-4.0.0-alpha-7-bin.zip && \
    unzip apache-maven-4.0.0-alpha-7-bin.zip -d /opt && \
    ln -s /opt/apache-maven-4.0.0-alpha-7/bin/mvn /usr/bin/mvn && \
    rm -rf apache-maven-4.0.0-alpha-7-bin.zip

# Copiar los archivos del proyecto al contenedor
COPY . .

# Construir el JAR con Maven
RUN mvn clean package -DskipTests

# Etapa 2: Imagen para ejecución
FROM openjdk:21-jdk-slim

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el JAR generado desde la etapa de construcción
COPY --from=BUILD /app/target/*.jar app.jar

ENV SPRING_PROFILES_ACTIVE=local
ENV SPRING_DATASOURCE_URL=jdbc:mysql://db:3306/api_rest?serverTimezone=Europe/Madrid
ENV SPRING_DATASOURCE_USERNAME=root  
ENV SPRING_DATASOURCE_PASSWORD=17042002
ENV SPRING_JACKSON_TIMEZONE=Europe/Madrid

# Exponer el puerto 8443
EXPOSE 8443

# Comando para ejecutar la aplicación
ENTRYPOINT ["sh", "-c", "sleep 60 && java -jar app.jar"]

