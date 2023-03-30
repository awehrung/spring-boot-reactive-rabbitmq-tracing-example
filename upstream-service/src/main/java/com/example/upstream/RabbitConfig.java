package com.example.upstream;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitConfig {
    private final RabbitTemplate rabbitTemplate;
    
    @PostConstruct
    void enableObservation() {
        rabbitTemplate.setObservationEnabled(true);
    }
}
