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
