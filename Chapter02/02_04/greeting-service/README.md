# Greeting Service

## Application tasks

### Build and run the application

```bash
./gradlew bootRun
```

### Build the application

```bash
./gradlew build
```

### Run the tests

```bash
./gradlew test
```

### Package the application as JAR

```bash
./gradlew bootJar
```

## Docker tasks

### Build Docker image

```bash
./gradlew bootBuildImage
```

### Run application as Docker container

```bash
docker run --name greeting-service -p 8080:8080 arcticgreetings/greeting-service:0.0.1-SNAPSHOT
```

### Stop application Docker container

```bash
docker stop greeting-service
```

### Remove application Docker container

```bash
docker rm greeting-service
```

### Remove application Docker image

```bash
docker rmi arcticgreetings/greeting-service:0.0.1-SNAPSHOT
```

## Kubernetes tasks

### Create Deployment for application container

```bash
kubectl create deployment greeting-service-deployment --image=arcticgreetings/greeting-service:0.0.1-SNAPSHOT
```

### Create Service for application Deployment

```bash
kubectl expose deployment greeting-service-deployment --type=ClusterIP --name=my-service --port=8080
```

### Port forwarding from localhost to Kubernetes cluster

```bash
kubectl port-forward service/my-service 8000:8080
```

### Delete Deployment for application container

```bash
kubectl delete deployment greeting-service-deployment
```

### Delete Service for application container

```bash
kubectl delete svc my-service
```
