# ---------- STAGE 1: The Builder ----------
FROM gradle:9.2.0-jdk21 AS builder
WORKDIR /app
RUN apt-get update && \
    apt-get install -y curl gnupg && \
    curl -fsSL https://deb.nodesource.com/setup_20.x | bash - && \
    apt-get install -y nodejs
COPY . .

RUN ./gradlew clean bootJar -x test --refresh-dependencies


# ---------- STAGE 2: The Runner ----------
FROM eclipse-temurin:25-jdk
WORKDIR /app
COPY --from=builder /app/build/libs/*-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
