# Chapter 02 - Begin

In this chapter, you'll build a Greeting Service application. You can initialize a Spring Boot project using your favorite method. This guide describes
how to do that with the application and with the REST API. Either way, you'll get a zip archive that you can extract and import in your IDE.

## Initialize Greeting Service with Spring Initializr - App

From the [Spring Initialzr](https://start.spring.io/) application, choose the following:

* _Project_: Gradle (or Maven)
* _Spring Boot_: 2.5.1 (or the latest production version available)
* _Group_: `com.arcticgreetings`
* _Artifact_: `greeting-service`
* _Name_: greeting-service
* _Package name_: `com.arcticgreetings.greetingservice`
* _Java version_: 16
* _Dependencies_: Spring Web

Then, click "Generate" to download the `greeting-service.zip` archive containing the project.

## Initialize Greeting Service with Spring Initializr - REST API

If you prefer using the REST API offered by Spring Initializr, use this command to initialize a project with Gradle as the build tool:

```bash
curl https://start.spring.io/starter.tgz -d groupId=com.arcticgreetings -d artifactId=greeting-service -d name=greeting-service -d packageName=com.arcticgreetings.greetingservice -d dependencies=web -d javaVersion=16 -d bootVersion=2.5.1 -d type=gradle-project -o greeting-service.zip
```

For using Maven instead of Gradle:

```bash
curl https://start.spring.io/starter.tgz -d groupId=com.arcticgreetings -d artifactId=greeting-service -d name=greeting-service -d packageName=com.arcticgreetings.greetingservice -d dependencies=web -d javaVersion=16 -d bootVersion=2.5.1 -o greeting-service.zip
```
