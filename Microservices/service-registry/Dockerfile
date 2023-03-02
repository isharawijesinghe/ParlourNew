FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} service-registry.jar
ENTRYPOINT ["java","-jar","/service-registry.jar", "--spring.profiles.active=k8"]
EXPOSE 8761