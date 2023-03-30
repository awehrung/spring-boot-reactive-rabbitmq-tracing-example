package com.example.upstream;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class RabbitPublisher {
    private final RabbitTemplate rabbitTemplate;
    private final QueueConfigProperties queueConfigProperties;
    
    public void publishMessage(String message) {
        log.info("Publishing {}", message);

        Message convertedPayload = rabbitTemplate.getMessageConverter().toMessage(message, new MessageProperties());
        
        rabbitTemplate.send(
                queueConfigProperties.getExchange(),
                queueConfigProperties.getKey(),
                convertedPayload
        );
    }
}
