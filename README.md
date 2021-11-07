# 🍃 Cloud Native Spring in Action

<a href="https://www.manning.com/books/cloud-native-spring-in-action?utm_source=affiliate&utm_medium=affiliate&a_aid=thomas&a_bid=3dda43a8"><img src="/book-cover.jpeg" alt="The book cover of 'Cloud Native Spring in Action' by Thomas Vitale" align="left" height="200px" /></a>

This repository contains the source code accompanying the book [Cloud Native Spring in Action - With Spring Boot and Kubernetes](https://www.manning.com/books/cloud-native-spring-in-action?utm_source=affiliate&utm_medium=affiliate&a_aid=thomas&a_bid=3dda43a8) written by [Thomas Vitale](https://www.thomasvitale.com/) and published by Manning Publications. It's currently available through the Manning Early Access Program (MEAP).

There is a folder for each chapter, for which both an _initial_ and _final_ versions are available. For example, for chapter 4, you can use `Chapter04/04-begin` as a starting point to follow along with the examples in the chapter and `Chapter04/04-end` to check the code as it looks like at the end.

## Prerequisites

Chapter after chapter, you'll build, containerize, and deploy cloud native applications. Along the journey, you will need the following software installed.

* Java 17
    * OpenJDK: [Eclipse Temurin](https://adoptium.net)
    * GraalVM: [GraalVM](https://www.graalvm.org)
    * JDK Management: [SDKMAN](https://sdkman.io)
* Docker 20.10+
    * [Docker for Linux](https://docs.docker.com/engine/install/ubuntu/)
    * [Docker Desktop for Mac](https://www.docker.com/products/docker-desktop)
    * [Docker Desktop for Windows](https://www.docker.com/products/docker-desktop)
* Kubernetes 1.21+
    * [kubectl](https://kubernetes.io/docs/tasks/tools/install-kubectl/)
    * [kind](https://kind.sigs.k8s.io)
* Other
    * [HTTPie](https://httpie.org/)

## Gradle and Maven

The code samples in the book use Gradle as the build tool. Should you prefer Maven, here's a table mapping Gradle commands to Maven so that you can easily follow along.

Gradle | Maven
------ | ------
`./gradlew clean` | `./mvnw clean`
`./gradlew build` | `./mvnw install`
`./gradlew test` | `./mvnw test`
`./gradlew bootJar` | `./mvnw spring-boot:repackage`
`./gradlew bootRun` | `./mvnw spring-boot:run`
`./gradlew bootBuildImage` | `./mvnw spring-boot:build-image`

## 1. Introduction to Cloud Native

## 2. Cloud native patterns and technologies

* [Starting point](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter02/02-begin)
* [Final version](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter02/02-end)

## 3. Getting started with cloud native development

* [Starting point](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter03/03-begin)
* [Final version](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter03/03-end)

## 4. Externalized configuration management

* [Starting point](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter04/04-begin)
* [Final version](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter04/04-end)

## 5. Persisting and managing data in the cloud

* [Starting point](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter05/05-begin)
* [Final version](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter05/05-end)

## 6. Containerizing Spring Boot

* [Starting point](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter06/06-begin)
* [Final version](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter06/06-end)

## 7. Kubernetes fundamentals for Spring Boot

* [Starting point](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter07/07-begin)
* [Final version](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter07/07-end)

## 8. Reactive Spring: Resilience and scalability

* [Starting point](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter08/08-begin)
* [Final version](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter08/08-end)

## 9. API gateway and circuit breakers

* [Starting point](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter09/09-begin)
* [Final version](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter09/09-end)

## 10. Event-driven applications and functions

* [Starting point](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter10/10-begin)
* [Final version](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter10/10-end)

## 11. Security: Authentication and SPA

* [Starting point](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter11/11-begin)
* [Final version](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter11/11-end)

## 12. Security: Authorization and Auditing

* [Starting point](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter12/12-begin)
* [Final version](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter12/12-end)

## 13. Configuration and secrets management

* [Starting point](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter13/13-begin)
* [Final version](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter13/13-end)

## 14. Observability and monitoring

* [Starting point](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter14/14-begin)
* [Final version](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter14/14-end)

## Book forum

Feel free to submit questions, feedback, or errata to the forum dedicated to "Cloud Native Spring in Action": https://livebook.manning.com/book/cloud-native-spring-in-action/.

## Contact the author

You are very welcome to contact me for questions, feedback, or suggestions. Feel free to reach out to me on [Twitter](https://twitter.com/vitalethomas), [LinkedIn](https://www.linkedin.com/in/vitalethomas), or here on [GitHub](https://github.com/ThomasVitale/).
