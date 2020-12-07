# Base image.
FROM ubuntu:20.04

# Install the Java Runtime Environment.
RUN apt-get update && apt-get install -y default-jre

# Define the "java --version" command as the execution entry point for the container.
ENTRYPOINT ["java", "--version"]