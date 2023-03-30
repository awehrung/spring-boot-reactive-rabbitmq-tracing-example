package com.example.downstream;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Log4j2
@RequiredArgsConstructor
public class MyController {
    private final DynamoDbService dynamoDbService;

    @GetMapping("/write/{message}")
    public Mono<ResponseEntity<String>> myWriteEndpoint(@PathVariable String message) {
        log.info("Entering endpoint write");

        return dynamoDbService.saveMessage(message)
                .doOnNext(id -> log.info("Message saved: {}/{}", id, message))
                .map(id -> ResponseEntity.ok(message));
    }
}
