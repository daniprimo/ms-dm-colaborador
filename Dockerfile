FROM maven:3.8.5-openjdk-17 as build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests


FROM openjdk:17
WORKDIR /app
COPY --from=build ./app/target/*.jar ./app.jar
EXPOSE 8761

ARG EUREKA_SERVER=localhost
ARG SERVER_DATABASE=postgres
ARG DATABASE_PORT=5432
ARG DATABASE_PASSWORD=0000

ENTRYPOINT java -jar -Dspring.profiles.active=hmg app.jar