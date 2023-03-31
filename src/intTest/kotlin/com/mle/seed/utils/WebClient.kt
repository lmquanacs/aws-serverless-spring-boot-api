package com.mle.seed.utils

import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.WebTestClient.bindToServer

object WebClient {
    fun lambdaProxyWebClient(): WebTestClient {
        return WebTestClient
            .bindToServer(ReactorClientHttpConnector())
            .baseUrl("http://localhost:9000/2015-03-31/functions/function/invocations")
            .build()
    }
}