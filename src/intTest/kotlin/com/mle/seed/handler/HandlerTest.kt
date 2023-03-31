package com.mle.seed.handler

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.mle.seed.utils.WebClient.lambdaProxyWebClient
import org.junit.jupiter.api.Test
import org.springframework.web.reactive.function.BodyInserters


class HandlerTest {

    val webTestClient = lambdaProxyWebClient()

    @Test
    fun test() {
        val event = APIGatewayProxyRequestEvent()
            .withHttpMethod("GET")
            .withPath("/ping")
        webTestClient.post()
            .uri("")
            .body(BodyInserters.fromValue(event))
            .exchange().expectStatus().isOk
    }
}