FROM openjdk:11
ADD src/teamwork-api.jar teamwork-api.jar  
ENTRYPOINT ["java", "-jar","teamwork-api.jar"]
EXPOSE 8080