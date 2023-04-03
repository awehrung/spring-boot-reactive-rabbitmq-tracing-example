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
        // !!! Observation is not enabled by default, which prevents the tracing headers to be propagated over the queue
        rabbitTemplate.setObservationEnabled(true);
    }
}
