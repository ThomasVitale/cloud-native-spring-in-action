# 🍃 Cloud Native Spring in Action

<a href="https://www.manning.com/books/cloud-native-spring-in-action"><img src="/book-cover.jpg" alt="The book cover of 'Cloud Native Spring in Action' by Thomas Vitale" align="left" height="200px" /></a>

This repository contains the source code accompanying the book [Cloud Native Spring in Action - With Spring Boot and Kubernetes](https://www.manning.com/books/cloud-native-spring-in-action) written by [Thomas Vitale](https://www.thomasvitale.com/) and published by Manning Publications.

There is a folder for each chapter, for which both an _initial_ and _final_ versions are available. For example, for chapter 4, you can use `Chapter04/04-begin` as a starting point to follow along with the examples in the chapter and `Chapter04/04-end` to check the code as it looks like at the end.

The book uses Spring Boot 2.7.3. You can find the same examples upgraded to the latest [2.7.x](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/sb-2-main) and [3.x](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/sb-3-main)
versions of Spring Boot in dedicated branches. Feel free to use **Spring Boot 3** while reading the book. Only a few minor changes are necessary
to the code samples and they are documented [here](https://github.com/ThomasVitale/cloud-native-spring-in-action/blob/sb-3-main/README.md).

## Prerequisites

Chapter after chapter, you'll build, containerize, and deploy cloud native applications. Along the journey, you will need the following software installed.

* Java 17+
    * OpenJDK: [Eclipse Temurin](https://adoptium.net)
    * GraalVM: [GraalVM](https://www.graalvm.org)
    * JDK Management: [SDKMAN](https://sdkman.io)
* Docker 20.10+
    * [Docker for Linux](https://docs.docker.com/engine/install/ubuntu/)
    * [Docker Desktop for Mac](https://www.docker.com/products/docker-desktop)
    * [Docker Desktop for Windows](https://www.docker.com/products/docker-desktop)
* Kubernetes 1.24+
    * [kubectl](https://kubernetes.io/docs/tasks/tools/install-kubectl/)
    * [minikube](https://minikube.sigs.k8s.io/docs/)
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

## Guides, Tools and Tips

* [Configuring IntelliJ IDEA](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Guides/configuring-intellij-idea.md)
* [Configuring Visual Studio Code](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Guides/configuring-visual-studio-code.md)
* [Observability setup on Kubernetes](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Guides/grafana-observability-stack)
* [Replacing Kubeval with Kubeconform](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Guides/replacing-kubeval-with-kubeconform.md)
* [Setting up a Kubernetes cluster for Polar Bookshop on Azure](#)
* [Setting up a Kubernetes cluster for Polar Bookshop on DigitalOcean](#)
* [Working with macOS on Apple Silicon](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Guides/working-with-macos-on-apple-silicon.md)
* [Working with Windows](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Guides/working-with-windows.md)

## Source Code by Chapter

| Chapter | Starting point | Intermediate version | Final version |
|---------|----------------|----------------------|---------------|
| 1. Introduction to cloud native | - | - | - |
| 2. Cloud native patterns and technologies | [02-begin](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter02/02-begin) | - | [02-end](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter02/02-end) |
| 3. Getting started with cloud native development | [03-begin](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter03/03-begin) | - | [03-end](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter03/03-end) |
| 4. Externalized configuration management | [04-begin](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter04/04-begin) | - | [04-end](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter04/04-end) |
| 5. Persisting and managing data in the cloud | [05-begin](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter05/05-begin) | [05-intermediate](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter05/05-intermediate) | [05-end](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter05/05-end) |
| 6. Containerizing Spring Boot | [06-begin](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter06/06-begin) | - | [06-end](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter06/06-end) |
| 7. Kubernetes fundamentals for Spring Boot | [07-begin](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter07/07-begin) | - | [07-end](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter07/07-end) |
| 8. Reactive Spring: Resilience and scalability | [08-begin](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter08/08-begin) | - | [08-end](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter08/08-end) |
| 9. API gateway and circuit breakers | [09-begin](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter09/09-begin) | - | [09-end](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter09/09-end) |
| 10. Event-driven applications and functions | [10-begin](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter10/10-begin) | [10-intermediate](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter10/10-intermediate) | [10-end](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter10/10-end) |
| 11. Security: Authentication and SPA | [11-begin](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter11/11-begin) | - | [11-end](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter11/11-end) |
| 12. Security: Authorization and auditing | [12-begin](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter12/12-begin) | - | [12-end](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter12/12-end) |
| 13. Observability and monitoring | [13-begin](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter13/13-begin) | - | [13-end](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter13/13-end) |
| 14. Configuration and secrets management | [14-begin](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter14/14-begin) | - | [14-end](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter14/14-end) |
| 15. Continuous delivery and GitOps | [15-begin](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter15/15-begin) | - | [15-end](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter15/15-end) |
| 16. Serverless, GraalVM and Knative | [16-begin](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter16/16-begin) | - | [10-end](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/Chapter16/16-end) |

## Polar Bookshop

The final project developed throughout the book is available [here](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/PolarBookshop).

You can find the source code for the Angular frontend [here](https://github.com/PolarBookshop/polar-ui/tree/v1).

## Book Forum

Feel free to submit questions, feedback, or errata to the forum dedicated to "Cloud Native Spring in Action": https://livebook.manning.com/book/cloud-native-spring-in-action/.

## Contact the Author

You are very welcome to contact me for questions, feedback, or suggestions. Feel free to reach out to me on [Twitter](https://twitter.com/vitalethomas), [LinkedIn](https://www.linkedin.com/in/vitalethomas), [Mastodon](https://mastodon.online/@thomasvitale), or here on [GitHub](https://github.com/ThomasVitale/).
