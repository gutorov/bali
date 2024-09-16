# Используем официальный образ OpenJDK 21
FROM openjdk:21-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем jar файл в контейнер
COPY bali-1.0.0.jar /app/bali-1.0.0.jar
COPY files /app/files

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "/app/bali-1.0.0.jar"]