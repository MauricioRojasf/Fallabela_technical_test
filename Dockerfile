#build spring boot

FROM maven:latest AS build  

COPY src /usr/local/src

COPY pom.xml /usr/local/pom.xml

WORKDIR /usr/local/

RUN mvn clean install

#make container

FROM gcr.io/distroless/java:8

COPY --from=build /usr/local/target/falabella-techtest-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-Dspring.profiles.active=prod","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
