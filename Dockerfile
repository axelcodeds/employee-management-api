FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app

# Copy only what is needed for a reproducible Maven build
COPY pom.xml ./
COPY src ./src

# Build the application
# RUN mvn -B -DskipTests package
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the fat jar produced by the builder stage
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]

