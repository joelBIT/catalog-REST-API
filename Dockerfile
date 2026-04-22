# Build stage
FROM maven:3.6.3-openjdk-17-slim AS build
ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME
ADD . $HOME
RUN mvn clean package

# Run stage
FROM amazoncorretto:17-alpine-jdk
EXPOSE 8080
COPY --from=build /usr/app/target/catalog.jar catalog.jar
ENTRYPOINT ["java","-jar","/catalog.jar"]