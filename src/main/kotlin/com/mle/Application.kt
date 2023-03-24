package com.mle

import com.mle.handler.StreamLambdaHandler
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.servlet.HandlerAdapter
import org.springframework.web.servlet.HandlerMapping
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping

@SpringBootApplication
class Application {
	/*
     * Create required HandlerMapping, to avoid several default HandlerMapping instances being created
     */
	@Bean
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



