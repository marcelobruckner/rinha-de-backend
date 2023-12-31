FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY ./target/rinha-de-backend-0.0.1-SNAPSHOT.jar ./rinha.jar

EXPOSE 8000

# ENTRYPOINT [ "java", "-jar", "./rinha.jar" ]
ENTRYPOINT [ "java", "-XX:+UseParallelGC", "-XX:MaxRAMPercentage=75", "--enable-preview", "-jar", "rinha.jar" ]