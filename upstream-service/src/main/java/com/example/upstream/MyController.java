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
    public Mono<ResponseEntity<String>> myEndpoint(@PathVariable String message) {
        log.info("Entering controller");

        return Mono.just(message)
                .doOnNext(rabbitPublisher::publishMessage)
                .doOnNext(m -> log.info("Message published: {}", m))
                .map(ResponseEntity::ok);
    }
}
