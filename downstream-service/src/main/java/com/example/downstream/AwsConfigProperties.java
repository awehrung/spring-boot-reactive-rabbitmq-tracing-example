package com.example.downstream;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "aws")
@Getter
@Setter
public class AwsConfigProperties {
    private String region;
    private String accessKeyId;
    private String secretAccessKey;
    private String endpoint;
}
