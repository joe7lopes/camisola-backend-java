FROM openjdk:11-jre-slim
EXPOSE 3001
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]