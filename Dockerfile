FROM amazoncorretto:17-alpine-jdk
EXPOSE 8080
ARG JAR_FILE=target/catalog.jar
COPY ${JAR_FILE} catalog.jar
ENTRYPOINT ["java","-jar","/catalog.jar"]