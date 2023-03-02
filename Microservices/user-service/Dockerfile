FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} user-service.jar
ENTRYPOINT ["java", "-jar", "/user-service.jar", "--spring.profiles.active=k8"]
EXPOSE 9002