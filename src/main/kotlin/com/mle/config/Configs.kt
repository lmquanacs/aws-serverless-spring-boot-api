package com.mle.config

import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import aws.smithy.kotlin.runtime.http.Url
import kotlinx.coroutines.runBlocking
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
class Configs {

    @Bean
    fun dynamoDb() = runBlocking {
        DynamoDbClient.fromEnvironment()
    }

    @Bean
    @Profile("test")
    fun testDynamoDb() = runBlocking {
        DynamoDbClient.fromEnvironment {
            endpointUrl = Url.parse("http://localhost:4566/")
        }
    }
}