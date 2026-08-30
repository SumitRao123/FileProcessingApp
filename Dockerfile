FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY ./target/FileProcessingApp-0.0.1-SNAPSHOT.jar /app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "FileProcessingApp-0.0.1-SNAPSHOT.jar"]