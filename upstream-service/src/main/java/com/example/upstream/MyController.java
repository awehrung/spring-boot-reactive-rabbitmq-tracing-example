package com.example.upstream;

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
    private final RabbitPublisher rabbitPublisher;

    @GetMapping("/test/{message}")
    public Mono<ResponseEntity<String>> myTestEndpoint(@PathVariable String message) {
        log.info("Entering endpoint test");

        return this.publish("TEST:%s".formatted(message)).map(ResponseEntity::ok);
    }

    @GetMapping("/write/{message}")
    public Mono<ResponseEntity<String>> myWriteEndpoint(@PathVariable String message) {
        log.info("Entering endpoint write");

        return this.publish("WRITE:%s".formatted(message)).map(ResponseEntity::ok);
    }

    private Mono<String> publish(String message) {
        return Mono.just(message)
                .doOnNext(rabbitPublisher::publishMessage)
                .doOnNext(m -> log.info("Message published: {}", m));
    }
}
