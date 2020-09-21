# Config Service

Initialize a Spring project with your favorite method, for example from Spring Initialzr (https://start.spring.io/) or your IDE.
During the initialization, you need to select the "Spring Web" and "Config Server" dependencies.

If you prefer, you can initialize the project with this command:

```bash
curl https://start.spring.io/starter.tgz -d groupId=com.polarbookshop -d artifactId=config-service -d name=config-service -d packageName=com.polarbookshop.configservice -d dependencies=web,cloud-config-server -d javaVersion=11 -d type=gradle-project -o config-service.zip
```

For using Maven instead of Gradle:

```bash
curl https://start.spring.io/starter.tgz --d groupId=com.polarbookshop -d artifactId=config-service -d name=config-service -d packageName=com.polarbookshop.configservice -d dependencies=web,cloud-config-server -d javaVersion=11 -o config-service.zip
```
