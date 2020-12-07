# Use AdoptOpenJDk 11 as base image.
FROM adoptopenjdk:11-jre-hotspot-bionic

# Define the path where the application JAR is located as an argument.
# By default, the path used by Gradle projects is defined.
ARG JAR_FILE=build/libs/*.jar

# Copy the application JAR from your project into the image.
COPY ${JAR_FILE} catalog-service.jar

# Define the execution of your application JAR as the entry point of the container.
ENTRYPOINT ["java", "-jar" ,"catalog-service.jar"]
