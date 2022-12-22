FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} api-gateway.jar
ENTRYPOINT ["java", "-jar", "/api-gateway.jar", "--spring.profiles.active=k8"]
EXPOSE 9191