FROM openjdk:22-jdk-alpine as build
WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go -DskipTests

COPY src ./src
RUN ./mvnw package -DskipTests

FROM openjdk:22-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar ./app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]