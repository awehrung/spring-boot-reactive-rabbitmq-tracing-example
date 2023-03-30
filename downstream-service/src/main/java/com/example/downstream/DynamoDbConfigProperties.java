package com.example.downstream;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "dynamodb")
@Getter
@Setter
public class DynamoDbConfigProperties {
    private String tableName;
}
