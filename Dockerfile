FROM openjdk:8
ADD target/carsapp-0.0.1-SNAPSHOT.jar carapp.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "carapp.jar"]