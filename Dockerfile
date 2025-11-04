# ED-155-SJ

FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/*.jar app.jar

# TODO ENV = PROD
ENV SPRING_PROFILES_ACTIVE=dev

EXPOSE 8181
ENTRYPOINT ["java","-jar","app.jar"]