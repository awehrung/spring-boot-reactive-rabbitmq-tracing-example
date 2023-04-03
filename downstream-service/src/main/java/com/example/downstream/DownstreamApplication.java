package com.example.downstream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import reactor.core.publisher.Hooks;

@SpringBootApplication
@ConfigurationPropertiesScan("com.example.downstream")
public class DownstreamApplication {
    public static void main(String[] args) {
        // !!! Nothing works without this hook, see https://github.com/spring-projects/spring-boot/issues/34201
        Hooks.enableAutomaticContextPropagation();
        SpringApplication.run(DownstreamApplication.class, args);
    }
}
