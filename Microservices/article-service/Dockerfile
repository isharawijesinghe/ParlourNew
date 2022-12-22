FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} main-service.jar
ENTRYPOINT ["java", "-jar", "/main-service.jar", "--spring.profiles.active=k8"]
EXPOSE 9001