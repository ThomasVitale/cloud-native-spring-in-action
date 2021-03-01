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
docker run --name polardb-redis -p 6379:6379 -d redis:6
```

### Container Commands

| Docker Command	              | Description       |
|:-------------------------------:|:-----------------:|
| `docker stop polardb-redis`   | Stop container.   |
| `docker start polardb-redis`  | Start container.  |
| `docker remove polardb-redis` | Remove container. |

## Running a Redis Database (Kubernetes)

Run Redis as a Helm chart:

```bash
helm repo add bitnami https://charts.bitnami.com/bitnami
```

```bash
helm install polardb-redis bitnami/redis \
  --set cluster.enabled=false \
  --set image.tag=6 \
  --set password=admin
```
