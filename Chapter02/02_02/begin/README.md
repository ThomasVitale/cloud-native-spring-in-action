# Greeting Service

Initialize a Spring project for the Greeting Service application with your favorite method, for example from [Spring Initialzr](https://start.spring.io/)
or your IDE.

## Spring Initializr - REST API

If you prefer using the REST API offered by Spring Initializr, use this command to initialize a project for the Greeting Service:

```bash
curl https://start.spring.io/starter.tgz -d groupId=com.arcticgreetings -d artifactId=greeting-service -d name=greeting-service -d packageName=com.arcticgreetings.greetingservice -d dependencies=web -d javaVersion=11 -d bootVersion=2.3.2 -d type=gradle-project -o greeting-service.zip
```

For using Maven instead of Gradle:

```bash
curl https://start.spring.io/starter.tgz -d groupId=com.arcticgreetings -d artifactId=greeting-service -d name=greeting-service -d packageName=com.arcticgreetings.greeting-service -d dependencies=web -d javaVersion=11 -d bootVersion=2.3.2 -o greeting-service.zip
```

## Spring Initializr - App

From the Spring Initializr application, choose the following:

* _Project_: Gradle (or Maven)
* _Spring Boot_: 2.3.2
* _Group_: `com.arcticgreetings`
* _Artifact_: `greeting-service`
* _Name_: greeting-service
* _Package name_: `com.arcticgreetings.greetingservice`
* _Java version_: 11
* _Dependencies_: Spring Web
