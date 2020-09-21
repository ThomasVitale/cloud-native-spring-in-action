# Greeting Service

## Package the application

```bash
./gradlew bootJar
```

## Run the application

```bash
java -jar build/libs/greeting-service-0.0.1-SNAPSHOT.jar
```

## Run the application + override configuration from CLI arguments

```bash
java -jar build/libs/greeting-service-0.0.1-SNAPSHOT.jar --greetings.message="Welcome to the Arctic from CLI"
```

## Run the application + override configuration from JVM system properties

```bash
java -Dgreetings.message="Welcome to the Arctic from JVM" -jar build/libs/greeting-service-0.0.1-SNAPSHOT.jar
```

## Run the application + override configuration from environment variables

```bash
export GREETINGS_MESSAGE="Welcome to the Arctic from ENV"
java -jar build/libs/greeting-service-0.0.1-SNAPSHOT.jar
```