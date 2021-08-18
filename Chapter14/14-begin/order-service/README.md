# Order Service

## REST API

| Endpoint	      | Method   | Req. body    | Status | Resp. body     | Description    		   	         |
|:---------------:|:--------:|:------------:|:------:|:--------------:|:---------------------------------|
| `/orders`       | `GET`    |              | 200    | Orders         | Get all the orders.              |
| `/orders`       | `POST`   | OrderRequest | 200    | Order          | Submit a new order.              |

## Useful Commands

| Gradle Command	   | Description            |
|:--------------------:|:----------------------:|
| `./gradlew bootRun`  | Run the application.   |
| `./gradlew build`    | Build the application. |
| `./gradlew test`     | Run tests.

After building the application, you can also run it from the Java CLI:

```bash
java -jar build/libs/order-service-0.0.1-SNAPSHOT.jar
```

## Running a PostgreSQL Database (Docker)

Run PostgreSQL as a Docker container:

```bash
docker run --name polar-postgres-order -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=admin -e POSTGRES_DB=polardb_order -p 5433:5432 -d postgres:13
```

### Container Commands

| Docker Command	              | Description       |
|:-------------------------------:|:-----------------:|
| `docker stop polar-postgres-order`   | Stop container.   |
| `docker start polar-postgres-order`  | Start container.  |
| `docker remove polar-postgres-order` | Remove container. |

### Database Commands

Start an interactive PSQL console:

```bash
docker exec -it polar-postgres-order psql -U admin -d polardb_order
```

| PSQL Command	             | Description                    |
|:--------------------------:|:------------------------------:|
| `\list`                    | List all databases.            |
| `\connect polardb_order`   | Connect to specific database.  |
| `\dt`                      | List all tables.               |
| `\quit`                    | Quit interactive psql console. |

## Running a PostgreSQL Database (Kubernetes)

Run PostgreSQL as a Helm chart:

```bash
helm repo add bitnami https://charts.bitnami.com/bitnami
```

```bash
helm install polar-postgres-order bitnami/postgresql \
  --set fullnameOverride=polar-postgres-order \
  --set postgresqlUsername=admin \
  --set postgresqlPassword=admin \
  --set postgresqlDatabase=polardb_order \
  --set persistence.enabled=false \
  --set service.port=5433
```
