# Config Service

## Useful Commands

| Gradle Command	   | Description            |
|:--------------------:|:----------------------:|
| `./gradlew bootRun`  | Run the application.   |
| `./gradlew build`    | Build the application. |
| `./gradlew test`     | Run tests.              |

After building the application, you can also run it from the Java CLI:

```bash
java -jar build/libs/config-service-0.0.1-SNAPSHOT.jar
```

## Container tasks

Run Config Service as a container

```bash
docker run --name config-service -p 8888:8888 polarbookshop/config-service:0.0.1-SNAPSHOT
```

### Container Commands

| Docker Command	              | Description       |
|:-------------------------------:|:-----------------:|
| `docker stop config-service`   | Stop container.   |
| `docker start config-service`  | Start container.  |
| `docker remove config-service` | Remove container. |
