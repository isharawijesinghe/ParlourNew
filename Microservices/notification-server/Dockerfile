FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} notification-service.jar
ENTRYPOINT ["java", "-jar", "/notification-service.jar", "--spring.profiles.active=k8"]
EXPOSE 9002