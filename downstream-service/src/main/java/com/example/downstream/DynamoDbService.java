package com.example.downstream;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemResponse;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class DynamoDbService {
    private final DynamoDbAsyncClient dynamoDbAsyncClient;
    private final DynamoDbConfigProperties dynamoDbConfigProperties;

    public Mono<UUID> saveMessage(String message) {
        UUID id = UUID.randomUUID();

        return Mono.fromFuture(this.putItem(id, message))
                .contextWrite(Function.identity())
                .doOnNext(response -> log.info("Wrote item to DynamoDB {}/{}", id, message))
                .thenReturn(id);
    }

    private CompletableFuture<PutItemResponse> putItem(UUID id, String message) {
        Map<String, AttributeValue> item = Map.of(
                "id", AttributeValue.fromS(id.toString()),
                "message", AttributeValue.fromS(message)
        );

        log.info("Writing item to DynamoDB: {}/{}", id, message);

        return dynamoDbAsyncClient.putItem(
                PutItemRequest.builder()
                        .tableName(dynamoDbConfigProperties.getTableName())
                        .item(item)
                        .build()
        );
    }
}
