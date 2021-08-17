# Edge Service

## Useful Commands

| Gradle Command	   | Description            |
|:--------------------:|:----------------------:|
| `./gradlew bootRun`  | Run the application.   |
| `./gradlew build`    | Build the application. |
| `./gradlew test`     | Run tests.

After building the application, you can also run it from the Java CLI:

```bash
java -jar build/libs/edge-service-0.0.1-SNAPSHOT.jar
```

## Running a Redis Database (Docker)

Run Redis as a Docker container:

```bash
docker run --name polar-redis -p 6379:6379 -d redis:6
```

### Container Commands

| Docker Command	              | Description       |
|:-------------------------------:|:-----------------:|
| `docker stop polar-redis`   | Stop container.   |
| `docker start polar-redis`  | Start container.  |
| `docker remove polar-redis` | Remove container. |

## Running a Redis Database (Kubernetes)

Run Redis as a Helm chart:

```bash
helm repo add bitnami https://charts.bitnami.com/bitnami
```

```bash
helm install polardb-redis bitnami/redis \
  --set fullnameOverride=polar-redis
  --set architecture=standalone
  --set auth.password=admin \
  --set master.persistence.enabled=false
```
