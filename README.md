# Spring-Boot-Reactive-RabbitMQ-Tracing-Example

## Build

```shell
mvn clean install
docker-compose build
```

## Run

```shell
docker-compose up -d
docker-compose logs -f downstream-service upstream-service
```

## Test

### Working cases

1. Call the Upstream service, which publishes on the queue for the Downstream service to consume and do nothing with.

```shell
curl http://localhost:12001/test/hello
```

2. Directly call the Downstream service, which saves the message in a DynamoDB.

```shell
curl http://localhost:12002/write/hello
```

Upstream and Downstream service log using the same Trace-ID, which gets transmitted over RabbitMQ using the B3 or W3C header (depending on config from application properties), and **all log entries** have access to the Trace-ID.

### Broken case

Call the Upstream service, which publishes on the queue for the Downstream service to consume and save into a DynamoDB.

```shell
curl http://localhost:12001/write/hello
```

Upstream and Downstream service log using the same Trace-ID, but **it is missing from the logs written after the `Mono` is created from the `DynamoDbService`**.

## URLs for debugging

Upstream service: `localhost:12001`

Downstream service: `localhost:12002`

RabbitMQ admin overview: http://localhost:15672/ (User: `rabbitmq`, Password: `rabbitmq`)

Localstack admin overview: https://app.localstack.cloud/
