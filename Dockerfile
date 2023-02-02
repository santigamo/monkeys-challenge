# Build jar file
FROM maven:3.8.7-amazoncorretto-17 as builder
USER root
WORKDIR /builder
ADD . /builder
RUN mvn clean package -DskipTests

# Run jar file
FROM amazoncorretto:17.0.6
WORKDIR /app
EXPOSE 8080
COPY --from=builder /builder/target/*.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]