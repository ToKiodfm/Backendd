FROM amazoncorretto: 11-alpine-jdk
MAINTAINER HaruMachado
COPY target/HM-0.0.1-SNAPSHOT.jar HM-app.jar
ENTRYPOINT ["java","-jar", "/HM-app.jar"]
