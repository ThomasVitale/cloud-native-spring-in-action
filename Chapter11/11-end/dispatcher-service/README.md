# Dispatcher Service

## Useful Commands

| Gradle Command	   | Description            |
|:--------------------:|:----------------------:|
| `./gradlew bootRun`  | Run the application.   |
| `./gradlew build`    | Build the application. |
| `./gradlew test`     | Run tests.

After building the application, you can also run it from the Java CLI:

```bash
java -jar build/libs/dispatcher-service-0.0.1-SNAPSHOT.jar
```

## Running a RabbitMQ broker (Docker)

Run RabbitMQ as a Docker container:

```bash
docker run --name polar-rabbitmq \
  -e RABBITMQ_DEFAULT_USER=user \
  -e RABBITMQ_DEFAULT_PASS=password \
  -p 5672:5672 \
  -p 15672:15672 \
  -d rabbitmq:3-management
```

### Container Commands

| Docker Command	              | Description       |
|:-------------------------------:|:-----------------:|
| `docker stop polar-rabbitmq`   | Stop container.   |
| `docker start polar-rabbitmq`  | Start container.  |
| `docker remove polar-rabbitmq` | Remove container. |

## Running a RabbitMQ broker (Kubernetes)

Run RabbitMQ as a Helm chart:

```bash
helm repo add bitnami https://charts.bitnami.com/bitnami
```

```bash
helm install polar-rabbitmq bitnami/rabbitmq \
  --set auth.username=user \
  --set auth.password=password \
  --set fullnameOverride=polar-rabbitmq \
  --set clustering.enabled=false \
  --set persistence.enabled=false
```
