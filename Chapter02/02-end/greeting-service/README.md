# Greeting Service

## Useful Commands

| Gradle Command	         | Description                   |
|:--------------------------:|:-----------------------------:|
| `./gradlew bootRun`        | Run the application.          |
| `./gradlew build`          | Build the application.        |
| `./gradlew test`           | Run tests.                    |
| `./gradlew bootBuildImage` | Containerize the application. |

After building the application, you can also run it from the Java CLI:

```bash
java -jar build/libs/greeting-service-0.0.1-SNAPSHOT.jar
```

## Container tasks

Run Greeting Service as a container

```bash
docker run --name greeting-service -p 8080:8080 greeting-service:0.0.1-SNAPSHOT
```

### Container Commands

| Docker Command	              | Description       |
|:-------------------------------:|:-----------------:|
| `docker stop greeting-service`   | Stop container.   |
| `docker start greeting-service`  | Start container.  |
| `docker remove greeting-service` | Remove container. |

## Kubernetes tasks

### Create Deployment for application container

```bash
kubectl create deployment greeting-service --image=greeting-service:0.0.1-SNAPSHOT
```

### Create Service for application Deployment

```bash
kubectl expose deployment greeting-service --type=ClusterIP --name=greeting-service --port=8080
```

### Port forwarding from localhost to Kubernetes cluster

```bash
kubectl port-forward service/greeting-service 8000:8080
```

### Delete Deployment for application container

```bash
kubectl delete deployment greeting-service
```

### Delete Service for application container

```bash
kubectl delete svc greeting-service
```
