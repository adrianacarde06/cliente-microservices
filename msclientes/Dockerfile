# ==========================================
# ETAPA 1: Compilación (Builder)
# ==========================================
FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /app

# 1. Copiamos solo el pom.xml y descargamos dependencias.
# Al hacer esto en una capa separada, Docker cachea las dependencias.
# Si no cambias el pom.xml, las siguientes compilaciones serán mucho más rápidas.
COPY pom.xml .
RUN mvn dependency:go-offline -B

# 2. Copiamos el código fuente y compilamos.
COPY src ./src
RUN mvn clean package -DskipTests

# ==========================================
# ETAPA 2: Ejecución (Runtime)
# ==========================================
# Usamos una imagen base ligera (Alpine) solo con el JRE (no JDK)
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# 3. BUENA PRÁCTICA DE SEGURIDAD: No correr como root.
# Creamos un usuario y grupo sin privilegios para la aplicación.
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# 4. Copiamos el JAR generado en la etapa anterior
COPY --from=builder /app/target/*.jar app.jar

# 5. Exponemos el puerto (informativo, K8s o Docker lo mapean)
EXPOSE 8080

# 6. Punto de entrada optimizado para contenedores
ENTRYPOINT ["java", "-jar", "app.jar"]