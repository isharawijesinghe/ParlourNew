FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} authorization-service.jar
ENTRYPOINT ["java", "-jar", "/authorization-service.jar", "--spring.profiles.active=k8"]
EXPOSE 9002