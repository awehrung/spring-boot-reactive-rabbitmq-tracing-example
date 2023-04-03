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

[1. Partial, no DB-write] Call the Upstream service, which publishes on the queue for the Downstream service to consume and do nothing with.

```shell
curl http://localhost:12001/test/hello
```

[2. Partial, no Queue] Directly call the Downstream service, which saves the message in a DynamoDB.

```shell
curl http://localhost:12002/write/hello
```

[3. Full chain] Call the Upstream service, which publishes on the queue for the Downstream service to consume and save into a DynamoDB.

```shell
curl http://localhost:12001/write/hello
```

Upstream and Downstream service log using the same Trace-ID, which gets transmitted over RabbitMQ using the B3 or W3C header (depending on config from application properties), and **all log entries** have access to the Trace-ID.

## URLs for debugging

Upstream service: `localhost:12001`

Downstream service: `localhost:12002`

RabbitMQ admin overview: http://localhost:15672/ (User: `rabbitmq`, Password: `rabbitmq`)

Localstack admin overview: https://app.localstack.cloud/

## Details and explanation

Comments starting with `!!!` have been added in code with explanation of the critical steps.

Main learnings:
* Enable automatic context propagation wherever possible by adding `Hooks.enableAutomaticContextPropagation()` to the main method
* Enable observation for the RabbitMQ, which is disabled by default (see examples in code)
* Add `.contextWrite(Function.identity())` just after creating a `Mono` from a non-reactive source
* Add `.contextCapture()` at the very end of the reactive pipeline in some obscure cases
