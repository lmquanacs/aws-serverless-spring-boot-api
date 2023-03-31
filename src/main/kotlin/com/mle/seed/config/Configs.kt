package com.mle.seed.config


import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import java.net.URI

@Configuration
class Configs {

    @Bean
    @Profile("!local")
    fun dynamoDb(): DynamoDbClient {
        return DynamoDbClient.builder().build()
    }

    @Bean
    @Profile("local")
    fun testDynamoDb(): DynamoDbClient {
        return DynamoDbClient.builder()
            .region(Region.AP_SOUTHEAST_2)
            .endpointOverride(
            URI.create("http://localhost:4566")
        ).build()
    }

}