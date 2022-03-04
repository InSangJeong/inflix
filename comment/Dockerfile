FROM openjdk:8-jdk-alpine
COPY build/libs/*.jar comment.jar
ENTRYPOINT ["java","-jar","/comment.jar"]