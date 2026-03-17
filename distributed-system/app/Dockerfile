# Spring Boot için Java 21 image'ını kullan
FROM eclipse-temurin:21-jdk AS build

# Çalışma dizini ayarla
WORKDIR /app

# Projeyi konteynere kopyala
COPY . .

# Maven ile projeyi derle
RUN ./mvnw clean package -DskipTests

# Çalıştırma aşaması için yeni bir image
FROM eclipse-temurin:21-jre

# Çalışma dizini ayarla
WORKDIR /app

# Paketlenen jar dosyasını konteynere kopyala
COPY --from=build /app/target/*.jar app.jar

# Uygulamayı çalıştır
ENTRYPOINT ["java", "-jar", "app.jar"]
