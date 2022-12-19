# Chapter 02 - Begin

In this chapter, you'll build a Config Service application. You can initialize a Spring Boot project using your
favorite method. This guide describes how to do that with the Spring Initializr application and with its REST API.
Either way, you'll get a zip archive that you can extract and import in your IDE.

## Initialize Config Service with Spring Initializr - Website

From the [Spring Initialzr](https://start.spring.io/) website, choose the following:

* _Project_: Gradle (or Maven)
* _Spring Boot_: 2.7.3 (or the latest 2.x production version available)
* _Group_: `com.polarbookshop`
* _Artifact_: `config-service`
* _Name_: config-service
* _Package name_: `com.polarbookshop.configservice`
* _Java version_: 17
* _Dependencies_: Config Server

Then, click "Generate" to download the `config-service.zip` archive containing the project.

## Initialize Config Service with Spring Initializr - REST API

If you prefer using the REST API offered by Spring Initializr, run the following command to initialize a project with Gradle as the build tool:

```bash
curl https://start.spring.io/starter.zip -d groupId=com.polarbookshop -d artifactId=config-service -d name=config-service -d packageName=com.polarbookshop.configservice -d dependencies=cloud-config-server -d javaVersion=17 -d bootVersion=2.7.3 -d type=gradle-project -o config-service.zip
```

For using Maven instead of Gradle:

```bash
curl https://start.spring.io/starter.zip -d groupId=com.polarbookshop -d artifactId=config-service -d name=config-service -d packageName=com.polarbookshop.configservice -d dependencies=cloud-config-server -d javaVersion=17 -d bootVersion=2.7.3 -d type=maven-project -o config-service.zip
```
