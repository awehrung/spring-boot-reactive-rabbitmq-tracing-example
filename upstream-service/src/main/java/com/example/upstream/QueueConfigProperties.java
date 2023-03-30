package com.example.upstream;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rabbitmq")
@Getter
@Setter
public class QueueConfigProperties {
    private String exchange;
    private String key;
}
