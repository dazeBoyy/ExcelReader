# Используем официальный образ OpenJDK 17
FROM openjdk:23-jdk-slim AS build
# Устанавливаем рабочую директорию
WORKDIR /app
COPY . .
RUN ./gradlew build --no-daemon

# Финальный этап
FROM openjdk:23-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]