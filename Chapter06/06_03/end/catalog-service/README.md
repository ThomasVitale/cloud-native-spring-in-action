# Catalog Service

## Application tasks

**Build the application**

```bash
./gradlew build
```

**Package the application as fat-JAR**

```bash
./gradlew bootJar
```

**Run the application (JAR)**

```bash
java -jar build/libs/catalog-service-0.0.1-SNAPSHOT.jar
```

**Run the application (Boot)**

```bash
./gradlew bootRun
```

**Run tests**

```bash
./gradlew test
```

## Docker tasks (Dockerfile)

**Build the application image from Dockerfile (Gradle)**

```bash
docker build -t <your_docker_username>/catalog-service:0.0.1-SNAPSHOT .
```

**Build the application image from Dockerfile (Maven)**

```bash
docker build --build-arg JAR_FILE=target/*.jar -t <your_docker_username>/catalog-service:0.0.1-SNAPSHOT .
```

**Run application as Docker container**

```bash
docker run -d --name catalog-service -p 9001:9001 --net catalog-network -e SPRING_DATASOURCE_URL=jdbc:postgresql://polardb-catalog:5432/polardb_catalog <your_docker_username>/catalog-service:0.0.1-SNAPSHOT
```

**Remove the application container**

```bash
docker rm -f catalog-service
```

## Docker tasks (Cloud Native Buildpacks)

**Build the application image from Cloud Native Buildpacks**

```bash
./gradlew bootBuildImage
```

**Run application as Docker container**

```bash
docker run -d --name catalog-service -p 9001:9001 --net catalog-network -e SPRING_DATASOURCE_URL=jdbc:postgresql://polardb-catalog:5432/polardb_catalog <your_docker_username>/catalog-service:0.0.1-SNAPSHOT
```

**Remove the application container**

```bash
docker rm -f catalog-service
```

## Docker Compose tasks

**Start containers**

```bash
docker-compose up -d
```

**Remove containers**

```bash
docker-compose down
```
