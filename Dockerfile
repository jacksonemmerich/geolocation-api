#FROM openjdk:17-jdk-alpine as build
#WORKDIR /app
#
#COPY .mvn/ .mvn
#COPY mvnw pom.xml ./
#RUN chmod +x ./mvnw
#RUN ./mvnw dependency:go-offline -DskipTests
#
#
#
#COPY src ./src
#RUN ./mvnw package -DskipTests
#
#FROM openjdk:17-jdk-alpine
#WORKDIR /app
#COPY --from=build /app/target/*.jar ./app.jar
#EXPOSE 8081
#ENTRYPOINT ["java", "-jar", "app.jar"]