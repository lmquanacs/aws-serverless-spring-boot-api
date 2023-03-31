package com.mle.seed

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.web.reactive.HandlerAdapter
import org.springframework.web.reactive.HandlerMapping
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerAdapter
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping

@SpringBootApplication
@EnableWebFlux
class Application {
	/*
     * Create required HandlerMapping, to avoid several default HandlerMapping instances being created
     */
	@Bean
	@Primary
	fun handlerMapping(): HandlerMapping {
		return RequestMappingHandlerMapping()
	}

	/*
     * Create required HandlerAdapter, to avoid several default HandlerAdapter instances being created
     */
	@Bean
	fun handlerAdapter(): HandlerAdapter {
		return RequestMappingHandlerAdapter()
	}
}

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}



