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
