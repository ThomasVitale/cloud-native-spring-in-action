# Catalog Service

Initialize a Spring project for the Catalog Service application with your favorite method, for example from [Spring Initialzr](https://start.spring.io/)
or your IDE.

## Spring Initializr - REST API

If you prefer using the REST API offered by Spring Initializr, use this command to initialize a project for the Catalog Service:

```bash
curl https://start.spring.io/starter.tgz -d groupId=com.polarbookshop -d artifactId=catalog-service -d name=catalog-service -d packageName=com.polarbookshop.catalogservice -d dependencies=web -d javaVersion=11 -d bootVersion=2.3.2 -d type=gradle-project -o catalog-service.zip
```

For using Maven instead of Gradle:

```bash
curl https://start.spring.io/starter.tgz -d groupId=com.polarbookshop -d artifactId=catalog-service -d name=catalog-service -d packageName=com.polarbookshop.catalogservice -d dependencies=web -d javaVersion=11 -d bootVersion=2.3.2 -o catalog-service.zip
```

## Spring Initializr - App

From the Spring Initializr application, choose the following:

* _Project_: Gradle (or Maven)
* _Spring Boot_: 2.3.2
* _Group_: `com.polarbookshop`
* _Artifact_: `catalog-service`
* _Name_: catalog-service
* _Package name_: `com.polarbookshop.catalogservice`
* _Java version_: 11
* _Dependencies_: Spring Web
