FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} article-service.jar
ENTRYPOINT ["java", "-jar", "/article-service.jar", "--spring.profiles.active=k8"]
EXPOSE 9001