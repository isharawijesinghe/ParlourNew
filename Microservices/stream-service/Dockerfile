FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} stream-service.jar
ENTRYPOINT ["java","-jar","/stream-service.jar"]
EXPOSE 9001