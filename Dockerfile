FROM eclipse-temurin:17
LABEL maintainer="lucasliaw20@gmail.com"
WORKDIR /app
COPY target/Library-0.0.1-SNAPSHOT.jar /app/MyLibrary.jar
ENTRYPOINT ["java", "-jar", "MyLibrary.jar"]