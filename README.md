# spring-boot-reactive-rabbitmq-tracing-example

## Build

```shell
mvn clean install
docker-compose build
```

## Run

```shell
docker-compose up -d
docker-compose logs -f
```

## Test

```shell
curl http://localhost:12001/test/hello
```

Upstream and Downstream service log using the same Trace-ID, which gets transmitted over RabbitMQ using the B3 or W3C header (depending on config from application properties).

## URLs

Upstream-service: `localhost:12001`

Downstream-service: `localhost:12002`

RabbitMQ admin overview: http://localhost:15672/ (User: `rabbitmq`, Password: `rabbitmq`)
