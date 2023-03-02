FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} stream-service.jar
ENTRYPOINT ["java","-jar","/stream-service.jar", "--spring.profiles.active=k8"]
EXPOSE 9001