FROM maven:3.9.9-amazoncorretto-21 AS build
ENV HOME=/app
RUN mkdir -p "$HOME"
WORKDIR $HOME
COPY pom.xml $HOME
RUN mvn verify --fail-never
COPY . $HOME
RUN mvn package -Dmaven.test.skip

FROM amazoncorretto:21.0.6-alpine3.21
COPY --from=build /app/target/*.jar /app.jar
RUN mkdir "images"
CMD ["java", "-jar", "/app.jar"]
