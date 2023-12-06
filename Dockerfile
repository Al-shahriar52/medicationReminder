FROM maven:3.8.5-openjdk-19 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:19.0.1-jdk-slim
COPY --from=build /target/MedicationReminderBackEndCode-0.0.1-SNAPSHOT.jar MedicationReminderBackEndCode.jar
EXPOSE 3306
ENTRYPOINT ["java","-jar","MedicationReminderBackEndCode.jar"]