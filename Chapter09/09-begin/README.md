# Chapter 09 - Begin

In this chapter, you'll build an Edge Service application. You can initialize a Spring Boot project using your
favorite method. This guide describes how to do that with the Spring Initializr application and with its REST API.
Either way, you'll get a zip archive that you can extract and import in your IDE.

> **Warning**
> Spring Boot 2.7.x is not available anymore from Spring Initializr. You can either use Spring Boot 3.x and refer to the [sb-3-main](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/sb-3-main) branch of this repository for further instructions, or check the end folder for this chapter and keep using Spring Boot 2.7.x.

## Initialize Edge Service with Spring Initializr - Website

From the [Spring Initialzr](https://start.spring.io/) website, choose the following:

* _Project_: Gradle (or Maven)
* _Spring Boot_: 2.7.18
* _Group_: `com.polarbookshop`
* _Artifact_: `edge-service`
* _Name_: edge-service
* _Package name_: `com.polarbookshop.edgeservice`
* _Java version_: 17
* _Dependencies_: Edge Server

Then, click "Generate" to download the `edge-service.zip` archive containing the project.

## Initialize Edge Service with Spring Initializr - REST API

If you prefer using the REST API offered by Spring Initializr, run the following command to initialize a project with Gradle as the build tool:

```bash
curl https://start.spring.io/starter.zip -d groupId=com.polarbookshop -d artifactId=edge-service -d name=edge-service -d packageName=com.polarbookshop.edgeservice -d dependencies=cloud-gateway -d javaVersion=17 -d bootVersion=2.7.18 -d type=gradle-project -o edge-service.zip
```

For using Maven instead of Gradle:

```bash
curl https://start.spring.io/starter.zip -d groupId=com.polarbookshop -d artifactId=edge-service -d name=edge-service -d packageName=com.polarbookshop.edgeservice -d dependencies=cloud-gateway -d javaVersion=17 -d bootVersion=2.7.18 -d type=maven-project -o edge-service.zip
```
