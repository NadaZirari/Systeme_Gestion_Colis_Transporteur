# Étape 1 : construire l'application avec Maven
FROM maven:3.9.2-eclipse-temurin-17 AS build
WORKDIR /app

# Copier seulement le pom.xml pour télécharger les dépendances en premier
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copier le code source et compiler
COPY src ./src
RUN mvn clean package -DskipTests

# Étape 2 : exécuter l'application
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
