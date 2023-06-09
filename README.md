# 🍃 Cloud Native Spring in Action

<a href="https://www.manning.com/books/cloud-native-spring-in-action"><img src="/book-cover.jpg" alt="The book cover of 'Cloud Native Spring in Action' by Thomas Vitale" align="left" height="200px" /></a>

This repository contains the source code accompanying the book [Cloud Native Spring in Action - With Spring Boot and Kubernetes](https://www.manning.com/books/cloud-native-spring-in-action) written by [Thomas Vitale](https://www.thomasvitale.com/) and published by Manning Publications.

There is a folder for each chapter, for which both an _initial_ and _final_ versions are available. For example, for chapter 4, you can use `Chapter04/04-begin` as a starting point to follow along with the examples in the chapter and `Chapter04/04-end` to check the code as it looks like at the end.

## Changes with Spring Boot 3

This branch contains the source code accompanying the book "Cloud Native Spring in Action" upgraded to Spring Boot 3. Besides the new dependency version, there are a few minor changes compared to what it's included in the book.

### Jakarta EE

Spring Boot 3 is based on Jakarta EE 9, which comes with a change in the package naming strategy from `javax.*` to `jakarta.*`.
The annotations from the Java Validation API introduced in chapter 3 should change from `javax.validation.*` to `jakarta.validation.*`
(Catalog Service and Order Service).

### Spring Data Redis

Chapter 9 introduces Spring Data Redis. In Spring Boot 3, all configuration properties related to Redis changed naming strategy from
`spring.redis.*` to `spring.data.redis.*` (Edge Service).

### Spring Session

The `spring.session.store-type=redis` configuration property used in chapter 9 (Edge Service) is not necessary anymore. Spring Session will detect
that the application is integrated with Redis and it will use it to store the session data automatically.

### Spring Cloud Stream

The test binder used in chapter 10 is now provided via a dedicated dependency `org.springframework.cloud:spring-cloud-stream-test-binder`
and it comes with its autoconfiguration. Therefore, it's not necessary anymore to explicitly add the `@Import(TestChannelBinderConfiguration.class)`
annotation on a test class.

### Spring Security

#### CSRF

The latest version of Spring Security provides a more robust protection against CSRF attacks. In order to make the new functionality work,
we need to update the configuration in Edge Service explained in chapter 11 to combine the higher protection with support for SPAs
using a cookie-based strategy to read CSRF tokens.

```java
@Configuration
public class SecurityConfig {

	@Bean
	SecurityWebFilterChain securityFilterChain(...) {
		return http
			...
			.csrf(csrf -> csrf
				.csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse())
				.csrfTokenRequestHandler(new XorServerCsrfTokenRequestAttributeHandler()::handle)) <1>
			.build();
	}
}
```

<1> This is the new line to add.

#### Servlet

In chapter 12, we use `mvcMatcher()` to tell Spring Security which endpoints of Catalog Service should be protected.
We need to replace the `mvcMatcher()` expressions with `requestMatcher()` because the former has been removed.

```java
@Configuration
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(...) throws Exception {
		return http
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/actuator/**").permitAll() <2>
				.requestMatchers(HttpMethod.GET, "/", "/books/**").permitAll() <2>
				...
			.build();
	}
}
```

<2> These are the lines with changes.

### GraalVM Support

Chapter 16 covers Spring Native. Starting from Spring Boot 3 and Spring Framework 6, support for GraalVM native images is part of the core
framework. From the Spring Initializr, you would choose "GraalVM Native Support" instead of "Spring Native (Experimental)".
In practice, the only necessary code change is in the Gradle configuration. Instead of having the `org.springframework.experimental.aot` plugin,
you would have the `org.graalvm.buildtools.native` plugin. This is provided for you when you use Spring Initializr. Everything else stays the same
as it's explained in the book, including all the commands to build the application as a native executable.

## Prerequisites

Chapter after chapter, you'll build, containerize, and deploy cloud native applications. Along the journey, you will need the following
software installed.

* Java 17+
    * OpenJDK: [Eclipse Temurin](https://adoptium.net)
    * GraalVM: [GraalVM](https://www.graalvm.org)
    * JDK Management: [SDKMAN](https://sdkman.io)
* Docker 20.10+
    * [Docker for Linux](https://docs.docker.com/engine/install/ubuntu/)
    * [Docker Desktop for Mac](https://www.docker.com/products/docker-desktop)
    * [Docker Desktop for Windows](https://www.docker.com/products/docker-desktop)
* Kubernetes 1.25+
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

* [Configuring IntelliJ IDEA](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/sb-3-main/Guides/configuring-intellij-idea.md)
* [Configuring Visual Studio Code](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/sb-3-main/Guides/configuring-visual-studio-code.md)
* [Observability setup on Kubernetes](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/sb-3-main/Guides/grafana-observability-stack)
* [Replacing Kubeval with Kubeconform](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/sb-3-main/Guides/replacing-kubeval-with-kubeconform.md)
* [Setting up a Kubernetes cluster for Polar Bookshop on Azure](#)
* [Setting up a Kubernetes cluster for Polar Bookshop on DigitalOcean](#)
* [Working with macOS on Apple Silicon](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/sb-3-main/Guides/working-with-macos-on-apple-silicon.md)
* [Working with Windows](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/sb-3-main/Guides/working-with-windows.md)

## Source Code by Chapter

| Chapter | Starting point | Intermediate version | Final version |
|---------|----------------|----------------------|---------------|
| 1. Introduction to cloud native | - | - | - |
| 2. Cloud native patterns and technologies | [02-begin](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/sb-3-main/Chapter02/02-begin) | - | [02-end](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/sb-3-main/Chapter02/02-end) |
| 3. Getting started with cloud native development | [03-begin](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/sb-3-main/Chapter03/03-begin) | - | [03-end](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/sb-3-main/Chapter03/03-end) |
| 4. Externalized configuration management | [04-begin](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/sb-3-main/Chapter04/04-begin) | - | [04-end](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/sb-3-main/Chapter04/04-end) |
| 5. Persisting and managing data in the cloud | [05-begin](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/sb-3-main/Chapter05/05-begin) | [05-intermediate](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/sb-3-main/Chapter05/05-intermediate) | [05-end](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/sb-3-main/Chapter05/05-end) |
| 6. Containerizing Spring Boot | [06-begin](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/sb-3-main/Chapter06/06-begin) | - | [06-end](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/sb-3-main/Chapter06/06-end) |
| 7. Kubernetes fundamentals for Spring Boot | [07-begin](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/sb-3-main/Chapter07/07-begin) | - | [07-end](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/sb-3-main/Chapter07/07-end) |
| 8. Reactive Spring: Resilience and scalability | [08-begin](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/sb-3-main/Chapter08/08-begin) | - | [08-end](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/sb-3-main/Chapter08/08-end) |
| 9. API gateway and circuit breakers | [09-begin](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/sb-3-main/Chapter09/09-begin) | - | [09-end](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/sb-3-main/Chapter09/09-end) |
| 10. Event-driven applications and functions | [10-begin](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/sb-3-main/Chapter10/10-begin) | [10-intermediate](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/sb-3-main/Chapter10/10-intermediate) | [10-end](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/sb-3-main/Chapter10/10-end) |
| 11. Security: Authentication and SPA | [11-begin](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/sb-3-main/Chapter11/11-begin) | - | [11-end](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/sb-3-main/Chapter11/11-end) |
| 12. Security: Authorization and auditing | [12-begin](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/sb-3-main/Chapter12/12-begin) | - | [12-end](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/sb-3-main/Chapter12/12-end) |
| 13. Observability and monitoring | [13-begin](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/sb-3-main/Chapter13/13-begin) | - | [13-end](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/sb-3-main/Chapter13/13-end) |
| 14. Configuration and secrets management | [14-begin](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/sb-3-main/Chapter14/14-begin) | - | [14-end](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/sb-3-main/Chapter14/14-end) |
| 15. Continuous delivery and GitOps | [15-begin](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/sb-3-main/Chapter15/15-begin) | - | [15-end](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/sb-3-main/Chapter15/15-end) |
| 16. Serverless, GraalVM and Knative | [16-begin](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/sb-3-main/Chapter16/16-begin) | - | [10-end](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/sb-3-main/Chapter16/16-end) |

## Polar Bookshop

The final project developed throughout the book is available [here](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/sb-3-main/PolarBookshop).

You can find the source code for the Angular frontend [here](https://github.com/PolarBookshop/polar-ui/tree/v1).

## Book Forum

Feel free to submit questions, feedback, or errata to the forum dedicated to "Cloud Native Spring in Action": https://livebook.manning.com/book/cloud-native-spring-in-action/.

## Contact the Author

You are very welcome to contact me for questions, feedback, or suggestions. Feel free to reach out to me on [Twitter](https://twitter.com/vitalethomas), [LinkedIn](https://www.linkedin.com/in/vitalethomas), [Mastodon](https://mastodon.online/@thomasvitale), or here on [GitHub](https://github.com/ThomasVitale/).
