package com.example.downstream;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClientBuilder;

import java.net.URI;

@Configuration
@RequiredArgsConstructor
public class DynamoDbConfig {
    private final AwsConfigProperties awsConfigProperties;
    
    @Bean
    public DynamoDbAsyncClient dynamoDbAsyncClient() {
        DynamoDbAsyncClientBuilder builder = DynamoDbAsyncClient.builder();
        
        builder.region(Region.of(awsConfigProperties.getRegion()));
        builder.credentialsProvider(
                StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(
                                awsConfigProperties.getAccessKeyId(),
                                awsConfigProperties.getSecretAccessKey()
                        )
                )
        );
        builder.endpointOverride(URI.create(awsConfigProperties.getEndpoint()));
        
        return builder.build();
    }
}
