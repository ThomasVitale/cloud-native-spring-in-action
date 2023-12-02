# Chapter 16 - Begin

In this chapter, you'll build Quote Service and Quote Function applications. You can initialize a Spring Boot project using your
favorite method. This guide describes how to do that with the Spring Initializr application and with its REST API.
Either way, you'll get a zip archive that you can extract and import in your IDE.

> **Warning**
> Spring Boot 2.7.x is not available anymore from Spring Initializr. You can either use Spring Boot 3.x and refer to the [sb-3-main](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/sb-3-main) branch of this repository for further instructions, or check the end folder for this chapter and keep using Spring Boot 2.7.x.

## Initialize Quote Service with Spring Initializr - Website

From the [Spring Initialzr](https://start.spring.io/) website, choose the following:

* _Project_: Gradle (or Maven)
* _Spring Boot_: 2.7.18
* _Group_: `com.polarbookshop`
* _Artifact_: `quote-service`
* _Name_: quote-service
* _Package name_: `com.polarbookshop.quoteservice`
* _Java version_: 17
* _Dependencies_: Spring Web Reactive, Spring Native

Then, click "Generate" to download the `quote-service.zip` archive containing the project.

## Initialize Quote Function with Spring Initializr - Website

From the [Spring Initialzr](https://start.spring.io/) website, choose the following:

* _Project_: Gradle (or Maven)
* _Spring Boot_: 2.7.18
* _Group_: `com.polarbookshop`
* _Artifact_: `quote-function`
* _Name_: quote-function
* _Package name_: `com.polarbookshop.quotefunction`
* _Java version_: 17
* _Dependencies_: Spring Web Reactive, Spring Native, Function

Then, click "Generate" to download the `quote-function.zip` archive containing the project.
