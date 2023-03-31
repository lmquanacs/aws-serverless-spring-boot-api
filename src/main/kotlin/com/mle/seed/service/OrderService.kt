package com.mle.seed.service

import com.mle.seed.controller.data.OrderRequest
import com.mle.seed.controller.data.OrderResponse
import com.mle.seed.mapping.toOrder
import com.mle.seed.repository.OrderRepository
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderRepository: OrderRepository
) {
    suspend fun createNewOrder(orderRequest: OrderRequest): OrderResponse {
        return orderRequest.toOrder().let {
            orderRepository.save(it)
        }.let {
            OrderResponse(id = it.orderUid)
        }
    }
}