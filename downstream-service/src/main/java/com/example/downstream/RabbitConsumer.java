package com.example.downstream;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Log4j2
@RequiredArgsConstructor
@RabbitListener(queues = "${rabbitmq.queue}")
public class RabbitConsumer {
    @RabbitHandler
    public void consumeMessage(String message, MessageProperties messageProperties) {
        log.info("Consuming message: {}", message);
        log.info("Message properties: {}", messageProperties);
        
        Mono.just(message)
                .doOnNext(m -> log.info("Consumed message: {}", m))
                .subscribe();
    }

    @RabbitHandler(isDefault = true)
    public void consumeDefault(Message message) {
        throw new IllegalArgumentException("Unsupported message");
    }
}
