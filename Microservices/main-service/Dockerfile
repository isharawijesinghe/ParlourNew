FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} main-service.jar
ENTRYPOINT ["java","-jar","/main-service.jar"]
EXPOSE 9001