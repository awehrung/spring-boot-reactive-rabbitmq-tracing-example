package com.example.downstream;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.ReactiveHealthIndicator;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.DescribeTableResponse;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Log4j2
@Component
public class DynamoDbHealthIndicator implements ReactiveHealthIndicator {

    private final DynamoDbAsyncClient dynamoDbAsyncClient;
    private final DynamoDbConfigProperties dynamoDbConfigProperties;

    @Override
    public Mono<Health> health() {
        CompletableFuture<DescribeTableResponse> responseFuture = dynamoDbAsyncClient.describeTable(
                b -> b.tableName(dynamoDbConfigProperties.getTableName()));

        return Mono.fromFuture(responseFuture)
                .doOnNext(response -> log.info("Successfully pinged DynamoDb"))
                .doOnError(throwable -> log.warn("Error in DynamoDb health-check"))
                .map(describeTableResponse -> Health.up().build())
                .onErrorResume(ex -> Mono.just(Health.down().withException(ex).build()));
    }
}
