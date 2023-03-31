package com.mle.seed.repository

import com.mle.seed.mapping.toMapAttrValue
import mu.KotlinLogging
import org.springframework.stereotype.Repository
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest

private val logger = KotlinLogging.logger {}

@Repository
class OrderRepository(
    private val dynamoDbClient: DynamoDbClient
) {
    suspend fun save(order: Order): Order {
        val response = dynamoDbClient.putItem(
            PutItemRequest.builder()
                .tableName("order")
                .item(order.toMapAttrValue()).build()
        )
        logger.info { "Save consuming ${response.consumedCapacity().capacityUnits()}" }
        return order
    }
}