# =========================
# Build stage
# =========================
FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /app

# Copia arquivos do Gradle primeiro (cache de dependências)
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./

# Dá permissão de execução
RUN chmod +x gradlew

# Baixa dependências (cache layer)
RUN ./gradlew dependencies --no-daemon || true

# Copia o código fonte
COPY src src

# Build do bootJar
RUN ./gradlew clean bootJar --no-daemon

# =========================
# Runtime stage
# =========================
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
