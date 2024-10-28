FROM openjdk:17-jdk

WORKDIR /jgen

ARG JAR_FILE=target/*.jar

COPY src/main/resources/templates /jgen/templates

COPY ${JAR_FILE} /jgen/jgen.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/jgen/jgen.jar"]