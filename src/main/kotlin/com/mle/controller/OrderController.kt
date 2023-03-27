package com.mle.controller

import com.mle.controller.data.OrderRequest
import com.mle.controller.data.OrderResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import java.util.UUID

@RestController
@RequestMapping("/v1/orders")
@EnableWebMvc
class OrderController {


    @PostMapping
    fun createNewOrder(@RequestBody orderRequest: OrderRequest): OrderResponse {
        return OrderResponse(UUID.randomUUID())
    }
}