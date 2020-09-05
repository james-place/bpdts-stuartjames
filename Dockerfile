FROM openjdk:8-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
EXPOSE 8080
ARG JAR_FILE=target/bpdtstest-stuartjames-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE}  bpdtstest.jar
ENTRYPOINT ["java","-jar","/bpdtstest.jar"]