package com.example.upstream;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Log4j2
@RequiredArgsConstructor
public class MyController {
    private final RabbitPublisher rabbitPublisher;

    @GetMapping("/test")
    public Mono<ResponseEntity<String>> myEndpoint() {
        log.info("Entering controller");

        return Mono.just("hello")
                .doOnNext(rabbitPublisher::publishMessage)
                .doOnNext(message -> log.info("Message published: {}", message))
                .map(ResponseEntity::ok);
    }
}
