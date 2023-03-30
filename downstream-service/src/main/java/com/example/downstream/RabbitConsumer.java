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
    private final DynamoDbService dynamoDbService;

    @RabbitHandler
    public void consumeMessage(String message, MessageProperties messageProperties) {
        log.info("Consuming message: {}", message);
        log.info("B3-Header: {}", (String) messageProperties.getHeader("b3"));
        log.info("W3C-Header: {}", (String) messageProperties.getHeader("traceparent"));

        if (message.startsWith("TEST:")) {
            Mono.just(message)
                    .doOnNext(m -> log.info("Consumed message: {}", m))
                    .subscribe();
        } else if (message.startsWith("WRITE:")) {
            String cleanedMessage = message.replaceFirst("WRITE:", "");
            dynamoDbService.saveMessage(cleanedMessage)
                    .doOnNext(id -> log.info("Wrote message: {} with id: {}", cleanedMessage, id))
                    .subscribe();
        } else {
            throw new IllegalArgumentException("Unsupported message");
        }
    }

    @RabbitHandler(isDefault = true)
    public void consumeDefault(Message message) {
        throw new IllegalArgumentException("Unsupported message");
    }
}
