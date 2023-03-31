package com.mle.seed.controller

import com.mle.seed.controller.data.OrderRequest
import com.mle.seed.controller.data.OrderResponse
import com.mle.seed.service.OrderService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/v1/orders")
@Validated
class OrderController(
    private val orderService: OrderService
) {

    @PostMapping
    suspend fun createNewOrder(@Valid @RequestBody orderRequest: OrderRequest): OrderResponse {
        return orderService.createNewOrder(orderRequest)
    }
}